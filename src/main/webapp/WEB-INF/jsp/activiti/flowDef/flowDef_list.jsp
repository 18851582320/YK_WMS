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
			<table class="easyui-datagrid" id="dataTable" data-options="fit:true" toolbar="#dataInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="dataInfo_bar" class="dataGridToolBar">
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.PROCDEF.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.PROCDEF.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="procdefInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="procdefInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="procdefInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
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
		$("#dataTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        sortName:"DEPLOY_TIME_",
	        sortOrder:"desc",
	        multiSort:true,
	        remoteSort: true,  
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>flowDef/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"ID_",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:100,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							var editHtml="";
							
							if(row.SUSPENSION_STATE_=='2'){
								editHtml="<a  href=\"javascript:onoff('"+row.ID_+"','1');\" class=\"btn btn-mini btn-success\" title=\"激活\"><i class=\"iconfont icon-you\"></i>&nbsp;激活</a>";
							}
							if(row.SUSPENSION_STATE_=='1'){
								editHtml="<a  href=\"javascript:onoff('"+row.ID_+"','2');\" class=\"btn btn-mini btn-danger\" title=\"挂起\"><i class=\"iconfont icon-tuichu\"></i>&nbsp;挂起</a>";
							}
							return editHtml;
						}else{
							return "";
						}
					}	
				},
				{title:"流程名称",field:"NAME_",align:"center",width:200,sortable:true}, 
	        	{title:"流程定义KEY",field:"KEY_",align:"center",width:150,sortable:true}, 	        	
	        	{title:"版本",field:"VERSION_",align:"center",width:80,sortable:true}, 	        	
	        	{title:"状态",field:"SUSPENSION_STATE_",align:"center",width:150,sortable:false,
	        		formatter:function(value,row){
						if(row.SUSPENSION_STATE_=="1"){
							return "已激活";
						}else{
							return "已挂起";
						}
	        			
					}		
	        	}, 
	        	{title:"部署时间",field:"DEPLOY_TIME_",align:"center",width:140,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}, 
	        	{title:"流程bpmn文件名称",field:"RESOURCE_NAME_",align:"center",width:250,sortable:false,
	        		formatter:function(value,row){
						return "<a  href=\"javascript:goViewXml('"+row.DEPLOYMENT_ID_+"','"+row.RESOURCE_NAME_+"');\">"+row.RESOURCE_NAME_+"</a>";
					}	
	        	}, 
	        	{title:"流程图片名称",field:"DGRM_RESOURCE_NAME_",align:"center",width:250,sortable:false,
	        		formatter:function(value,row){
						return "<a  href=\"javascript:goViewPng('"+row.DEPLOYMENT_ID_+"','"+row.DGRM_RESOURCE_NAME_+"');\">"+row.DGRM_RESOURCE_NAME_+"</a>";
					}		
	        	} 
	        	
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#dataTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#dataTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#dataTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#dataTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });


		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#procdefInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="流程查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=ACT_RE_PROCDEF&sysDataTableName=ACT_RE_PROCDEF';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#dataTable").datagrid("options").url = "<%=basePath%>flowDef/querylistPage.do";
					 $("#dataTable").datagrid("load",conObj);
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
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=ACT_RE_PROCDEF&sysDataTableName=ACT_RE_PROCDEF';
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
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=ACT_RE_PROCDEF&sysDataTableName=ACT_RE_PROCDEF';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#procdefInfo-reload").click(function(){
			$("#dataTable").datagrid("unselectAll");
			$("#dataTable").datagrid("reload");
		});
		
		//删除
		$("#procdefInfo-remove").click(function(){
			var row=$("#dataTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var procdefIdS="";
						$(row).each(function(i,item){
							var index=$("#dataTable").datagrid("getRowIndex",item);
							if(procdefIdS==""){
								procdefIdS=procdefIdS+item.DEPLOYMENT_ID_;
							}else{
								procdefIdS=procdefIdS+","+item.DEPLOYMENT_ID_;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>flowDef/delete.do",
							type:"post",
							async:false,
							data:{procdefIdS:procdefIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#dataTable").datagrid("reload");
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
		
	});
	
	
	
	//预览Png
	function goViewPng(DEPLOYMENT_ID_,FILENAME){
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="预览流程图";
		diag.URL = '<%=basePath%>flowDef/goViewPng.do?DEPLOYMENT_ID_='+DEPLOYMENT_ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
		diag.Width = 800;
		diag.Height = 500;
		diag.Modal = true;				//有无遮罩窗口
		diag.ShowMinButton = true;		//最小化按钮
		diag.ShowMaxButton = true;		//最大化按钮
		diag.CancelEvent = function(){ 	//关闭事件
		diag.close();
		};
		diag.show();
	}
	
	
	//预览xml
	function goViewXml(DEPLOYMENT_ID_,FILENAME){
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="预览xml";
		diag.URL = '<%=basePath%>flowDef/goViewXml.do?DEPLOYMENT_ID_='+DEPLOYMENT_ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
		diag.Width = 800;
		diag.Height = 500;
		diag.Modal = true;				//有无遮罩窗口
		diag.ShowMinButton = true;		//最小化按钮
		diag.ShowMaxButton = true;		//最大化按钮
		diag.CancelEvent = function(){ 	//关闭事件
		diag.close();
		};
		diag.show();
	}
	
	//激活 or 挂起
	function onoff(ID_,STATUS){
		
		var confirmName="激活";
		if(STATUS=="2"){
			confirmName="挂起";
		}
		
		$.messager.confirm("提示","确定"+confirmName+"?",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:'<%=basePath%>flowDef/onoffPro.do?tm='+new Date().getTime(),
					type:"post",
					async:false,
					data: {ID_:ID_,STATUS:STATUS},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							$("#dataTable").datagrid("reload");
							$.messager.show({
								title:"系统提示",
								msg:"操作成功！",
								timeout:2000,
								showType:"slide"
							});
						}else if(data.result=="emptyXml"){
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","流程定义XML不能为空！","error");
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","部署出错:"+data.errmsg,"error");
						}
					}
				});
			}
		});
		
	}
	
	
	
</script>

  
</html>
