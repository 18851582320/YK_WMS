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
	
	<style type="text/css">
		.new-list { margin-left: 10px;}
		.new-list ul li{ width: 160px; background-color: #DEDEDE; margin-top: 10px;text-align: center; padding-top: 10px; padding-bottom: 10px;}
		.new-list ul li a{ color: #555555; font-size: 12px; }
		.new-list ul li.active{ background-color: #1C88B9; }
		.new-list ul li:hover{ background-color: #1C88B9; }
		.new-list ul li:hover a{ color: #fff;}
		.new-list ul li.active a{ color: #fff;}
	</style>
	
</head>
<body>
	<div class="main_tableDiv">
		<table style="width:100%;height: 100%;" border="0" id="menuTable">
			<tr>
				<td style="width:15%;" valign="top" bgcolor="#f5f5f5">
					<div style="width:100%;overflow: auto;" id="leftDiv">
						<div class="new-list">
							<ul>
								<c:forEach items="${typeLst}" var="type" varStatus="">
									<c:choose>
										<c:when test="${type.trackLimitTypeCode eq firstCode}">
											<li id="${type.trackLimitTypeCode}" class="active" onclick="javascript:queryData('${type.trackLimitTypeCode}')">
											  <a href="javascript:void(0)">
											    <span>${type.trackLimitTypeName}</span>
											  </a>
											</li>
										</c:when>
										<c:otherwise>
											<li id="${type.trackLimitTypeCode}" onclick="javascript:queryData('${type.trackLimitTypeCode}')">
											  <a href="javascript:void(0)">
											    <span>${type.trackLimitTypeName}</span>
											  </a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								
							</ul>
						</div>
					</div>
				</td>
				<td style="width: 2%;"></td>
				<td style="width:83%;" id="dataFrameId">
					<iframe name="dataFrame" id="dataFrame" frameborder="0" src="<%=basePath%>sysTrackLimit/list.do?trackLimitTypeCode=${firstCode}" style="margin:0 0 0 0;width:100%;height:100%;"></iframe>
				</td>
			</tr>
		</table>		
	</div>
	
</body>

<script type="text/javascript">
	var zTree;
	$(function(){
		
		menuTableT();
		window.onresize=function(){  
			menuTableT();
		};
	
	});
	
	function menuTableT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginTop=parseInt($(".main_tableDiv").css("margin-top"));
		var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
		$("#leftDiv").css("height",(h-marginTop*2)+"px");
		$("#dataFrameId").css("height",(h-marginTop*2)+"px");
	}
	
	//获取上外边距
	function getMarginTop(){
		var marginTop=parseInt($(".main_tableDiv").css("margin-top"));
		return marginTop;
	}
	
	//获取左外边距
	function getMarginLeft(){
		var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
		return marginLeft;
	}
	
	
	function queryData(trackLimitTypeCode){
		
		$(".new-list li").removeClass("active");
		$("#"+trackLimitTypeCode).addClass("active");
		$("#dataFrame").attr("src",'<%=basePath%>sysTrackLimit/list.do?trackLimitTypeCode='+trackLimitTypeCode);
	}
	
	
</script>

</html>
