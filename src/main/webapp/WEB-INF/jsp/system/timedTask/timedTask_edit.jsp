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
			<form action="timedTask/${method}.do" name="timedTaskForm" id="timedTaskForm" method="post">
				<input type="hidden" name="timedTaskId" id="timedTaskId" value="${pd.timedTaskId}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 310px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 140px;text-align: right;">任务名：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="timedTaskName"   name="timedTaskName" value="${pd.timedTaskName}" prompt="" data-options="required:true"  title="">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">任务别名：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="timedTaskAliasName"   name="timedTaskAliasName" value="${pd.timedTaskAliasName}" prompt="" data-options="required:true"  title="">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">任务分类：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="timedTaskType"   name="timedTaskType" value="${pd.timedTaskType}" prompt="" data-options="required:true"  title="">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">任务类Class：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="timedTaskClass"   name="timedTaskClass" value="${pd.timedTaskClass}" prompt="" data-options="required:true"  title="">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">执行方式：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="cronExpression"   name="cronExpression" value="${pd.cronExpression}" prompt="" data-options="required:true"  title="">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">执行方式：</td>
						 	<td style="width: 180px;">
						 		<select id="timedTaskState" name="timedTaskState" class="easyui-combobox" limitToList="true"  style="width: 154px;height: 26px;" data-options="required:true">
									<option value="1" <c:if test="${pd.timedTaskState == 1}">selected</c:if>>启用</option>
									<option value="0" <c:if test="${pd.timedTaskState == 0}">selected</c:if>>停用</option>
								</select>
						 	</td>
						 </tr>
					     <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">描述：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="timedTaskMemo" name="timedTaskMemo" value="${pd.timedTaskMemo}" style="height: 70px;" prompt="" multiline="true" data-options="required:false"  title="备注">
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
		$("#timedTaskForm").form("submit",{
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
