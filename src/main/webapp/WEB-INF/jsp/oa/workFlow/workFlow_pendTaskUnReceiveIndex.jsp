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
			<table class="easyui-datagrid" id="pendTaskUnReceiveInfoTable" data-options="fit:true" toolbar="#pendTaskUnReceiveInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="pendTaskUnReceiveInfo_bar" class="dataGridToolBar">
		&nbsp;<a class="btn btn-mini btn-lightBlue"  id="pendTaskUnReceiveInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;<a class="btn btn-mini btn-dark" id="pendTaskUnReceiveInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
		&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
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
		$("#pendTaskUnReceiveInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        remoteSort: false,  
	        pagination: false,
	        rownumbers: true,
	        fitColumns:false,
	        url:"<%=basePath%>workFlow/pendTaskUnReceiveList.do",     
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						return "<a  href=\"javascript:signWorkFlow('"+row.id+"');\" class=\"btn btn-mini btn-info\" title=\"签收\"><i class=\"iconfont icon-bianji\"></i>签收</a>";
					}	
				},
	        	{title:"流程名称",field:"workFlowName",align:"center",width:160,sortable:false}, 	        	
	        	{title:"当前步骤",field:"name",align:"center",width:160,sortable:false}, 	        	
	        	{title:"发起人",field:"applyUserId",align:"center",width:160,sortable:false},
	        	{title:"任务创建时间",field:"createTime",align:"center",width:160,sortable:false},
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#pendTaskUnReceiveInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#pendTaskUnReceiveInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#pendTaskUnReceiveInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#pendTaskUnReceiveInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });


		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#pendTaskUnReceiveInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="流程监控器查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=WF_pendTaskUnReceive&sysDataTableName=WF_pendTaskUnReceive';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#pendTaskUnReceiveInfoTable").datagrid("options").url = "<%=basePath%>workFlow/pendTaskUnReceiveList.do";
					 $("#pendTaskUnReceiveInfoTable").datagrid("load",conObj);
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
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=WF_pendTaskUnReceive&sysDataTableName=WF_pendTaskUnReceive';
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
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=WF_pendTaskUnReceive&sysDataTableName=WF_pendTaskUnReceive';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#pendTaskUnReceiveInfo-reload").click(function(){
			$("#pendTaskUnReceiveInfoTable").datagrid("unselectAll");
			$("#pendTaskUnReceiveInfoTable").datagrid("reload");
		});
			
	});
	
	//签收
	function signWorkFlow(taskId){
		$.messager.confirm("提示","确定签收?",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>workFlow/receiveTask.do",
					type:"post",
					async:false,
					data:{taskId:taskId},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							$("#pendTaskUnReceiveInfoTable").datagrid("reload");
							$.messager.show({
								title:"系统提示",
								msg:"签收成功",
								timeout:2000,
								showType:"slide"
							});
						}else if(data.result=="emptyTask"){
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","任务不存在！","error");
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","签收出错:"+data.errmsg,"error");
						}
					}
				});
			}
		});
	}
		
	
</script>

  
</html>
