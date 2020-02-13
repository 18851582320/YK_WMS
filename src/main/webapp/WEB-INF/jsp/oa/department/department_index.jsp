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
		<table style="width:100%;height: 100%;" border="0" id="departmentTable">
			<tr>
				<td style="width:15%;" valign="top" bgcolor="#f5f5f5">
					<div style="width:100%;overflow: auto;" id="treeDiv">
						<ul id="leftTree"></ul>
					</div>
				</td>
				<td style="width: 2%;"></td>
				<td style="width:83%;" id="departmentFrameId">
					<iframe name="departmentFrame" id="departmentFrame" frameborder="0" src="<%=basePath%>department/departmentListIndex.do?PARENT_ID=" style="margin:0 0 0 0;width:100%;height:100%;"></iframe>
				</td>
			</tr>
		</table>		
	</div>
	
</body>

<script type="text/javascript">
	
	$(function(){
		
		departmentTableT();
		window.onresize=function(){  
			departmentTableT();
		};
		
		var treeNodes = eval(${treeNodes});
		$("#leftTree").tree({    
		   	data:treeNodes,
		   	onClick: function(node){
		   		dealSubDepartMent(node.id);  // 在用户点击的时候提示
			}
		}); 
		
		
	});
	
	
	function dealSubDepartMent(DEPARTMENT_ID){
		//$(window.frames["departmentFrame"].document.querySubDepartment(DEPARTMENT_ID));
		$("#departmentFrame").attr("src",'<%=basePath%>department/departmentListIndex.do?PARENT_ID='+DEPARTMENT_ID);
	}
	
	
	
	function departmentTableT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginTop=parseInt($(".main_tableDiv").css("margin-top"));
		var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
		$("#treeDiv").css("height",(h-marginTop*2)+"px");
		$("#departmentFrameId").css("height",(h-marginTop*2)+"px");
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
