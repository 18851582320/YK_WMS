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
			<form action="${controllerName}/readUploadFile.do" name="form" id="form" method="post" enctype="multipart/form-data">
				<input type="hidden" name="suffix" id="suffix" value="${suffix}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 160px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr style="height: 50px">
						 	<td style="width: 10px;"></td>
						 	<td style="width: 200px;">
						 		<input class="easyui-filebox" id="uploadFile" name="uploadFile" style="width: 380px" value=""  data-options="required:true" buttonText="请选择文件">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
					</table>
				</div>
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;导入</a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-purple" onclick="window.location.href='<%=basePath%>file/downExcel.do?controllerName=${controllerName}'"><i class="iconfont icon-yunxiazai"></i>&nbsp;下载模板</a>
				</div>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript">
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#form").form("submit",{
			onSubmit: function(){
				var isValid = $(this).form("validate");
				if (!isValid){
					top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
				}
				
				//验证成功后，判断文件类型
				if(isValid){
					var suffix=$("#suffix").val();
					
					var uploadFile=$("#uploadFile").textbox("getValue");
					var uploadFileType=uploadFile.substr(uploadFile.lastIndexOf(".")+1).toLowerCase();//获得文件后缀名
					var chkTag=false;
					if(suffix){
						var suffixArry=suffix.split(",");
						for(var i in suffixArry){
							if(uploadFileType==suffixArry[i]){
								chkTag=true;
								break;
							}
						}
					}
					
					if(!chkTag){
						top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
						$.messager.alert("系统提示","上传文件的类型只能为："+suffix,"error");
						isValid=false;
					}
					
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
