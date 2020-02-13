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
			<table class="easyui-datagrid" id="workFlowListenerInfoTable" data-options="fit:true" toolbar="#workFlowListenerInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="workFlowListenerInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.WFLISTENER.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="workFlowListenerInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.WFLISTENER.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="workFlowListenerInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="workFlowListenerInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
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
		$("#workFlowListenerInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>workFlowListener/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"wfListenerId",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editworkFlowListener('"+row.wfListenerId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
						}else{
							return "";
						}
					}	
				},
				{title:"流程监控器名称",field:"wfListenerName",align:"center",width:160,sortable:true},
	        	{title:"类型",field:"wfListenerType",align:"center",width:160,sortable:true,
					formatter:function(value,row){
						//有修改权限
						if(value==1){
							return "执行监听器";
						}else{
							return "用户任务监听器";
						}
					}
	        	}, 	        	
	        	{title:"事件",field:"wfListenerEvent",align:"center",width:160,sortable:true},
	        	{title:"执行类型",field:"wfListenerValueType",align:"center",width:160,sortable:true,
					formatter:function(value,row){
						//有修改权限
						if(value==1){
							return "Java Class";
						}else{
							return "expression";
						}
					}
	        	},
	        	{title:"执行内容",field:"wfListenerValue",align:"center",width:300,sortable:true},
	        	{title:"流程监控器备注",field:"wfListenerMemo",align:"center",width:200,sortable:true} 
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#workFlowListenerInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#workFlowListenerInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#workFlowListenerInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#workFlowListenerInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });


		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#workFlowListenerInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="流程监控器查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=WF_workFlowListener&sysDataTableName=WF_workFlowListener';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#workFlowListenerInfoTable").datagrid("options").url = "<%=basePath%>workFlowListener/querylistPage.do";
					 $("#workFlowListenerInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-流程监控器";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=WF_workFlowListener&sysDataTableName=WF_workFlowListener';
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
			 columnHideSetDiag.Title ="隐藏列设置-流程监控器";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=WF_workFlowListener&sysDataTableName=WF_workFlowListener';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#workFlowListenerInfo-reload").click(function(){
			$("#workFlowListenerInfoTable").datagrid("unselectAll");
			$("#workFlowListenerInfoTable").datagrid("reload");
		});
		
		
		//删除
		$("#workFlowListenerInfo-remove").click(function(){
			var row=$("#workFlowListenerInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var wfListenerIdS="";
						$(row).each(function(i,item){
							var index=$("#workFlowListenerInfoTable").datagrid("getRowIndex",item);
							if(wfListenerIdS==""){
								wfListenerIdS=wfListenerIdS+item.wfListenerId;
							}else{
								wfListenerIdS=wfListenerIdS+","+item.wfListenerId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>workFlow/delete.do",
							type:"post",
							async:false,
							data:{wfListenerIdS:wfListenerIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#workFlowListenerInfoTable").datagrid("reload");
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
		$("#workFlowListenerInfo-add").click(function(){
			 var workFlowListenerAddDiag = new top.Dialog();
			 workFlowListenerAddDiag.Drag=true;
			 workFlowListenerAddDiag.Title ="流程监控器新增";
			 workFlowListenerAddDiag.URL = '<%=basePath%>workFlowListener/toAdd.do';
			 workFlowListenerAddDiag.Width = 550;
			 workFlowListenerAddDiag.Height = 400;
			 workFlowListenerAddDiag.CancelEvent = function(){ //关闭事件
				 if(workFlowListenerAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#workFlowListenerInfoTable").datagrid("reload");//重新加载数据
				 }
				 workFlowListenerAddDiag.close();
			 };
			 workFlowListenerAddDiag.show();
		});
		
	});
	
	
	//修改
	function editworkFlowListener(wfListenerId){
		 var workFlowListenerEditDiag = new top.Dialog();
		 workFlowListenerEditDiag.Drag=true;
		 workFlowListenerEditDiag.Title ="流程监控器编辑";
		 workFlowListenerEditDiag.URL = '<%=basePath%>workFlowListener/toEdit.do?wfListenerId='+wfListenerId;
		 workFlowListenerEditDiag.Width = 550;
		 workFlowListenerEditDiag.Height = 400;
		 workFlowListenerEditDiag.CancelEvent = function(){ //关闭事件
			 if(workFlowListenerEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#workFlowListenerInfoTable").datagrid("reload");//重新加载数据
			 }
			 workFlowListenerEditDiag.close();
		 };
		 workFlowListenerEditDiag.show();
	}
	
	
	
</script>

  
</html>
