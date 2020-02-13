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
			<form action="folderDoc/${method}.do" name="dataForm" id="dataForm" method="post" enctype="multipart/form-data" >
				<input type="hidden" name="updateUser" id="updateUser" value="${pd.updateUser}"/>
				<input type="hidden" name="createUser" id="createUser" value="${pd.createUser}"/>
				<input type="hidden" name="folderId" id="folderId" value="${pd.folderId}"/>
				<input type="hidden" name="folderDocId" id="folderDocId" value="${pd.folderDocId}"/>
				<input type="hidden" name="dealType" id="dealType" value="1"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 290px;">
					<table>
						<tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">当前文件夹：</td>
						 	<td style="width: 180px;">
						 			${pd.folderName}
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">文件上传：</td>
						 	<td style="width: 180px;" colspan="4">
						 		<input class="easyui-filebox" id="uploadFile" name="uploadFile" multiple="true" style="width: 560px" value="" data-options="required:false" buttonText="请选择文件">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">创建人：</td>
						 	<td style="width: 180px;">
						 	<input class="easyui-textbox" type="text" name="createUserName" value="${pd.createUserName}" prompt="" editable="false" data-options="required:false"  title="创建人员">					 		
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">创建时间：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="createTime" value="${pd.createTime}" prompt="" editable="false"  data-options="required:false"  title="创建时间">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">最后修改人：</td>
						 	<td style="width: 180px;">
						 	<input class="easyui-textbox" type="text" name="updateUserName" value="${pd.updateUserName}" prompt="" editable="false" data-options="required:false"  title="最后修改人">					 		
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">最后修改时间：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="updateTime" value="${pd.updateTime}" prompt="" editable="false"  data-options="required:false"  title="最后修改时间">
						 	</td>
						 </tr>
						 <tr style="height: 40px">
						 	<td style="width: 90px;text-align: right;">处理方式：</td>
						 	<td style="width: 180px;" colspan="4">
						 		<input name="Deal_radio" type="radio"  id="Deal_radio" checked="checked" onclick="setType(1);"/>有相同文件时，保留先前所有版本文件，生成新版本文件
								<br/><input name="Deal_radio" type="radio"  id="Deal_radio" onclick="setType(0);"/>有相同文件时，删除所有先前版本文件，生成新版本文件
						 	</td>
						 </tr>
						   <tr style="height: 5px"/>
						  <tr style="height: 70px">
						 	<td style="width: 90px;text-align: right;">备注：</td>
						 	<td style="width: 450px;" colspan="4">
						 		<input class="easyui-textbox" type="text" name="folderDocMemo" value="${pd.folderDocMemo}" style="width: 492px;height: 70px;" prompt="" multiline="true" data-options="required:false"  title="备注">
						
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
	
	function setType(stateValue){
		$("#dealType").val(stateValue);
	}
	
	
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
					top.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
