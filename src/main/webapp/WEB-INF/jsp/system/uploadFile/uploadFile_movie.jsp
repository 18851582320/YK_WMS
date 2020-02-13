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
	<script type="text/javascript" src="plugins/ckplayer/ckplayer.js"></script>
</head>
<body>
	<div id="video" style="${pd.fileDivW}px; height: ${pd.fileDivH}px;">
		
	</div>
</body>
	<script type="text/javascript">
		var fileUrl="${pd.fileUrl}";
		var videoObject = {
			container: '#video',//“#”代表容器的ID，“.”或“”代表容器的class
			variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
			poster:'pic/wdm.jpg',
			video:fileUrl//视频地址
		};
		var player=new ckplayer(videoObject);
	</script>
</html>
