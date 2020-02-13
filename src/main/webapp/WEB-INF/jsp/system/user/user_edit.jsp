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
			<form action="user/${method}.do" name="userForm" id="userForm" method="post">
				<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
				<input type="hidden" name="codeRuleType" id="codeRuleType" value="${codeRuleType}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 300px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">角色：</td>
						 	<td style="width: 180px;">
						 		<select id="ROLE_ID" name="ROLE_ID" class="easyui-combobox" limitToList="true" style="width: 154px;height: 26px;" data-options="required:true">
									<c:forEach items="${roleList}" var="role">
										<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
									</c:forEach>
								</select>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">用户名：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="USERNAME" name="USERNAME" editable="${pd.USER_ID ne null?false:true}"  value="${pd.USERNAME }" title="用户名" data-options="required:true,validType:'length[1,50]'">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">编号：</td>
						 	<td style="width: 180px;">
						 		<c:choose>
						 			<c:when test="${pd.USER_ID == null}">
						 				<input class="easyui-textbox" type="text" id="NUMBER"   name="NUMBER"  editable="${codeRuleType eq 1?true:false}" value="${code}" prompt="" data-options="required:true"  title="部门编码">
						 			</c:when>
						 			<c:otherwise>
						 				<input class="easyui-textbox" type="text" id="NUMBER" name="NUMBER" editable="false"  value="${pd.NUMBER }" title="编号" data-options="required:true">
						 			</c:otherwise>
						 		</c:choose>
						 	</td>
						 </tr>
						 
						 <c:if test="${pd.USER_ID eq null}">
							 <tr style="height: 5px"/>
							 <tr>
							 	<td style="width: 90px;text-align: right;">密码：</td>
							 	<td style="width: 180px;">
							 		<input class="easyui-textbox" type="text" id="PASSWORD" name="PASSWORD"  value="" title="密码" data-options="required:true">
							 	</td>
							 </tr>
						 </c:if>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 30%;text-align: right;">邮箱：</td>
						 	<td style="width: 70%;">
						 		<input class="easyui-textbox" type="text" id="EMAIL" name="EMAIL"  value="${pd.EMAIL }" title="邮件" data-options="required:false,validType:'email'">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 30%;text-align: right;">备注：</td>
						 	<td style="width: 70%;">
						 		<input class="easyui-textbox" type="text" id="BZ" name="BZ"  value="${pd.BZ }" title="备注" data-options="required:false">
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
		$("#userForm").form("submit",{
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
				}else if(data.result=="userNameRepeat"){
					$.messager.alert("系统提示","用户名重复！","error");
				}else if(data.result=="numberRepeat"){
					$.messager.alert("系统提示","编号重复,请重新保存！","error");
					getFormCodeByRule("sys_user","NUMBER","NUMBER","codeRuleType");
				}else{
					$.messager.alert("系统提示","保存出错："+data.errmsg,"error");
				}
			}
		}); 
	}
</script>


</html>
