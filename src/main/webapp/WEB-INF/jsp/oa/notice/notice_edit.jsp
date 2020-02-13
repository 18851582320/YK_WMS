<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
			<form action="notice/${method}.do" name="noticeForm" id="noticeForm" method="post">
				<input type="hidden" name="noticeId" id="noticeId" value="${pd.noticeId}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 460px;">
					<table>
						<tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;"><spring:message code="notice_noticeTitle" />：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="noticeTitle"   name="noticeTitle" value="${pd.noticeTitle}" prompt="" data-options="required:true"  >
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;"><spring:message code="notice_isStop" />：</td>
						 	<td style="width: 180px;">
						 		<select id="isStop" name="isStop" class="easyui-combobox" limitToList="true"  style="width: 154px;height: 26px;" data-options="required:true">
									<option value="0" <c:if test="${pd.isStop == 0}">selected</c:if>>否</option>
									<option value="1" <c:if test="${pd.isStop == 1}">selected</c:if>>是</option>
								</select>
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;"><spring:message code="notice_endTime" />：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-datetimebox" type="text" name="endTime" value="${pd.endTime}" prompt="" editable="false"  data-options="required:true"  >
						 	</td>
						 </tr>
						 <tr style="height: 15px"/>
						 <tr style="height: 70px">
						 	<td style="width: 90px;text-align: right;"><spring:message code="notice_noticeContent" />：</td>
						 	<td style="width: 900px;" colspan="7">
						 		 <input class="easyui-textbox" type="text" name="noticeContent" value="${pd.noticeContent}" style="width: 825px;height: 360px;" prompt="" multiline="true" data-options="required:true" >
						 	</td>
						  </tr>
					</table>
				</div>
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;<spring:message code="common_submit" /></a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;<spring:message code="common_cancel" /></a>
				</div>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript">
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#noticeForm").form("submit",{
			onSubmit: function(){
				var isValid = $(this).form("validate");
				if (!isValid){
					top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
				}
				
				return isValid;//返回false终止表单提交
			},
			success:function(data){
				top.closeMsgProgress();//关闭进度条
				var data = eval("("+data+")");  
				if("success"==data.result){
					$("#saveEditCenter").hide();
					top.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
