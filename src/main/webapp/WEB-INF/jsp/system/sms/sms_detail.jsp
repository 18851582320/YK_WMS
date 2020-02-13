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
<body class="addEditDivBody">
	<div class="addEditDiv">
		<div class="">
				<div id="saveEditCenter" class="saveEditCenter" style="height: 460px;">
					<div style="font-size: 18px;font-weight: bold;text-align: center;height: 50px;">
						${pd.title}
					</div>
					<div style="text-align: right;height: 30px;">
						发送人：${pd.fromUserName}&nbsp;&nbsp;&nbsp;发送时间：${pd.sendTime}&nbsp;&nbsp;&nbsp;
					</div>
					<div style="padding: 10px;height: 350px;border-top: 1px dotted red">
			 			${pd.content}
			 		</div>
				</div>
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
				</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	
</script>


</html>
