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
	<div class="addEditDiv">
		<form action="user/setLanguage.do" name="pwdForm" id="pwdForm" method="post">
			<input type="hidden" name="userLanguage" id="userLanguage" value="${language }"/>
			<div id="saveEditCenter" class="saveEditCenter">
				<table>
					 <tr>
					 	<td style="width: 30%;text-align: right;"></td>
					 	<td style="width: 70%;">
					 		<br>
					 		<input name="language_radio" type="radio"  id="language_radio" <c:if test="${language=='zh_CN'}">checked="checked"</c:if> onclick="setLanguage('zh_CN');"/>Chinese
							<br>
							<br>
							<input name="language_radio" type="radio"  id="language_radio" <c:if test="${language=='en'}">checked="checked"</c:if> onclick="setLanguage('en');"/>English
							<br>
							<br>
							<input name="language_radio" type="radio"  id="language_radio" <c:if test="${language=='de'}">checked="checked"</c:if> onclick="setLanguage('de');"/>German
					 	</td>
					 </tr>
				</table>
			</div>
			<div class="addEditButtonDiv">
				<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
				&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript">
	$(function(){
		//设置中间区域的高度		
		setAddEditDivHeight();
	});
	
	
	function setLanguage(language){
		$("#userLanguage").val(language);
	}
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#pwdForm").form("submit",{
			onSubmit: function(){
				var isValid = $(this).form("validate");
				if (!isValid){
					top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
				}
				return isValid;//返回false终止表单提交
			},
			success:function(data){
				top.closeMsgProgress();//关闭进度条;
				var data = eval("("+data+")");  
				if("success"==data.result){
					$("#saveEditCenter").hide();
					top.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错："+data.errmsg,"error");
				}
			}
		}); 
	}
</script>


</html>
