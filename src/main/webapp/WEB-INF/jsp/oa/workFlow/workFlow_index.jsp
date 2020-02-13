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
	<div class="main_tableDiv">
		<table style="width:100%;height: 100%;" border="0" id="workFlowTable">
			<tr>
				<td style="width:15%;" valign="top" bgcolor="#f5f5f5">
					<div style="width:100%;overflow: auto;" id="treeDiv">
						<ul id="leftTree"></ul>
					</div>
				</td>
				<td style="width: 2%;"></td>
				<td style="width:83%;" id="workFlowFrameId">
					<iframe name="workFlowFrame" id="workFlowFrame" frameborder="0" src="<%=basePath%>workFlow/workFlowListIndex.do?workFlowTypeId=" style="margin:0 0 0 0;width:100%;height:100%;"></iframe>
				</td>
			</tr>
		</table>		
	</div>
	
</body>

<script type="text/javascript">
	
	$(function(){
		
		workFlowTableT();
		window.onresize=function(){  
			workFlowTableT();
		};
		
		$("#leftTree").tree({    
			url:'<%=basePath%>workFlow/getTree.do',
		   	onClick: function(node){
		   		dealSubWorkFlow(node.id);  // 在用户点击的时候提示
			}
		}); 
		
		
	});
	
	
	function dealSubWorkFlow(workFlowTypeId){
		//$(window.frames["workFlowFrame"].document.querySubworkFlow(workFlow_ID));
		$("#workFlowFrame").attr("src",'<%=basePath%>workFlow/workFlowListIndex.do?workFlowTypeId='+workFlowTypeId);
	}
	
	
	
	function workFlowTableT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginTop=parseInt($(".main_tableDiv").css("margin-top"));
		var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
		$("#treeDiv").css("height",(h-marginTop*2)+"px");
		$("#workFlowFrameId").css("height",(h-marginTop*2)+"px");
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
	
</script>

</html>
