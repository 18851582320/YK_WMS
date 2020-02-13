<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
  	<base href="<%=basePath%>">
    <title>${pd.SYSNAME}</title>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="static/image/favicon.ico" type="image/x-icon" rel="shortcut icon">
	<link rel="stylesheet" href="static/iconfont/iconfont.css">	
	<link rel="stylesheet" href="plugins/ace/css/aceMy.css" class="ace-main-stylesheet" id="main-ace-style" />
	<link rel="stylesheet" type="text/css" href="static/login/css/base.css">
	<link rel="stylesheet" type="text/css" href="static/login/css/login.css">
	
	<script src="static/js/jquery-1.11.3.min.js"></script>
	<script src="static/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
  </head>
  <body>
	<div class="banner">
		<div class="lg-box">
			<div class="lg-label"><h4>云科WMS系统</h4></div>
			<div class="lg-username input-item clearfix">
				<i class="iconfont icon-yonghuming15 bigger-110" style="color: #05a7d8;font-weight: bold;"></i>
				<input type="text" id="loginname" name="loginname" placeholder="请输入用户名">
			</div>
			<div class="lg-password input-item clearfix">
				<i class="iconfont icon-mima2 bigger-110" style="color: #05a7d8;" id="btn"></i>
				<input type="password" id="password" name="password" placeholder="请输入密码" >
			</div>
			<div class="enter" style="text-align: center;">
				<a class="btn btn-mini btn-login" id="loginBtn" onClick="doLogin()">登&nbsp;录</a>
			</div>
		</div>
	</div>
	
	<div class="login-ft">
		<div class="ft-inner">
			<div class="about-us">
				<a href="http://www.jsniuyun.com:800/#" target="_blank">关于我们</a>
			</div>
			<div class="address">Copyright&nbsp;©&nbsp;2019&nbsp;-&nbsp;2025&nbsp;无锡云科智造信息技术有限公司&nbsp;版权所有</div>
			<div class="other-info">E-mail：zhurj.yk@foxmail.com&nbsp;&nbsp;Add：无锡市经济开发区华庄街道明芳西路27号&nbsp;&nbsp;Tel：13376200913&nbsp;&nbsp;</div>
		</div>
	</div>
	
	<script type="text/javascript">
		jQuery(".banner").slide({ titCell:".hd ul", mainCell:".bd ul", effect:"fold",  autoPlay:true, autoPage:true, trigger:"click" });
	</script>
	
	<c:if test="${'2' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg(){
				$.messager.alert("提示","您被系统管理员强制下线或您的帐号在别处登录","info");
			}
		</script>
	</c:if>
	
  </body>
  
  <script type="text/javascript">
  	//显示密码
	  $(function(){
	  	$("#btn").mousedown(function(){
	  		$("#password").attr("type", "text");
	  	});
	  	$("#btn").mouseup(function(){
	  		$("#password").attr("type", "password");
	  	});
	  });
  
	//注册回车登录事件
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#loginBtn").trigger("click");
		}
	});
  
    //登录
  	function doLogin(){
  		if(loginCheck()){
  			var loginname=$("#loginname").val();
  			var password=$("#password").val();
  			$.ajax({
  				type:"POST",
  				url:"login_login",
  				data:{loginname:loginname,password:password},
  				dataType:"json",
  				cache:false,
  				success:function(data){
  					if("success"==data.result){
  						saveCookie();
						window.location.href="main/index";
  					}else if("stafferror"==data.result){
  						$("#loginname").tips({
							side : 1,
							msg : "该用户尚未绑定员工信息",
							bg : '#FF5080',
							time : 15
						});
						$("#loginname").focus();
  					}else if("outDate"==data.result){
  						$("#loginname").tips({
							side : 1,
							msg : "软件到期，请联系相关人员",
							bg : '#FF5080',
							time : 15
						});
						$("#loginname").focus();
  					}else if("usererror" == data.result){
						$("#loginname").tips({
							side : 1,
							msg : "用户名或密码有误",
							bg : '#FF5080',
							time : 15
						});
						$("#loginname").focus();
					}else if("codeerror" == data.result){
						$("#code").tips({
							side : 1,
							msg : "验证码输入有误",
							bg : '#FF5080',
							time : 15
						});
						$("#code").focus();
					}else{
						$("#loginname").tips({
							side : 1,
							msg : "缺少参数",
							bg : '#FF5080',
							time : 15
						});
						$("#loginname").focus();
					}
  				}
  			});
  			
  			
  		}
  	}
  
  
	/**
	 *客户端验证
	 */
	function loginCheck(){
		var loginName=$("#loginname").val();
		var password=$("#password").val();
		
		if($.trim(loginName)==""){
			$("#loginname").tips({
				side:2,
				msg:"用户名不能为空！",
				bg:"#AE81FF",
				time:3
			});
			
			$("#loginname").focus();
			return false;
		}
		
		if($.trim(password)==""){
			$("#password").tips({
				side:2,
				msg:"密码不能为空！",
				bg:"#AE81FF",
				time:3
			});
			
			$("#password").focus();
			return false;
		}
		
		return true;
	}
	
	
	jQuery(function() {
		var loginname = $.cookie('loginname');
		if (typeof(loginname) != "undefined") {
			$("#loginname").val(loginname);
			$("#saveid").attr("checked", true);
		}
	});
	
	function savePaw() {
		if (!$("#saveid").attr("checked")) {
			$.cookie('loginname', '', {
				expires : -1
			});
			$("#loginname").val('');
		}
	}

	function saveCookie() {
		if ($("#saveid").attr("checked")) {
			$.cookie('loginname', $("#loginname").val(), {
				expires : 7
			});
		}
	}
  
  
  </script>
  
</html>
