<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>江苏海宝智造科技股份有限公司</title>
	<style type="text/css">
		
		.top{ margin: 0 auto; width:100vmin; position: relative;}
		.top img{ width: 100vmin;}
		.top .erm{ position: absolute; top:23vmin; left: 15.2vmin;}
		.top .erm img{ width: 26.875vmin; margin-right:14vmin;}
		.mid{ margin: 0 auto; width:100vmin; background-color: #f9f9f9;}
		.mid img{ width: 100vmin;}
		.app{ margin: 0 auto; width:100vmin; position: relative;}
		.app img{ width: 100vmin;}
		.app .first{ position: absolute; top:31vmin; left: 43vmin; right: 3vmin;} 
		.app .first p{ font-size:3.75vmin ; line-height: 5vmin; color: #636363;}
		.app .first p.color{ color: #3C8DE6; font-weight: 600;  margin-bottom: 2vmin;}
		.app .second{ position: absolute; top:78vmin; left: 6vmin; right: 43vmin;}
		.app .second p{  font-size:3.75vmin ; line-height: 5vmin;  color: #636363;}
		.app .second p.color{  color: #3C8DE6; font-weight: 600; margin-bottom: 2vmin;}
		.pc{ margin: 0 auto; width:100vmin; background-color: #f9f9f9;}
		.pc p{ color: #3c8de6; font-size:4vmin ; font-weight: 600; margin-left:3.1vmin ; margin-top: 3vmin; margin-bottom: 3vmin; line-height: 6vmin; }
		.pc p.color{ color: #525252;}
		.pc a{ color: #525252; }
		.pc img{ width: 100vmin;}
		.tui{ margin: 0 auto; width:100vmin; background-color: #fdfdfd;}
		.tui img{ width: 100vmin;}
		.tui p{ margin-top: 3vmin; margin-bottom: 3vmin; font-size: 3vmin;  margin-left: 4vmin; margin-right: 4vmin; line-height: 5vmin;}
		.tui span{ font-size:1.8vmin ; color: #646464; margin-top: 4vmin; margin-bottom: 3vmin; display:block; text-align: center;}
		.bottom{margin: 0 auto; width:100vmin; background-color: #3a3a3a; }
		.bottom img{ width: 28.59375vmin; padding-top: 4vmin; padding-left: 3vmin;}
		.bottom .right{ float: right; margin-top: -44vmin; margin-left: 35vmin;}
		.bottom p.little{ font-size: 2vmin; margin-left:10vmin;}
		.bottom p{ font-size: 2.8vmin; color: #cccccc; line-height: 5vmin; margin-right: 3vmin;}
		.bottom p.weight{ font-size: 4vmin; font-weight: 600; color: #FFFFFF; margin-bottom: 3vmin;}
	</style>

  </head>
  <body style="margin-left:0vmin;">
<div class="top">
			<img src="static/login/helloImages/top-img.jpg" >
			<div class="erm">
				<img src="static/login/helloImages/img-1.jpg" >
				<img src="static/login/helloImages/img-2.jpg" >
			</div>
		</div>
		<div class="mid">
			<img src="static/login/helloImages/add-1.jpg" >
		</div>
		    <div class="mid">
      <img src="static/login/helloImages/add-2.jpg" >
    </div>
		<div class="mid">
			<img src="static/login/helloImages/icon-mid.jpg" >
		</div>
    <div class="mid">
      <img src="static/login/helloImages/add-3.jpg" >
    </div>		
		
		<div class="app">
			<img src="static/login/helloImages/app-login.jpg" >
			<div class="first">
				<p class="color">第一步：</p>
				<p>点击设置按钮，弹出服务器配置页面</p>
			</div>
			<div class="second">
				<p class="color">第二步，输入：</p>
				<p>服务器地址:119.3.6.12，端口号:8871,点击确定,用手机注册体验。体验账号:guest，密码:111111 </p>
			</div>
		</div>
		<div class="pc">
			<img src="static/login/helloImages/pc-login.jpg" >
			<p>PC端服务器地址：<a href="http://119.3.6.12:8871/YK_WMS/">http://119.3.6.12:8871/YK_WMS/</a></p>
			<p class="color">点击注册按钮，用手机注册体验。体验账号：guest，密码：111111</p>
		</div>
    <div class="mid">
      <img src="static/login/helloImages/add-4.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-5.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-6.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-7.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-8.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-9.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-10.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-11.jpg" >
    </div>
    <div class="mid">
      <img src="static/login/helloImages/add-12.jpg" >
    </div>  
    <div class="mid">
      <img src="static/login/helloImages/add-13.jpg" >
    </div>
	<div class="bottom clearfix">
	   <img src="static/login/helloImages/erm.jpg" >
	   <p class="little">微信公众号</p>
	   <div class="right">
	   	<p class="weight">江苏海宝智造科技股份有限公司</p>
		<p>邮箱：yongkang.Li@hibaosoft.com</p>
		<p>电话：400-638-7383</p>
		<p>手机：186-6129-3766</p>
		<p>地址：无锡市新吴区净慧东道66号五号楼八层</p>
	   </div>
	</div>
  </body>


<script type="text/javascript">


</script>

  
</html>
