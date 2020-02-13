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
			<form action="${controllerName}/uploadFileSave.do" name="form" id="form" method="post" enctype="multipart/form-data">
				<input type="hidden" name="tableName" id="tableName" value="${pd.tableName}"/>
				<input type="hidden" name="tableKeyValue" id="tableKeyValue" value="${pd.tableKeyValue}"/>
				<div id="saveEditCenter" class="saveEditCenter">
					<table>
						 <tr style="height: 40px">
						 	<td style="width: 10px;"></td>
						 	<td style="width: 200px;">
						 		<input class="easyui-filebox" id="uploadFile" name="uploadFile" multiple="true" style="width: 380px" value=""  data-options="required:true" buttonText="请选择文件">
						 	</td>
						 </tr>
					</table>
				</div>
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;上传</a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
				</div>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript">
	$(function(){
		//设置中间区域的高度		
		setAddEditDivHeight();
	});
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#form").form("submit",{
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
					$.messager.alert("系统提示","导入出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
