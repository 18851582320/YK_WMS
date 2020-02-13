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
			<form action="model/${pd.method}" name="Form" id="Form" method="post">
				<input type="hidden" name="BUTTON_ID" id="id" value="${pd.BUTTON_ID}"/>
				<div id="saveEditCenter" class="saveEditCenter">
					<table>
						 <tr style="height: 30px">
						 	<td style="width: 30%;text-align: right;">流程名称：</td>
						 	<td style="width: 70%;">
						 		<input class="easyui-textbox" type="text" name="PROCESS_NAME" value="${pd.PROCESS_NAME}" prompt="" data-options="required:true,validType:'length[1,255]'" style="width: 162px;"  title="流程名称">
						 	</td>
						 </tr>
						 <tr style="height: 30px">
						 	<td style="width: 30%;text-align: right;">流程描述：</td>
						 	<td style="width: 70%;">
						 		<input class="easyui-textbox" type="text" name="PROCESS_DESCRIPTION" value="${pd.PROCESS_DESCRIPTION}"  prompt="" data-options="required:true,validType:'length[1,255]'" style="width: 162px;"  title="流程描述">
						 	</td>
						 </tr>
						 <tr style="height: 30px">
						 	<td style="width: 30%;text-align: right;">流程版本：</td>
						 	<td style="width: 70%;">
						 		<input class="easyui-numberbox" type="number" name="MENU_ORDER" id="menuOrder" value="${pd.PROCESS_VERSION}" prompt="" title="请输入正整数" data-options="required:true,min:1,max:20,precision:0" />
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
	$(function(){
		//设置中间区域的高度		
		setAddEditDivHeight();
	});
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#Form").form("submit",{
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
