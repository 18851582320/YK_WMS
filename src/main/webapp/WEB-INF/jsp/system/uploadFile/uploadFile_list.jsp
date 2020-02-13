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
			<table class="easyui-datagrid" id="uploadFileInfoTable" data-options="fit:true" toolbar="#uploadFileInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="uploadFileInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="SYS.UPLOADFILE.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="uploadFileInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="uploadFileInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="uploadFileInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-info" id="uploadFileInfo-preview" title="预览"><i class="iconfont icon-chaxun5"></i>&nbsp;预览</a>		
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
		$("#uploadFileInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true, 
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>uploadFile/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"upFileId",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"表名称",field:"tableName",align:"center",width:150,sortable:true}, 
	        	{title:"表关键值",field:"tableKeyValue",align:"center",width:220,sortable:true}, 	        	
	        	{title:"文件路径",field:"filePath",align:"center",width:160,sortable:true}, 
	        	{title:"文件名称",field:"fileName",align:"center",width:250,sortable:true}, 
	        	{title:"上传时间",field:"uploadTime",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}, 
	        	{title:"上传人员",field:"updateStaffName",align:"center",width:100,sortable:true}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#uploadFileInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#uploadFileInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#uploadFileInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#uploadFileInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		

		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#uploadFileInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="系统附件查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=SYS_uploadFile&sysDataTableName=SYS_uploadFile';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#uploadFileInfoTable").datagrid("options").url = "<%=basePath%>uploadFile/querylistPage.do";
					 $("#uploadFileInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-系统附件";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=SYS_uploadFile&sysDataTableName=SYS_uploadFile';
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
			 columnHideSetDiag.Title ="隐藏列设置-系统附件";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=SYS_uploadFile&sysDataTableName=SYS_uploadFile';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#uploadFileInfo-reload").click(function(){
			$("#uploadFileInfoTable").datagrid("unselectAll");
			$("#uploadFileInfoTable").datagrid("reload");
		});
		
		//新增
		$("#uploadFileInfo-add").click(function(){
			 var uploadFileAddDiag = new top.Dialog();
			 uploadFileAddDiag.Drag=true;
			 uploadFileAddDiag.Title ="系统附件上传";
			 uploadFileAddDiag.URL = '<%=basePath%>uploadFile/toUpload.do?controllerName=uploadFile&tableName=ceshi&tableKeyValue=10a64aaf2d444651a414ccbbd656a0d1';
			 uploadFileAddDiag.Width = 500;
			 uploadFileAddDiag.Height = 150;
			 uploadFileAddDiag.CancelEvent = function(){ //关闭事件
				 uploadFileAddDiag.close();
			 };
			 uploadFileAddDiag.show();
		});
		
		
		//预览
		$("#uploadFileInfo-preview").click(function(){
			
			 var uploadFileAddDiag = new top.Dialog();
			 uploadFileAddDiag.Drag=true;
			 uploadFileAddDiag.Title ="系统附件预览";
			 uploadFileAddDiag.URL = '<%=basePath%>uploadFile/preview.do?winH='+winH+'&upFileIdS=&tableName=&tableKeyValue=&queryType=ceshi&queryKey=';
			 uploadFileAddDiag.Width = winW;
			 uploadFileAddDiag.Height = winH;
			 uploadFileAddDiag.CancelEvent = function(){ //关闭事件
				 uploadFileAddDiag.close();
			 };
			 uploadFileAddDiag.show();
		});
		
	});
	
	
	
</script>

  
</html>
