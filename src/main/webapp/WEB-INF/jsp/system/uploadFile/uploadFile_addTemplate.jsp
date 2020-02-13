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
	<div class="addEditDiv" style="background-color:#fafafa;">
		<div class="">
			<form action="uploadFile/${method}.do" name="dataForm" id="dataForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="tableName" id="tableName" value="${pd.tableName}"/>
				<input type="hidden" name="tableKeyValue" id="tableKeyValue" value="${pd.tableKeyValue}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height:460px;">
					<table>
						<tr class="editMiddleTr" style="height: 15px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">附件类型:&nbsp;</td>
							<td class="editTdValue" >
								<select id="fileType" name="fileType" class="easyui-combobox" editable="false" limitToList="true"  style="width: 180px;height: 26px;" data-options="required:true">
									<option value="1" <c:if test="${pd.fileType == '1'}">selected</c:if>>日常</option>
									<option value="2" <c:if test="${pd.fileType == '2'}">selected</c:if>>售前</option>
									<option value="3" <c:if test="${pd.fileType == '3'}">selected</c:if>>项目</option>
									<option value="4" <c:if test="${pd.fileType == '4'}">selected</c:if>>售后</option>
									<option value="5" <c:if test="${pd.fileType == '5'}">selected</c:if>>其他</option>
								</select>
							</td>
						</tr>
						<tr class="editMiddleTr" style="height: 15px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">附件:&nbsp;</td>
							<td class="editTdValue" colspan="7">
								<input class="easyui-filebox" id="uploadFile" name="uploadFile" multiple="true" style="width: 400px" value="" data-options="required:true" buttonText="请选择文件">
							</td>
						</tr>
					</table>
				</div>
				
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="parent.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	
	$(function(){
		setAddEditDivHeight();
	});
	
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#dataForm").form("submit",{
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
					parent.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>

</html>
