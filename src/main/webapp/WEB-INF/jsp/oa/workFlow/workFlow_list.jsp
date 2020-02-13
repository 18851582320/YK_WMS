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
	<div>
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="workFlowInfoTable" data-options="fit:true" toolbar="#workFlowInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="workFlowInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.DEPT.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="workFlowInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set>
		<shiro:hasPermission name="OA.DEPT.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.DEPT.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="workFlowInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="workFlowInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark " id="workFlowInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
		
	</div>
	 
</body>


<script type="text/javascript">
	var workFlowTypeId="${workFlowTypeId}";
	var queryConObj=null;
	//动态设定datagrid的高度
	function workFlowDataGridT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginLeft=parent.getMarginLeft();
		$("#dataGridDiv").css("height",h+"px");
		$("#dataGridDiv").css("width",(w-marginLeft)+"px");
	}

	$(function(){
		
		//动态设定datagrid的高度
		workFlowDataGridT();
		window.onresize=function(){  
			workFlowDataGridT();
		}; 
		

		
		//加载数据
		$("#workFlowInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        sortName: "workFlowID",  
	        sortOrder: "desc",
	        multiSort:true,
	        remoteSort: true,  
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:'<%=basePath%>workFlow/querylistPage.do?workFlowTypeId='+workFlowTypeId,     
	        frozenColumns:[[  
	            {field:"workFlowId",checkbox:true},
	        ]],  
	        columns:[[  
				{title:"操作",field:"Operation",align:"center",width:300,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							var editHtml="";
							editHtml="<a  href=\"javascript:editworkFlow('"+row.workFlowId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>"
							+"&nbsp;<a  href=\"javascript:designeWorkFlow('"+row.workFlowId+"');\" class=\"btn btn-mini btn-info\" title=\"设计\"><i class=\"iconfont icon-sheji\"></i>&nbsp;设计</a>";
							
							if(row.releaseState!=1){
								editHtml+="&nbsp;<a  href=\"javascript:releaseworkFlow('"+row.workFlowId+"');\" class=\"btn btn-mini btn-info\" title=\"发布\"><i class=\"iconfont icon-fabu\"></i>&nbsp;发布</a>";
							}
							if(row.versionNum>0){
								editHtml+="&nbsp;<a  href=\"javascript:editworkFlow('"+row.workFlowId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-banbenqiehuan\"></i>&nbsp;版本监控</a>";
							}
							return editHtml;
						}else{
							return "";
						}
					}	
				},
				{title:"流程名称",field:"workFlowName",align:"center",width:160,sortable:true}, 	        	
				{title:"启用状态",field:"isEnable",align:"center",width:100,sortable:true,
					formatter:function(value,row){
						if(value=="1"){
	        				return "<i class=\"iconfont icon-kejian green\"></i>"; 
	        			}else{
	        				return "<i class=\"iconfont icon-bukejian red\"></i>"; 
	        			}
					}	
				}, 	        	
				{title:"发布状态",field:"releaseState",align:"center",width:100,sortable:true,
					formatter:function(value,row){
						if(value=="1"){
	        				return "已发布"; 
	        			}else if(value=="2"){
	        				return "有更新"; 
	        			}else{
	        				return "未发布";
	        			}
					},
					styler:function(value,row,index){
						if(value=="1"){
							return "background-color:#1da02b;"; 
	        			}else if(value=="2"){
	        				return "background-color:#f29503;"; 
	        			}else{
	        				return "background-color:#80909a;"; 
	        			}
					}
				},
				{title:"版本数量",field:"versionNum",align:"center",width:80,sortable:true},
	        	{title:"流程描述",field:"workFlowMemo",align:"center",width:200,sortable:true} 
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#workFlowInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#workFlowInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#workFlowInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#workFlowInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#workFlowInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="流程查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=WF_workFlow&sysDataTableName=WF_workFlow';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#workFlowInfoTable").datagrid("options").url = "<%=basePath%>workFlow/querylistPage.do";
					 $("#workFlowInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-流程";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=WF_workFlow&sysDataTableName=WF_workFlow';
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
			 columnHideSetDiag.Title ="隐藏列设置-流程";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=WF_workFlow&sysDataTableName=WF_workFlow';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#workFlowInfo-reload").click(function(){
			$("#workFlowInfoTable").datagrid("unselectAll");
			$("#workFlowInfoTable").datagrid("reload");
		});
		
		//删除
		$("#workFlowInfo-remove").click(function(){
			var row=$("#workFlowInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var workFlow_IDS="";
						$(row).each(function(i,item){
							var index=$("#workFlowInfoTable").datagrid("getRowIndex",item);
							//$("#workFlowInfoTable").datagrid("deleteRow",index);
							if(workFlow_IDS==""){
								workFlow_IDS=workFlow_IDS+item.workFlowID;
							}else{
								workFlow_IDS=workFlow_IDS+","+item.workFlowID;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>workFlow/delete.do",
							type:"post",
							async:false,
							data:{workFlow_IDS:workFlow_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#workFlowInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"删除数据成功",
										timeout:2000,
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
		$("#workFlowInfo-add").click(function(){
			 
			 var workFlowAddDiag = new top.Dialog();
			 workFlowAddDiag.Drag=true;
			 workFlowAddDiag.Title ="新增流程";
			 workFlowAddDiag.URL =  '<%=basePath%>workFlow/toAdd.do';
			 workFlowAddDiag.Width = 400;
			 workFlowAddDiag.Height = 300;
			 workFlowAddDiag.CancelEvent = function(){ //关闭事件
				 if(workFlowAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#workFlowInfoTable").datagrid("reload");//重新加载数据
				 }
				 workFlowAddDiag.close();
			 };
			 workFlowAddDiag.show();
		});
		
		
	});
	
	//修改流程
	function editworkFlow(workFlowId){
		 var menuEditDiag = new top.Dialog();
		 menuEditDiag.Drag=true;
		 menuEditDiag.Title ="流程编辑";
		 menuEditDiag.URL = '<%=basePath%>workFlow/toEdit.do?workFlowId='+workFlowId;
		 menuEditDiag.Width = 400;
		 menuEditDiag.Height = 300;
		 menuEditDiag.CancelEvent = function(){ //关闭事件
			 if(menuEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#workFlowInfoTable").datagrid("reload");//重新加载数据
			 }
			 menuEditDiag.close();
		 };
		 menuEditDiag.show();
	}
	
	//工作流程设计
	function designeWorkFlow(workFlowId){
		var h=window.screen.availHeight*0.9;
		var w=window.screen.availWidth*0.9;
		
		var designEditDiag = new top.Dialog();
		 designEditDiag.Drag=true;
		 designEditDiag.Title ="工作流程设计";
		 designEditDiag.URL = '<%=basePath%>workFlow/designer/load.do?workFlowId='+workFlowId;
		 designEditDiag.Width = w;
		 designEditDiag.Height = h;
		 designEditDiag.CancelEvent = function(){ //关闭事件
			 /*
			 if(designEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
			 }
		 	 */
		 	 
		 	 $("#workFlowInfoTable").datagrid("reload");//重新加载数据
			 designEditDiag.close();
		 };
		 designEditDiag.show();
	}
	
	//发布
	function releaseworkFlow(workFlowId){
			
		$.messager.confirm("提示","确定发布?",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>workFlow/deployFlow.do",
					type:"post",
					async:false,
					data:{workFlowId:workFlowId},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							$("#workFlowInfoTable").datagrid("reload");
							$.messager.show({
								title:"系统提示",
								msg:"发布成功",
								timeout:2000,
								showType:"slide"
							});
						}else if(data.result=="emptyXml"){
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","流程定义XML不能为空！","error");
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","发布出错:"+data.errmsg,"error");
						}
					}
				});
			}
		});
	}
	
	
</script>

  
</html>
