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
<body class="addEditDivBody">
	<div class="addEditDiv">
		<div class="">
			<form action="role/${method}.do" name="roleForm" id="roleForm" method="post">
				<input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
				<input type="hidden" name="codeRuleType" id="codeRuleType" value="${codeRuleType}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 100px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 140px;text-align: right;">角色编码：</td>
						 	<td style="width: 180px;">
						 		<c:choose>
						 			<c:when test="${pd.ROLE_ID == null}">
						 				<input class="easyui-textbox" type="text" id="RNUMBER" editable="${codeRuleType eq 1?true:false}"  name="RNUMBER" value="${code}" prompt="" data-options="required:true"  title="编码">
						 			</c:when>
						 			<c:otherwise>
						 				<input class="easyui-textbox" type="text" id="RNUMBER" editable="false"  name="RNUMBER" value="${pd.RNUMBER}" prompt="" data-options="required:true"  title="职务编码">
						 			</c:otherwise>
						 		</c:choose>
						 	</td>
						 </tr>
					     <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 140px;text-align: right;">角色名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="ROLE_NAME" value="${pd.ROLE_NAME}" prompt="" data-options="required:true"  title="角色名称">
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
	</div>
</body>

<script type="text/javascript">
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#roleForm").form("submit",{
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
				}else if("codeRepeat"==data.result){
					$.messager.alert("系统提示","编码重复，请重新保存","error");
					getFormCodeByRule("sys_role","RNUMBER","RNUMBER","codeRuleType");
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
					
				}
			}
		}); 
	}
</script>


</html>
