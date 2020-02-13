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
			<div id="saveEditCenter" class="saveEditCenter" style="height: 493px;">
				<table>
					 <tr style="height: 5px"/>
					 <tr>
					 	<td>
					 		<textarea rows="" cols="" style="width: 800px;height: 490px;">${pd.xmlCode}</textarea>
					 	</td>
					 </tr>
					 <tr style="height: 5px"/>
				</table>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	
</script>


</html>
