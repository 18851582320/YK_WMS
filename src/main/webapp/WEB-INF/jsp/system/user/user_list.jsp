
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
			<table class="easyui-datagrid" id="userInfoTable" data-options="fit:true" toolbar="#userInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="userInfo_bar" class="dataGridToolBar">
		<c:set var="editQx"  value="0"></c:set>
		<shiro:hasPermission name="SYS.USER.EDIT">
			<c:set var="editQx" value="1"></c:set>
			
		</shiro:hasPermission>
		&nbsp;&nbsp;<a href="javascript:void(0)" class="btn btn-mini btn-warning" id="userInfo-pwdReset"><i class="iconfont icon-icon-4"></i>&nbsp;密码重置</a>
		
		<shiro:hasPermission name="SYS.USER.DELETE">
			<!-- &nbsp;<a class="btn btn-mini btn-danger" id="userInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a> -->
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="userInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="userInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
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
		$("#userInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns: true,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>user/querylistPage.do",
	        frozenColumns:[[  
    	        {field:"USER_ID",checkbox:true},
    	    ]], 
	        columns:[[  
	        	{title:"用户名",field:"USERNAME",align:"center",width:150,sortable:true}, 
	        	{title:"姓名",field:"NAME",align:"center",width:200,sortable:true}, 
	        	{title:"最近登录",field:"LAST_LOGIN",align:"center",width:140,sortable:true}, 
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#userInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#userInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#userInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#userInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		
		//删除
		$("#userInfo-pwdReset").click(function(){
			var row=$("#userInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定重置选中的"+row.length+"个用户的密码吗？",function(flag){
					if(flag){
						var USER_IDS="";
						$(row).each(function(i,item){
							var index=$("#userInfoTable").datagrid("getRowIndex",item);
							if(USER_IDS==""){
								USER_IDS=USER_IDS+item.USER_ID;
							}else{
								USER_IDS=USER_IDS+","+item.USER_ID;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>user/pwdreset.do",
							type:"post",
							async:false,
							data:{USER_IDS:USER_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#userInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"密码重置成功",
										timeout:3000,
										showType:"slide"
									});
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
		
		
		
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#userInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="用户查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=sys_user&sysDataTableName=sys_user';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#userInfoTable").datagrid("options").url = "<%=basePath%>user/querylistPage.do";
					 $("#userInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-用户";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=sys_user&sysDataTableName=sys_user';
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
			 columnHideSetDiag.Title ="隐藏列设置-用户";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=sys_user&sysDataTableName=sys_user';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		
		
		//刷新
		$("#userInfo-reload").click(function(){
			$("#userInfoTable").datagrid("unselectAll");
			$("#userInfoTable").datagrid("reload");
		});
		
		//删除
		$("#userInfo-remove").click(function(){
			var row=$("#userInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var USER_IDS="";
						$(row).each(function(i,item){
							var index=$("#userInfoTable").datagrid("getRowIndex",item);
							//$("#userInfoTable").datagrid("deleteRow",index);
							if(USER_IDS==""){
								USER_IDS=USER_IDS+item.USER_ID;
							}else{
								USER_IDS=USER_IDS+","+item.USER_ID;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>user/delete.do",
							type:"post",
							async:false,
							data:{USER_IDS:USER_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#userInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"删除数据成功",
										timeout:2000,
										showType:"slide"
									});
								}else if(data.result=="canNotDelete"){
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","所选数据中有数据被人员信息所引用，不能删除！","error");
								}else{
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","删除出错！","error");
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
		$("#userInfo-add").click(function(){
			 var userAddDiag = new top.Dialog();
			 userAddDiag.Drag=true;
			 userAddDiag.Title ="用户新增";
			 userAddDiag.URL = '<%=basePath%>user/toAdd.do';
			 userAddDiag.Width = 450;
			 userAddDiag.Height = 340;
			 userAddDiag.CancelEvent = function(){ //关闭事件
				 if(userAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#userInfoTable").datagrid("reload");//重新加载数据
				 }
				 userAddDiag.close();
			 };
			 userAddDiag.show();
		});
		
		//导入
		$("#userInfo-importExcel").click(function(){
			var userExcelDiag = new top.Dialog();
			userExcelDiag.Drag=true;
			userExcelDiag.Title ="用户导入";
			userExcelDiag.URL = '<%=basePath%>file/toImportFile.do?controllerName=user&suffix=xls';  
			userExcelDiag.Width = 450;
			userExcelDiag.Height = 200
			userExcelDiag.CancelEvent = function(){ //关闭事件
				 if(userExcelDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#userInfoTable").datagrid("reload");//重新加载数据
				 }
				 userExcelDiag.close();
			 };
			 userExcelDiag.show();
		});
		
		
		
		//导出
		$("#userInfo-toExcel").click(function(){
			 var exUrl='<%=basePath%>user/excel.do?1=1';
			 if(queryConObj){
				 $.each(queryConObj,function(name,value) {
					 exUrl=exUrl+"&"+name+"="+value;
				 });
			 }
			 window.location.href=exUrl;
		});
		
		
	});
	
	//修改用户
	function editUser(USER_ID){
		 var userEditDiag = new top.Dialog();
		 userEditDiag.Drag=true;
		 userEditDiag.Title ="用户编辑";
		 userEditDiag.URL = '<%=basePath%>user/toEdit.do?USER_ID='+USER_ID;
		 userEditDiag.Width = 450;
		 userEditDiag.Height = 340;
		 userEditDiag.CancelEvent = function(){ //关闭事件
			 if(userEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#userInfoTable").datagrid("reload");//重新加载数据
			 }
			 userEditDiag.close();
		 };
		 userEditDiag.show();
	}
	
</script>

  
</html>
