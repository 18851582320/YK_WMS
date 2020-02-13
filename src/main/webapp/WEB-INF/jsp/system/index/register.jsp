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
			<form action="app/saveRegister.do" name="dataForm" id="dataForm" method="post">
				<div id="saveEditCenter" class="saveEditCenter" style="height: 240px;">
					<table>
						<tr class="editMiddleTr" style="height: 10px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">公司名称:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="companyName"  name="companyName" editable="true" value="" style="width:230px;" data-options="required:true" prompt="">
							</td>
						</tr>
						<tr class="editMiddleTr" style="height: 10px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">联系人:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="linkman"  name="linkman" editable="true" value="" style="width:230px;" data-options="required:true" prompt="">
							</td>
						</tr>
						<tr class="editMiddleTr" style="height: 10px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">联系人手机:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="phone"  name="phone" editable="true" value="" style="width:230px;" data-options="required:true,validType:'phone'" prompt="">
							</td>
						</tr>
						<tr class="editMiddleTr" style="height: 10px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">密码:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="PASSWORD"  name="PASSWORD" editable="true" value="" style="width:230px;" data-options="required:true,validType:['length[0,20]']" prompt="">
							</td>
						</tr>
						<tr class="editMiddleTr" style="height: 10px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">验证码:&nbsp;</td>
							<td class="editTdValue" style="width: 330px;">
								<input class="easyui-textbox" type="text" id="checkCode"  name="checkCode" editable="true" value="" style="width:230px;" data-options="required:true" prompt="">
								<input type="button" id="checkCodeBtn" class="btn btn-mini btn-info" value="获取验证码" style="width: 80px;text-align: center;" onclick="getCheckCode();" ></input>
							</td>
						</tr>
					</table>
				</div>
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;注册</a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="parent.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
				</div>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript">
	
	var count = 120; //间隔函数，1秒执行
	var InterValObj; //timer变量，控制时间
	var curCount;//当前剩余秒数
	var reg = /^0?(13[0-9]|15[0-9]|18[0-9]|14[0-9])[0-9]{8}$/; 

	
	
	function getCheckCode(){
		curCount = count;
		var phone=$("#phone").textbox("getValue");
		if(!reg.test(phone)){
			$.messager.alert("提示","请输入正确的联系人手机！","error");
		}else{
			//设置button效果，开始计时
			$("#checkCodeBtn").attr("disabled", "true");
			$("#checkCodeBtn").val(curCount + "秒再获取");
			InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
			
			$.ajax({
				url:"<%=basePath%>app/getCheckCode.do",
				type:"post",
				async:false,
				data:{phone:phone},
				dataType:"json",
				success:function(data){
					if(data){
						if("success"==data.result){
							$.messager.alert("系统提示","验证码发送成功！","info");
						}else if(data.result=="errSms"){
							$.messager.alert("系统提示","验证码发送失败！","error");
						}else if(data.result=="hasReg"){
							$.messager.alert("系统提示","该手机号已经注册过！","error");
						}else{
							$.messager.alert("系统提示","系统出错:"+data.errmsg,"error");
						}
						
						
						if(data.result!="success"){
							window.clearInterval(InterValObj);//停止计时器
							$("#checkCodeBtn").removeAttr("disabled");//启用按钮
							$("#checkCodeBtn").val("获取验证码");
						}
					}
				}
			});
			
		}
	}
	
	
	function SetRemainTime() {
		if (curCount == 0) {                
			window.clearInterval(InterValObj);//停止计时器
			$("#checkCodeBtn").removeAttr("disabled");//启用按钮
			$("#checkCodeBtn").val("重新发送");
		}
		else {
			curCount--;
			$("#checkCodeBtn").val(curCount + "秒再获取");
		}
	} 

	
	
	//保存
	function save(){
		parent.showMsgProgress(); //显示进度条
		$("#dataForm").form("submit",{
			onSubmit: function(){
				var isValid = $(this).form("validate");
				if (!isValid){
					parent.closeMsgProgress(); //如果表单是无效的的则关闭进度条
				}
				return isValid;//返回false终止表单提交
			},
			success:function(data){
				parent.closeMsgProgress();//关闭进度条
				var data = eval("("+data+")");  
				if("success"==data.result){
					$.messager.alert("系统提示","注册成功","info");
					$("#saveEditCenter").hide();
					parent.Dialog.close();
				}else if("errPhone"==data.result){
					$.messager.alert("系统提示","系统尚未给该手机发送过任何验证码！","error");
				}else if("errCheckCode"==data.result){
					$.messager.alert("系统提示","验证码无效！","error");
				}else if("errChaoShi"==data.result){
					$.messager.alert("系统提示","验证码已过期！","error");
				}else if("hasReg"==data.result){
					$.messager.alert("系统提示","该手机已经注册过！","error");
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
