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
			<form action="workFlow/${method}.do" name="workFlowForm" id="workFlowForm" method="post">
				<input type="hidden" name="workFlowId" id="workFlowId" value="${pd.workFlowId}"/>
				<input type="hidden" name="versionNum" id="versionNum" value="${pd.workFlowId==null?0:pd.versionNum}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 260px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">流程名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="workFlowName" value="${pd.workFlowName}" prompt="" data-options="required:true"  title="流程名称">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">流程类型：</td>
						 	<td style="width: 180px;">
						 		<select id="workFlowTypeId" name="workFlowTypeId" class="easyui-combobox" limitToList="true" style="width: 154px;height: 26px;" data-options="required:true">
									<c:forEach items="${wfList}" var="wf">
										<option value="${wf.workFlowTypeId }" <c:if test="${wf.workFlowTypeId == pd.workFlowTypeId }">selected</c:if>>${wf.workFlowTypeName}</option>
									</c:forEach>
								</select>
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">key：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="workFlowKey" value="${pd.workFlowKey}" prompt="" data-options="required:true"  title="">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">使用状态：</td>
						 	<td style="width: 180px;">
						 		<select id="isEnable" name="isEnable" class="easyui-combobox" limitToList="true" style="width: 154px;height: 26px;" data-options="required:true">
									<option value="1" <c:if test="${pd.isEnable == 1 or pd.isEnable ==null }">selected</c:if>>可用</option>
									<option value="0" <c:if test="${pd.isEnable == 0}">selected</c:if>>禁用</option>
								</select>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">描述：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="workFlowMemo" name="workFlowMemo" multiline="true" style="height: 70px;"  value="${pd.workFlowMemo }" title="备注" data-options="required:false">
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
		$("#workFlowForm").form("submit",{
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
				}else if("nameRepeat"==data.result){
					$.messager.alert("系统提示","流程名称重复","error");
				}else if("keyRepeat"==data.result){
					$.messager.alert("系统提示","流程key重复","error");
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
