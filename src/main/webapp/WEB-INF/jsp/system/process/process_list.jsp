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
	<%@ include file="../index/jquery_easyui.jsp"%>
</head>
<body>
	<!-- dataGrid -->
	<div class="main_tableDiv">
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="processModelInfoTable" data-options="fit:true" toolbar="#processModelInfo_bar">
				<thead>
					<tr>
						<th data-options="field:'CATEGORY_',width:120,align:'center'">流程定义分类</th>
						<th data-options="field:'NAME_',width:180,align:'center'">名称</th>
						<th data-options="field:'VERSION_',width:100,align:'center'">版本</th>
						<th data-options="field:'CREATE_TIME_',width:140,align:'center'">创建时间</th>
						<th data-options="field:'LAST_UPDATE_TIME_',width:140,align:'center'">最后修改时间</th>
						<th data-options="field:'Operation',width:180,align:'center'">操作</th>
					</tr>
			    </thead>
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="processModelInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="SYS.PROCESS.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="processModelInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="SYS.PROCESS.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="SYS.PROCESS.DELETE">
			<!-- &nbsp;<a class="btn btn-mini btn-danger" id="processInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a> -->
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="processModelInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
	</div>
	 
</body>


<script type="text/javascript">

	$(function(){
		
		//动态设定datagrid的高度
		setDataGridHeight();
		window.onresize=function(){  
			setDataGridHeight();
		};
		
		
		//加载数据
		$("#processModelInfoTable").datagrid({  
	        autoRowHeight:false,  
	        remoteSort: false,  
	        rownumbers: true,
	        fitColumns:false,
	        pagination:false
	    });
		
		//刷新
		$("#processModelInfo-reload").click(function(){
			window.location.reload();
		});
		
		
		//新增
		$("#processModelInfo-add").click(function(){
			 var modelInfoAddDiag = new top.Dialog();
			 modelInfoAddDiag.Drag=true;
			 modelInfoAddDiag.Title ="流程新增";
			 modelInfoAddDiag.URL = '<%=basePath%>process/toAdd.do';
			 modelInfoAddDiag.Width = 400;
			 modelInfoAddDiag.Height = 200;
			 modelInfoAddDiag.CancelEvent = function(){ //关闭事件
				 if(modelInfoAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 window.location.reload();//重新加载数据
				 }
				 modelInfoAddDiag.close();
			 };
			 modelInfoAddDiag.show();
		});
		
	});
	
</script>

  
</html>
