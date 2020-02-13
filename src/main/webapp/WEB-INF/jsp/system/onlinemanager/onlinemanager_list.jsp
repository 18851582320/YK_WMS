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
	<!-- 即时通讯 -->
	<!-- <link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/css/websocket.css" /> -->
	<script type="text/javascript" src="plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
	<script type="text/javascript" src="plugins/websocketInstantMsg/websocket.js"></script>
	<!-- 即时通讯 -->
	
</head>
<body>
	<!-- dataGrid -->
	<div class="main_tableDiv">
		<div id="dataGridDiv" class="dataGridDiv" title="在线管理">
			<table id="onlineUserInfoTable">
				 <thead>
					<tr>
						<th data-options="field:'USERNAME',width:400,align:'center'">用户名</th>
						<th data-options="field:'Operation',width:150,align:'center'">操作</th>
					</tr>
			    </thead>
			    <tbody id="userlist">
					
				</tbody>
			</table>
		</div>
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
		$("#onlineUserInfoTable").datagrid({  
	        autoRowHeight:true,  
	        remoteSort: false,  
	        pageSize: 1000,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,1000]
	    });
		
		online();
		
	});
	
	var websocketonline;//websocke对象
	var userCount = 0;	//在线总数
	
	function online()
	{
		if (window.WebSocket) {
			websocketonline = new WebSocket(encodeURI("ws://"+top.oladress)); //oladress在main.jsp页面定义
			websocketonline.onopen = function() {
				websocketonline.send("[onLineUserList]");//连接成功
			};
			websocketonline.onerror = function() {
				//连接失败
			};
			websocketonline.onclose = function() {
				//连接断开
			};
			//消息接收
			websocketonline.onmessage = function(message) {
				var message = JSON.parse(message.data);
				if (message.type == "count") {
					userCount = message.msg;
				}else if(message.type == "userlist"){
					if(message.list){
						var userList=""+message.list;
						var userArray =userList.split(",");
						var len=userArray.length;
						for (var i = 0; i <len; i++) {
							var row = new Object();
							row.USERNAME = userArray[i];
							row.Operation = "<a href=\"javascript:goOutTUser('"+userArray[i]+"')\" class=\"btn btn-danger btn-mini\" title=\"下线\"><i class=\"iconfont icon-tuichu\"></i>&nbsp;强制下线</a>";
							$("#onlineUserInfoTable").datagrid("appendRow", row);
						}
					}
				}
			};
		}
	}
	
	//强制某用户下线
	function goOutTUser(theuser){
		if("admin" == theuser){
			$.messager.alert("提示","不能强制admin下线","error");
			return;
		}else{
			$.messager.confirm("提示","确定要强制【"+theuser+"】下线吗？",function(r){
				if(r){
					websocketonline.send("[goOut]"+theuser);
					window.location.reload();
				}
			});
		}
	}
	
</script>

  
</html>
