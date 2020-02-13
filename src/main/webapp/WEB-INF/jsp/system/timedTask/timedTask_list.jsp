<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<%@ include file="../../system/index/jquery_easyui.jsp"%>
</head>
<body>
	<!-- dataGrid -->
	<div class="main_tableDiv">
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="timedTaskInfoTable" data-options="fit:true" toolbar="#timedTaskInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="timedTaskInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="SYS.TIMEDTASK.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="timedTaskInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="SYS.TIMEDTASK.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="SYS.TIMEDTASK.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="timedTaskInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="timedTaskInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="timedTaskInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
	</div>
	 
</body>


<script type="text/javascript">
	var queryConObj=null;


	$(function(){
		
		//动态设定datagrid的高度
		setDataGridHeight();
		window.onresize=function(){  
			setDataGridHeight();
		};
		
		//加载数据
		$("#timedTaskInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns: true,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>timedTask/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"timedTaskId",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:200,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:edittimedTask('"+row.timedTaskId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>"+
							"&nbsp;&nbsp;<a  href=\"javascript:runOnce('"+row.timedTaskId+"');\" class=\"btn btn-mini btn-info\" title=\"运行一次任务\"><i class=\"iconfont icon-zaiceyici\"></i></a>"+
							"&nbsp;&nbsp;<a  href=\"javascript:resumeJob('"+row.timedTaskId+"');\" class=\"btn btn-mini btn-info\" title=\"启动任务\"><i class=\"iconfont icon-icon-1\"></i></a>"+
							"&nbsp;&nbsp;<a  href=\"javascript:pauseJob('"+row.timedTaskId+"');\" class=\"btn btn-mini btn-info\" title=\"暂停任务\"><i class=\"iconfont icon-zanting1\"></i></a>";
						}else{
							return "";
						}
					}	
				},
				{title:"任务名",field:"timedTaskName",align:"center",width:110,sortable:true}, 
	        	{title:"任务别名",field:"timedTaskAliasName",align:"center",width:130,sortable:true}, 	        	
	        	{title:"任务分组",field:"timedTaskType",align:"center",width:150,sortable:true}, 
	        	{title:"任务类Class",field:"timedTaskClass",align:"center",width:180,sortable:true,
	        		formatter:function(value,row){
	        			return "<span  title='"+value+"' class='cell_content' >"+value+"</span>";
	        		}	
	        	}, 
	        	{title:"状态",field:"timedTaskState",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			if(value==0){
	        				return "停用";
	        			}else if(value==1){
	        				return "启用";
	        			}
	        		}	
	        	},
	        	{title:"执行方式",field:"cronExpression",align:"center",width:100,sortable:true},
	        	{title:"描述",field:"timedTaskMemo",align:"center",width:100,sortable:true},
	        	{title:"修改时间",field:"updateTime",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#timedTaskInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#timedTaskInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#timedTaskInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#timedTaskInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	       		showTip();//单元格鼠标悬停时，显示单元格内容。
	         }

	    });
		

		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#timedTaskInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="任务查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=SYS_timedTask&sysDataTableName=SYS_timedTask';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#timedTaskInfoTable").datagrid("options").url = "<%=basePath%>timedTask/querylistPage.do";
					 $("#timedTaskInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-任务";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=SYS_timedTask&sysDataTableName=SYS_timedTask';
			 querySetDiag.Width = 800;
			 querySetDiag.Height = 500;
			 querySetDiag.CancelEvent = function(){ //关闭事件
				 querySetDiag.close();
			 };
			 querySetDiag.show();
		});
		
		//隐藏设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#columnHideSet").click(function(){
			 var columnHideSetDiag = new top.Dialog();
			 columnHideSetDiag.Drag=true;
			 columnHideSetDiag.Title ="隐藏列设置-任务";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=SYS_timedTask&sysDataTableName=SYS_timedTask';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#timedTaskInfo-reload").click(function(){
			$("#timedTaskInfoTable").datagrid("unselectAll");
			$("#timedTaskInfoTable").datagrid("reload");
		});
		
		//删除
		$("#timedTaskInfo-remove").click(function(){
			var row=$("#timedTaskInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var timedTaskIdS="";
						$(row).each(function(i,item){
							var index=$("#timedTaskInfoTable").datagrid("getRowIndex",item);
							if(timedTaskIdS==""){
								timedTaskIdS=timedTaskIdS+item.timedTaskId;
							}else{
								timedTaskIdS=timedTaskIdS+","+item.timedTaskId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>timedTask/delete.do",
							type:"post",
							async:false,
							data:{timedTaskIdS:timedTaskIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#timedTaskInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"删除数据成功",
										timeout:3000,
										showType:"slide"
									});
								}else if(data.result=="canNotDelete"){
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","所选数据中有数据被引用，不能删除！","error");
								}else{
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","删除出错:"+data.errmsg,"error");
								}
							}
						});
						
					}
				});
			}else{
				$.messager.alert("系统提示","请选择要删除的数据","error");
			}
		});
		
		
		//新增
		$("#timedTaskInfo-add").click(function(){
			 var timedTaskAddDiag = new top.Dialog();
			 timedTaskAddDiag.Drag=true;
			 timedTaskAddDiag.Title ="任务新增";
			 timedTaskAddDiag.URL = '<%=basePath%>timedTask/toAdd.do';
			 timedTaskAddDiag.Width = 400;
			 timedTaskAddDiag.Height = 350;
			 timedTaskAddDiag.CancelEvent = function(){ //关闭事件
				 if(timedTaskAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#timedTaskInfoTable").datagrid("reload");//重新加载数据
				 }
				 timedTaskAddDiag.close();
			 };
			 timedTaskAddDiag.show();
		});
		
	});
	
	
	//修改
	function edittimedTask(timedTaskId){
		 var timedTaskEditDiag = new top.Dialog();
		 timedTaskEditDiag.Drag=true;
		 timedTaskEditDiag.Title ="任务编辑";
		 timedTaskEditDiag.URL = '<%=basePath%>timedTask/toEdit.do?timedTaskId='+timedTaskId;
		 timedTaskEditDiag.Width = 400;
		 timedTaskEditDiag.Height = 350;
		 timedTaskEditDiag.CancelEvent = function(){ //关闭事件
			 if(timedTaskEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#timedTaskInfoTable").datagrid("reload");//重新加载数据
			 }
			 timedTaskEditDiag.close();
		 };
		 timedTaskEditDiag.show();
	}
	
	//运行一次任务
	function runOnce(timedTaskId){
		$.messager.confirm("提示","确定运行任务一次?",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>timedTask/runOnce.do",
					type:"post",
					async:false,
					data:{timedTaskId:timedTaskId},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							$.messager.show({
								title:"系统提示",
								msg:"成功运行任务一次",
								timeout:2000,
								showType:"slide"
							});
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","发布出错:"+data.errmsg,"error");
						}
					}
				});
			}
		});
	}
	//启动任务
	function resumeJob(timedTaskId){
		$.messager.confirm("提示","确定启动任务?",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>timedTask/resumeJob.do",
					type:"post",
					async:false,
					data:{timedTaskId:timedTaskId},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							$("#timedTaskInfoTable").datagrid("reload");//重新加载数据
							$.messager.show({
								title:"系统提示",
								msg:"启动任务成功",
								timeout:2000,
								showType:"slide"
							});
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","出错:"+data.errmsg,"error");
						}
					}
				});
			}
		});
	}
	//暂停任务
	function pauseJob(timedTaskId){
		$.messager.confirm("提示","确定暂停任务?",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>timedTask/pauseJob.do",
					type:"post",
					async:false,
					data:{timedTaskId:timedTaskId},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							$("#timedTaskInfoTable").datagrid("reload");//重新加载数据
							$.messager.show({
								title:"系统提示",
								msg:"暂停任务成功",
								timeout:2000,
								showType:"slide"
							});
						}else if(data.result=="notStart"){
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","没启动无需关闭！","error");
						} else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","出错:"+data.errmsg,"error");
						}
					}
				});
			}
		});
	}
	
	
	
</script>

  
</html>
