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
			<form action="department/${method}.do" name="departmentForm" id="departmentForm" method="post">
				<input type="hidden" name="codeRuleType" id="codeRuleType" value="${codeRuleType}"/>
				<input type="hidden" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}"/>
				<input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${null == pd.PARENT_ID ? PARENT_ID:pd.PARENT_ID}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 210px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">上级部门：</td>
						 	<td style="width: 180px;">
						 		<b>${null == pds.NAME ?'(无) 此项为顶级部门':pds.NAME}</b>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">部门编码：</td>
						 	<td style="width: 180px;">
						 		<c:choose>
						 			<c:when test="${pd.DEPARTMENT_ID == null}">
						 				<input class="easyui-textbox" type="text" id="BIANMA" editable="${codeRuleType eq 1?true:false}"  name="BIANMA" value="${code}" prompt="" data-options="required:true"  title="部门编码">
						 			</c:when>
						 			<c:otherwise>
						 				<input class="easyui-textbox" type="text" id="BIANMA" editable="false"  name="BIANMA" value="${pd.BIANMA}" prompt="" data-options="required:true"  title="部门编码">
						 			</c:otherwise>
						 		</c:choose>
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">部门名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="NAME" name="NAME" value="${pd.NAME}" prompt="" data-options="required:true"  title="部门名称">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">英文名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="NAME_EN" name="NAME_EN" value="${pd.NAME_EN}" prompt="" data-options="required:false"  title="英文名称">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">备注：</td>
						 	<td style="width: 180px;" colspan="4">
						 		<input class="easyui-textbox" type="text" id="BZ" name="BZ" value="${pd.BZ}" style="width: 430px;height:70px" prompt="" data-options="required:false,multiline:true"  title="说明">
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
		$("#departmentForm").form("submit",{
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
					getFormCodeByRule("oa_department","BIANMA","BIANMA","codeRuleType");
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
