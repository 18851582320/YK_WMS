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
			<form action="workFlowType/${method}.do" name="workFlowTypeForm" id="workFlowTypeForm" method="post">
				<input type="hidden" name="workFlowTypeId" id="workFlowTypeId" value="${pd.workFlowTypeId}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 230px;">
					<table>
						<tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">模型分类编码：</td>
						 	<td style="width: 180px;" colspan="4">
						 			<c:choose>
						 			<c:when test="${pd.workFlowTypeId == null}">
						 				<!-- <select id="workFlowTypeCode" name="workFlowTypeCode" class="easyui-combobox" limitToList="true" style="width: 430px;height: 26px;" data-options="required:true">
											<option value="producePlanCreate" >生产计划创建（producePlanCreate）</option>
										</select> -->
										<input class="easyui-textbox" type="text" id="workFlowTypeCode" editable="true"  name="workFlowTypeCode" value="" style="width: 430px;" prompt="" data-options="required:true"  title="模型分类编码">
						 			</c:when>
						 			<c:otherwise>
						 				<input class="easyui-textbox" type="text" id="workFlowTypeCode" editable="false"  name="workFlowTypeCode" value="${pd.workFlowTypeCode}" style="width: 430px;" prompt="" data-options="required:true"  title="模型分类编码">
						 			</c:otherwise>
						 		</c:choose>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">模型分类名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="workFlowTypeName" value="${pd.workFlowTypeName}" prompt="" data-options="required:true"  title="模型分类名称">
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">是否启用：</td>
						 	<td style="width: 180px;">
						 		<select id="isEnable" name="isEnable" class="easyui-combobox" limitToList="true"  style="width: 154px;height: 26px;" data-options="required:true">
									<option value="1" <c:if test="${pd.isEnable == 1}">selected</c:if>>启用</option>
									<option value="0" <c:if test="${pd.isEnable == 0}">selected</c:if>>停用</option>
								</select>
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						  <tr style="height: 70px">
						 	<td style="width: 90px;text-align: right;">备注：</td>
						 	<td style="width: 450px;" colspan="4">
						 		<input class="easyui-textbox" type="text" name="workFlowTypeMemo" value="${pd.workFlowTypeMemo}" style="width: 430px;height: 70px;" prompt="" multiline="true" data-options="required:false"  title="备注">
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
		$("#workFlowTypeForm").form("submit",{
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
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
