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
	<div class="main_tableDiv">
		<table style="width:100%;height: 100%;" border="0" id="menuTable">
			<tr>
				<td style="width:15%;" valign="top" bgcolor="#f5f5f5">
					<div style="width:100%;overflow: auto;" id="treeDiv">
						<ul id="leftTree" class="tree"></ul>
					</div>
				</td>
				<td style="width: 2%;"></td>
				<td style="width:83%;" id="menuFrameId">
					<iframe name="menuFrame" id="menuFrame" frameborder="0" src="<%=basePath%>menu/menuListIndex.do?PARENT_ID=0" style="margin:0 0 0 0;width:100%;height:100%;"></iframe>
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
		
		var treeNodes = eval(${treeNodes});
		$("#leftTree").tree({    
		   	data:treeNodes,
		   	onClick: function(node){
		   		dealMenu(node.id);  // 在用户点击的时候提示
			}
		}); 
	
	});
	
	function menuTableT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginTop=parseInt($(".main_tableDiv").css("margin-top"));
		var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
		$("#treeDiv").css("height",(h-marginTop*2)+"px");
		
		$("#menuFrameId").css("height",(h-marginTop*2)+"px");
		
	}
	
	
	//修改菜单
	function dealMenu(MENU_ID){
		 var menuEditDiag = new top.Dialog();
		 menuEditDiag.Drag=true;
		 menuEditDiag.Title ="编辑菜单";
		 menuEditDiag.URL = '<%=basePath%>menu/toEdit.do?MENU_ID='+MENU_ID;
		 menuEditDiag.Width = 400;
		 menuEditDiag.Height = 300;
		 menuEditDiag.CancelEvent = function(){ //关闭事件
			 if(menuEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 //$("#menuInfoTable").datagrid("reload");//重新加载数据
			 }
			 menuEditDiag.close();
		 };
		 menuEditDiag.show();
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
