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
	<style type="text/css">
		.icon_lists{width: 100% !important;}
		.icon_lists li{float:left;width: 150px;height:120px;text-align: center;list-style: none !important;margin-left: 2px;margin-top: 2px;}
		.icon_lists .icon{font-size: 35px;line-height: 35px;margin: 10px 0;color:#333;-webkit-transition: font-size 0.25s ease-out 0s;-moz-transition: font-size 0.25s ease-out 0s;transition: font-size 0.25s ease-out 0s;}
		.icon_lists .icon:hover{font-size: 35px;}
		.icon_lists .name{padding-top: 6px;}
		.fontclass{height: 35px;}
	</style>
</head>
<body>
	<div class="addEditDiv">
		<div class="">
			<form action="menu/icon.do" name="iconForm" id="iconForm" method="post">
				<input type="hidden" name="MENU_ID" id="menuId" value="${pd.MENU_ID }"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 360px;">
					<ul class="icon_lists clear">
		                <li>
		                	<i class="icon iconfont icon-xitongguanli"></i>
		                    <div class="name">系统管理</div>
		                    <div class="fontclass">.icon-xitongguanli</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-xitongguanli1"></i>
		                    <div class="name">系统管理</div>
		                    <div class="fontclass">.icon-xitongguanli1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-xitongguanli2"></i>
		                    <div class="name">系统管理</div>
		                    <div class="fontclass">.icon-xitongguanli2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-xitongguanli3"></i>
		                    <div class="name">系统管理</div>
		                    <div class="fontclass">.icon-xitongguanli3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yonghuguanli"></i>
		                    <div class="name">用户管理</div>
		                    <div class="fontclass">.icon-yonghuguanli</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yonghuguanli2"></i>
		                    <div class="name">用户管理</div>
		                    <div class="fontclass">.icon-yonghuguanli2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yonghuguanli4"></i>
		                    <div class="name">用户管理</div>
		                    <div class="fontclass">.icon-yonghuguanli4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yonghuguanli5"></i>
		                    <div class="name">用户管理</div>
		                    <div class="fontclass">.icon-yonghuguanli5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yonghuguanli7"></i>
		                    <div class="name">用户管理</div>
		                    <div class="fontclass">.icon-yonghuguanli7</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongju"></i>
		                    <div class="name">工具</div>
		                    <div class="fontclass">.icon-gongju</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongju1"></i>
		                    <div class="name">工具</div>
		                    <div class="fontclass">.icon-gongju1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongju2"></i>
		                    <div class="name">工具</div>
		                    <div class="fontclass">.icon-gongju2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongju3"></i>
		                    <div class="name">工具</div>
		                    <div class="fontclass">.icon-gongju3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-wechat"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-wechat</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-wechat1"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-wechat1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-weixin"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-weixin</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-weixin1"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-weixin1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-weixin2"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-weixin2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-weixin3"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-weixin3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-weixin6"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-weixin6</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-weixin8"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-weixin8</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-weixin5"></i>
		                    <div class="name">微信</div>
		                    <div class="fontclass">.icon-weixin5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-OA"></i>
		                    <div class="name">OA</div>
		                    <div class="fontclass">.icon-OA</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-OA1"></i>
		                    <div class="name">OA</div>
		                    <div class="fontclass">.icon-OA1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-OA2"></i>
		                    <div class="name">OA</div>
		                    <div class="fontclass">.icon-OA2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-shiliangzhinengduixiang"></i>
		                    <div class="name">OA</div>
		                    <div class="fontclass">.icon-shiliangzhinengduixiang</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-caidan"></i>
		                    <div class="name">菜单</div>
		                    <div class="fontclass">.icon-caidan</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-caidan1"></i>
		                    <div class="name">菜单</div>
		                    <div class="fontclass">.icon-caidan1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-caidan2"></i>
		                    <div class="name">菜单</div>
		                    <div class="fontclass">.icon-caidan2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-caidan3"></i>
		                    <div class="name">菜单</div>
		                    <div class="fontclass">.icon-caidan3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-caidan4"></i>
		                    <div class="name">菜单</div>
		                    <div class="fontclass">.icon-caidan4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-caidan5"></i>
		                    <div class="name">菜单</div>
		                    <div class="fontclass">.icon-caidan5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-caidan6"></i>
		                    <div class="name">菜单</div>
		                    <div class="fontclass">.icon-caidan6</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-anniu"></i>
		                    <div class="name">按钮</div>
		                    <div class="fontclass">.icon-anniu</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-anniu1"></i>
		                    <div class="name">按钮</div>
		                    <div class="fontclass">.icon-anniu1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-quanxian"></i>
		                    <div class="name">权限</div>
		                    <div class="fontclass">.icon-quanxian</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-quanxian1"></i>
		                    <div class="name">权限</div>
		                    <div class="fontclass">.icon-quanxian1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-quanxian2"></i>
		                    <div class="name">权限</div>
		                    <div class="fontclass">.icon-quanxian2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-quanxian3"></i>
		                    <div class="name">权限</div>
		                    <div class="fontclass">.icon-quanxian3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-quanxian4"></i>
		                    <div class="name">权限</div>
		                    <div class="fontclass">.icon-quanxian4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-quanxianpeizhi"></i>
		                    <div class="name">权限</div>
		                    <div class="fontclass">.icon-quanxianpeizhi</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zidian"></i>
		                    <div class="name">字典</div>
		                    <div class="fontclass">.icon-zidian</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zidian1"></i>
		                    <div class="name">字典</div>
		                    <div class="fontclass">.icon-zidian1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zidian2"></i>
		                    <div class="name">字典</div>
		                    <div class="fontclass">.icon-zidian2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zidian3"></i>
		                    <div class="name">字典</div>
		                    <div class="fontclass">.icon-zidian3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zidian4"></i>
		                    <div class="name">字典</div>
		                    <div class="fontclass">.icon-zidian4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zaixian"></i>
		                    <div class="name">在线</div>
		                    <div class="fontclass">.icon-zaixian</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zaixian2"></i>
		                    <div class="name">在线</div>
		                    <div class="fontclass">.icon-zaixian2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-xianshang"></i>
		                    <div class="name">在线</div>
		                    <div class="fontclass">.icon-xianshang</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rizhi"></i>
		                    <div class="name">日志</div>
		                    <div class="fontclass">.icon-rizhi</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rizhi1"></i>
		                    <div class="name">日志</div>
		                    <div class="fontclass">.icon-rizhi1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rizhi2"></i>
		                    <div class="name">日志</div>
		                    <div class="fontclass">.icon-rizhi2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rizhi3"></i>
		                    <div class="name">日志</div>
		                    <div class="fontclass">.icon-rizhi3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rizhi4"></i>
		                    <div class="name">日志</div>
		                    <div class="fontclass">.icon-rizhi4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rizhi5"></i>
		                    <div class="name">日志</div>
		                    <div class="fontclass">.icon-rizhi5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-role"></i>
		                    <div class="name">角色</div>
		                    <div class="fontclass">.icon-role</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-role1"></i>
		                    <div class="name">角色</div>
		                    <div class="fontclass">.icon-role1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-daima"></i>
		                    <div class="name">代码</div>
		                    <div class="fontclass">.icon-daima</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-daima1"></i>
		                    <div class="name">代码</div>
		                    <div class="fontclass">.icon-daima1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-daima2"></i>
		                    <div class="name">代码</div>
		                    <div class="fontclass">.icon-daima3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-daima3"></i>
		                    <div class="name">代码</div>
		                    <div class="fontclass">.icon-daima3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-daima4"></i>
		                    <div class="name">代码</div>
		                    <div class="fontclass">.icon-daima4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-uiform"></i>
		                    <div class="name">表单</div>
		                    <div class="fontclass">.icon-uiform</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-jiekou"></i>
		                    <div class="name">接口</div>
		                    <div class="fontclass">.icon-jiekou</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-jiekou1"></i>
		                    <div class="name">接口</div>
		                    <div class="fontclass">.icon-jiekou1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-jiekou2"></i>
		                    <div class="name">接口</div>
		                    <div class="fontclass">.icon-jiekou2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian1"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian2"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian3"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian4"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian5"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian6"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian6</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-youjian7"></i>
		                    <div class="name">邮件</div>
		                    <div class="fontclass">.icon-youjian7</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-erweima"></i>
		                    <div class="name">二维码</div>
		                    <div class="fontclass">.icon-erweima</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-erweima1"></i>
		                    <div class="name">二维码</div>
		                    <div class="fontclass">.icon-erweima1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-erweima2"></i>
		                    <div class="name">二维码</div>
		                    <div class="fontclass">.icon-erweima2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-erweima3"></i>
		                    <div class="name">二维码</div>
		                    <div class="fontclass">.icon-erweima3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-erweima4"></i>
		                    <div class="name">二维码</div>
		                    <div class="fontclass">.icon-erweima4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-dayin1"></i>
		                    <div class="name">打印</div>
		                    <div class="fontclass">.icon-dayin1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-dayin2"></i>
		                    <div class="name">打印</div>
		                    <div class="fontclass">.icon-dayin2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-dayin3"></i>
		                    <div class="name">打印</div>
		                    <div class="fontclass">.icon-dayin3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-dayin4"></i>
		                    <div class="name">打印</div>
		                    <div class="fontclass">.icon-dayin4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-xingnengjiankong"></i>
		                    <div class="name">性能</div>
		                    <div class="fontclass">.icon-xingnengjiankong</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-Performance-monitoring"></i>
		                    <div class="name">性能</div>
		                    <div class="fontclass">.icon-Performance-monitoring</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zuzhijiagou"></i>
		                    <div class="name">组织架构</div>
		                    <div class="fontclass">.icon-zuzhijiagou</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zuzhijiagou"></i>
		                    <div class="name">组织架构</div>
		                    <div class="fontclass">.icon-zuzhijiagou</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zuzhijiagou1"></i>
		                    <div class="name">组织架构</div>
		                    <div class="fontclass">.icon-zuzhijiagou1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zuzhijiagou2"></i>
		                    <div class="name">组织架构</div>
		                    <div class="fontclass">.icon-zuzhijiagou2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zuzhijiagou3"></i>
		                    <div class="name">组织架构</div>
		                    <div class="fontclass">.icon-zuzhijiagou3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zuzhijiagou4"></i>
		                    <div class="name">组织架构</div>
		                    <div class="fontclass">.icon-zuzhijiagou4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yuangong"></i>
		                    <div class="name">员工</div>
		                    <div class="fontclass">.icon-yuangong</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yuangong1"></i>
		                    <div class="name">员工</div>
		                    <div class="fontclass">.icon-yuangong1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yuangong2"></i>
		                    <div class="name">员工</div>
		                    <div class="fontclass">.icon-yuangong2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yuangong3"></i>
		                    <div class="name">员工</div>
		                    <div class="fontclass">.icon-yuangong3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yuangong4"></i>
		                    <div class="name">员工</div>
		                    <div class="fontclass">.icon-yuangong4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yuangong5"></i>
		                    <div class="name">员工</div>
		                    <div class="fontclass">.icon-yuangong5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-yuangong6"></i>
		                    <div class="name">员工</div>
		                    <div class="fontclass">.icon-yuangong6</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-wenjian"></i>
		                    <div class="name">文件</div>
		                    <div class="fontclass">.icon-wenjian</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-wenjian1"></i>
		                    <div class="name">文件</div>
		                    <div class="fontclass">.icon-wenjian1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-wenjian2"></i>
		                    <div class="name">文件</div>
		                    <div class="fontclass">.icon-wenjian2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-wenjian3"></i>
		                    <div class="name">文件</div>
		                    <div class="fontclass">.icon-wenjian3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-liucheng1"></i>
		                    <div class="name">流程</div>
		                    <div class="fontclass">.icon-liucheng1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-liucheng2"></i>
		                    <div class="name">流程</div>
		                    <div class="fontclass">.icon-liucheng2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-liucheng3"></i>
		                    <div class="name">流程</div>
		                    <div class="fontclass">.icon-liucheng3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-liucheng4"></i>
		                    <div class="name">流程</div>
		                    <div class="fontclass">.icon-liucheng4</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-liucheng5"></i>
		                    <div class="name">流程</div>
		                    <div class="fontclass">.icon-liucheng5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-liucheng6"></i>
		                    <div class="name">流程</div>
		                    <div class="fontclass">.icon-liucheng6</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-fabu"></i>
		                    <div class="name">发布</div>
		                    <div class="fontclass">.icon-fabu</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-fabu1"></i>
		                    <div class="name">发布</div>
		                    <div class="fontclass">.icon-fabu1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-fabu2"></i>
		                    <div class="name">发布</div>
		                    <div class="fontclass">.icon-fabu2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-fabu5"></i>
		                    <div class="name">发布</div>
		                    <div class="fontclass">.icon-fabu5</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-timer"></i>
		                    <div class="name">定时</div>
		                    <div class="fontclass">.icon-timer</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-fujian"></i>
		                    <div class="name">附件</div>
		                    <div class="fontclass">.icon-fujian</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-bulletin"></i>
		                    <div class="name">公告</div>
		                    <div class="fontclass">.icon-bulletin</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-zhiwu"></i>
		                    <div class="name">职务</div>
		                    <div class="fontclass">.icon-zhiwu</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-renwu"></i>
		                    <div class="name">任务</div>
		                    <div class="fontclass">.icon-renwu</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-jichushujuguanli"></i>
		                    <div class="name">基础数据</div>
		                    <div class="fontclass">.icon-jichushujuguanli</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-navicon-chfl"></i>
		                    <div class="name">存货分类</div>
		                    <div class="fontclass">.icon-navicon-chfl</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-navicon-chflsz"></i>
		                    <div class="name">存货属性</div>
		                    <div class="fontclass">.icon-navicon-chflsz</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-danwei"></i>
		                    <div class="name">单位</div>
		                    <div class="fontclass">.icon-danwei</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-renyuanbanciguanli"></i>
		                    <div class="name">班制</div>
		                    <div class="fontclass">.icon-danwei</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-banciguanli"></i>
		                    <div class="name">班次</div>
		                    <div class="fontclass">.icon-banciguanli</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-cangku"></i>
		                    <div class="name">仓库</div>
		                    <div class="fontclass">.icon-cangku</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-shangpinmorenkuwei"></i>
		                    <div class="name">库位</div>
		                    <div class="fontclass">.icon-shangpinmorenkuwei</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-kehu"></i>
		                    <div class="name">客户</div>
		                    <div class="fontclass">.icon-kehu</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongyingshangguanli"></i>
		                    <div class="name">客户</div>
		                    <div class="fontclass">.icon-gongyingshangguanli</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongchengxiangmu"></i>
		                    <div class="name">工程项目</div>
		                    <div class="fontclass">.icon-gongchengxiangmu</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rili"></i>
		                    <div class="name">日历</div>
		                    <div class="fontclass">.icon-rili</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rili1"></i>
		                    <div class="name">日历</div>
		                    <div class="fontclass">.icon-rili1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-rili2"></i>
		                    <div class="name">日历</div>
		                    <div class="fontclass">.icon-rili2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-wuliaoguanli"></i>
		                    <div class="name">物料管理</div>
		                    <div class="fontclass">.icon-wuliaoguanli</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-ziyuan3"></i>
		                    <div class="name">资源</div>
		                    <div class="fontclass">.icon-ziyuan3</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-web-icon-7"></i>
		                    <div class="name">工序</div>
		                    <div class="fontclass">.icon-web-icon-7</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongyi"></i>
		                    <div class="name">工艺</div>
		                    <div class="fontclass">.icon-gongyi</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-web-icon-2"></i>
		                    <div class="name">工作中心</div>
		                    <div class="fontclass">.icon-web-icon-2</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-Bomdanganguanli"></i>
		                    <div class="name">BOM</div>
		                    <div class="fontclass">.icon-Bomdanganguanli</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-8"></i>
		                    <div class="name">计划</div>
		                    <div class="fontclass">.icon-8</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-jihua1"></i>
		                    <div class="name">计划</div>
		                    <div class="fontclass">.icon-jihua1</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-gongzuojihua"></i>
		                    <div class="name">计划</div>
		                    <div class="fontclass">.icon-gongzuojihua</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-jihua"></i>
		                    <div class="name">计划</div>
		                    <div class="fontclass">.icon-jihua</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-renwutiaodu"></i>
		                    <div class="name">调度</div>
		                    <div class="fontclass">.icon-renwutiaodu</div>
		                </li>
		                <li>
		                	<i class="icon iconfont icon-shengchanrenwudanguanli"></i>
		                    <div class="name">派工</div>
		                    <div class="fontclass">.icon-shengchanrenwudanguanli</div>
		                </li>
		                <li>
                <i class="icon iconfont icon-shebeiguanli"></i>
                    <div class="name">设备管理</div>
                    <div class="fontclass">.icon-shebeiguanli</div>
                </li>
                <li>
                <i class="icon iconfont icon-icon3"></i>
                    <div class="name">设备</div>
                    <div class="fontclass">.icon-icon3</div>
                </li>
                <li>
                <i class="icon iconfont icon-shebei"></i>
                    <div class="name">设备</div>
                    <div class="fontclass">.icon-shebei</div>
                </li>
                <li>
                <i class="icon iconfont icon-icon5"></i>
                    <div class="name">设备管理</div>
                    <div class="fontclass">.icon-icon5</div>
                </li>
                <li>
                <i class="icon iconfont icon-icon-2"></i>
                    <div class="name">设备</div>
                    <div class="fontclass">.icon-icon-2</div>
                </li>
                 <li>
                <i class="icon iconfont icon-guzhangchaxun"></i>
                    <div class="name">故障查询</div>
                    <div class="fontclass">.icon-guzhangchaxun</div>
                </li>
                <li>
                <i class="icon iconfont icon-guzhangbaojing"></i>
                    <div class="name">故障报警</div>
                    <div class="fontclass">.icon-guzhangbaojing</div>
                </li>
                <li>
                <i class="icon iconfont icon-xitongguzhang"></i>
                    <div class="name">系统故障</div>
                    <div class="fontclass">.icon-xitongguzhang</div>
                </li>
                <li>
                <i class="icon iconfont icon-taizhanguzhang"></i>
                    <div class="name">27台站故障</div>
                    <div class="fontclass">.icon-taizhanguzhang</div>
                </li>
                <li>
                <i class="icon iconfont icon-guzhangleixing"></i>
                    <div class="name">故障类型</div>
                    <div class="fontclass">.icon-guzhangleixing</div>
                </li>
                <li>
                <i class="icon iconfont icon-weixiu"></i>
                    <div class="name">维修</div>
                    <div class="fontclass">.icon-weixiu</div>
                </li>
                
                <li>
                <i class="icon iconfont icon-weixiu1"></i>
                    <div class="name">维修</div>
                    <div class="fontclass">.icon-weixiu1</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-weixiu2"></i>
                    <div class="name">维修</div>
                    <div class="fontclass">.icon-weixiu2</div>
                </li>
                
                 <li>
                <i class="icon iconfont icon-weixiu3"></i>
                    <div class="name">维修</div>
                    <div class="fontclass">.icon-weixiu3</div>
                </li>
                <li>
                <i class="icon iconfont icon-zhengzaiweixiudegongdan"></i>
                    <div class="name">正在维修的工单</div>
                    <div class="fontclass">.icon-zhengzaiweixiudegongdan</div>
                </li>
                
                <li>
                <i class="icon iconfont icon-buoumaotubiao41"></i>
                    <div class="name">维修</div>
                    <div class="fontclass">.icon-buoumaotubiao41</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-buoumaotubiao46"></i>
                    <div class="name">维修</div>
                    <div class="fontclass">.icon-buoumaotubiao46</div>
                </li>
                
                <li>
                <i class="icon iconfont icon-iconkh1"></i>
                    <div class="name">客户</div>
                    <div class="fontclass">.icon-iconkh1</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-iconkh11"></i>
                    <div class="name">客户</div>
                    <div class="fontclass">.icon-iconkh11</div>
                </li>
                
                 <li>
                <i class="icon iconfont icon-web-icon-6"></i>
                    <div class="name">客户</div>
                    <div class="fontclass">.icon-web-icon-6</div>
                </li>
                
                <li>
                <i class="icon iconfont icon-xiaoshouchuku"></i>
                    <div class="name">销售出库</div>
                    <div class="fontclass">.icon-xiaoshouchuku</div>
                </li>
                <li>
                <i class="icon iconfont icon-xiaoshoudingdan"></i>
                    <div class="name">销售订单</div>
                    <div class="fontclass">.icon-xiaoshoudingdan</div>
                </li>
                <li>
                <i class="icon iconfont icon-webicon304"></i>
                    <div class="name">销售订单</div>
                    <div class="fontclass">.icon-webicon304</div>
                </li>
                <li>
                <i class="icon iconfont icon-list_xiaoshoudingdan"></i>
                    <div class="name">list_销售订单</div>
                    <div class="fontclass">.icon-list_xiaoshoudingdan</div>
                </li>
                <li>
                <i class="icon iconfont icon-web-icon-5"></i>
                    <div class="name">销售订单</div>
                    <div class="fontclass">.icon-web-icon-5</div>
                </li>
                <li>
                <i class="icon iconfont icon-navicon-xsckd"></i>
                    <div class="name">销售出库单</div>
                    <div class="fontclass">.icon-navicon-xsckd</div>
                </li>
                <li>
                <i class="icon iconfont icon-fahuo"></i>
                    <div class="name">发货</div>
                    <div class="fontclass">.icon-fahuo</div>
                </li>
                 <li>
                <i class="icon iconfont icon-fahuo1"></i>
                    <div class="name">发货</div>
                    <div class="fontclass">.icon-fahuo1</div>
                </li>
                <li>
                <i class="icon iconfont icon-fahuojihua"></i>
                    <div class="name">发货计划</div>
                    <div class="fontclass">.icon-fahuojihua</div>
                </li>
                
                 <li>
                <i class="icon iconfont icon-webicon307"></i>
                    <div class="name">外协加工</div>
                    <div class="fontclass">.icon-webicon307</div>
                </li>
                <li>
                <i class="icon iconfont icon-shengchanjiagong"></i>
                    <div class="name">生产加工</div>
                    <div class="fontclass">.icon-shengchanjiagong</div>
                </li>
                <li>
                <i class="icon iconfont icon-shengchanrenwudanguanli"></i>
                    <div class="name">生产任务单管理</div>
                    <div class="fontclass">.icon-shengchanrenwudanguanli</div>
                </li>
                 <li>
                <i class="icon iconfont icon-shengchanlingliaoyutuiliao"></i>
                    <div class="name">生产领料与退料</div>
                    <div class="fontclass">.icon-shengchanlingliaoyutuiliao</div>
                </li>
                <li>
                <i class="icon iconfont icon-shengchanguanli"></i>
                    <div class="name">需方-生产管理</div>
                    <div class="fontclass">.icon-shengchanguanli</div>
                </li>
                <li>
                <i class="icon iconfont icon-shengchangongxuchaxun"></i>
                    <div class="name">生产工序查询</div>
                    <div class="fontclass">.icon-shengchangongxuchaxun</div>
                </li>
                <li>
                <i class="icon iconfont icon-go"></i>
                    <div class="name">发出</div>
                    <div class="fontclass">.icon-go</div>
                </li>
                <li>
                <i class="icon iconfont icon-closed"></i>
                    <div class="name">接收</div>
                    <div class="fontclass">.icon-closed</div>
                </li>
                <li>
                <i class="icon iconfont icon-webicon05"></i>
                    <div class="name">采购管理</div>
                    <div class="fontclass">.icon-webicon05</div>
                </li>
                <li>
                <i class="icon iconfont icon-dingdanwuliaocaigouguanli"></i>
                    <div class="name">订单物料采购管理</div>
                    <div class="fontclass">.icon-dingdanwuliaocaigouguanli</div>
                </li>
                 <li>
                <i class="icon iconfont icon-navicon-cggl"></i>
                    <div class="name">采购管理</div>
                    <div class="fontclass">.icon-navicon-cggl</div>
                </li>
                <li>
                <i class="icon iconfont icon-web-icon-"></i>
                    <div class="name">供应商</div>
                    <div class="fontclass">.icon-web-icon-</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-web-icon-1"></i>
                    <div class="name">供应商2</div>
                    <div class="fontclass">.icon-web-icon-1</div>
                </li>
                <li>
                <i class="icon iconfont icon-gongyingshangguanli"></i>
                    <div class="name">供应商管理</div>
                    <div class="fontclass">.icon-gongyingshangguanli</div>
                </li>
                <li>
                <i class="icon iconfont icon-zuoyequyu"></i>
                    <div class="name">作业区域</div>
                    <div class="fontclass">.icon-zuoyequyu</div>
                </li>
                <li>
                <i class="icon iconfont icon-weizhi"></i>
                    <div class="name">位置</div>
                    <div class="fontclass">.icon-weizhi</div>
                </li>
                 <li>
                <i class="icon iconfont icon-weizhi1"></i>
                    <div class="name">位置</div>
                    <div class="fontclass">.icon-weizhi1</div>
                </li>
                <li>
                <i class="icon iconfont icon-icon-3"></i>
                    <div class="name">位置</div>
                    <div class="fontclass">.icon-icon-3</div>
                </li>
                <li>
                <i class="icon iconfont icon-navicon-zdda"></i>
                    <div class="name">站点档案</div>
                    <div class="fontclass">.icon-navicon-zdda</div>
                </li>
                <li>
                <i class="icon iconfont icon-zhandian"></i>
                    <div class="name">站点</div>
                    <div class="fontclass">.icon-zhandian</div>
                </li>
                 <li>
                <i class="icon iconfont icon-moju01"></i>
                    <div class="name">模具-01</div>
                    <div class="fontclass">.icon-moju01</div>
                </li>
                <li>
                <i class="icon iconfont icon-sanweimoxing2"></i>
                    <div class="name">三维模型2</div>
                    <div class="fontclass">.icon-sanweimoxing2</div>
                </li>
                 <li>
                <i class="icon iconfont icon-moxingzujian"></i>
                    <div class="name">模型组件</div>
                    <div class="fontclass">.icon-moxingzujian</div>
                </li>
                <li>
                 <i class="icon iconfont icon-rukuguanli"></i>
                    <div class="name">入库管理</div>
                    <div class="fontclass">.icon-rukuguanli</div>
                </li>
                <li>
                <i class="icon iconfont icon-ruku"></i>
                    <div class="name">入库</div>
                    <div class="fontclass">.icon-ruku</div>
                </li>
                <li>
                <i class="icon iconfont icon-rukuguanli1"></i>
                    <div class="name">入库管理</div>
                    <div class="fontclass">.icon-rukuguanli1</div>
                </li>
                 <li>
                <i class="icon iconfont icon-shangpinruku"></i>
                    <div class="name">商品入库</div>
                    <div class="fontclass">.icon-shangpinruku</div>
                </li>
                 <li>
                <i class="icon iconfont icon-chengpinrukuyuchukuguanli"></i>
                    <div class="name">成品入库与出库管理</div>
                    <div class="fontclass">.icon-chengpinrukuyuchukuguanli</div>
                </li>
                 <li>
                <i class="icon iconfont icon-dingdanwuliaocaigouruku"></i>
                    <div class="name">订单物料采购入库</div>
                    <div class="fontclass">.icon-dingdanwuliaocaigouruku</div>
                </li>
                <li>
                <i class="icon iconfont icon-weiwaijiagongcaigouruku"></i>
                    <div class="name">委外加工采购入库</div>
                    <div class="fontclass">.icon-weiwaijiagongcaigouruku</div>
                </li>
                 <li>
                <i class="icon iconfont icon-ruku1"></i>
                    <div class="name">入库</div>
                    <div class="fontclass">.icon-ruku1</div>
                </li>
                
                <li>
                <i class="icon iconfont icon-chuku"></i>
                    <div class="name">出库</div>
                    <div class="fontclass">.icon-chuku</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-kucun"></i>
                    <div class="name">库存</div>
                    <div class="fontclass">.icon-kucun</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-ruku1"></i>
                    <div class="name">入库</div>
                    <div class="fontclass">.icon-ruku1</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-tiaobochuku"></i>
                    <div class="name">调拨出库</div>
                    <div class="fontclass">.icon-tiaobochuku</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-tiaoboruku"></i>
                    <div class="name">调拨入库</div>
                    <div class="fontclass">.icon-tiaoboruku</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-pandian"></i>
                    <div class="name">盘点</div>
                    <div class="fontclass">.icon-pandian</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-caituiruku"></i>
                    <div class="name">采退入库</div>
                    <div class="fontclass">.icon-caituiruku</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-qitachuku"></i>
                    <div class="name">其他出库</div>
                    <div class="fontclass">.icon-qitachuku</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-shangpinmorenkuwei"></i>
                    <div class="name">上品默认库位</div>
                    <div class="fontclass">.icon-shangpinmorenkuwei</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-qitaruku"></i>
                    <div class="name">其他入库</div>
                    <div class="fontclass">.icon-qitaruku</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-xiaotuiruku"></i>
                    <div class="name">销退入库</div>
                    <div class="fontclass">.icon-xiaotuiruku</div>
                </li>
                <li>
                <i class="icon iconfont icon-navicon-rkyw"></i>
                    <div class="name">入库业务</div>
                    <div class="fontclass">.icon-navicon-rkyw</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-navicon-rkd"></i>
                    <div class="name">入库单</div>
                    <div class="fontclass">.icon-navicon-rkd</div>
                </li>
                <li>
                <i class="icon iconfont icon-chanpinruku"></i>
                    <div class="name">产品入库</div>
                    <div class="fontclass">.icon-chanpinruku</div>
                </li>
                <li>
                <i class="icon iconfont icon-chuku1"></i>
                    <div class="name">出库</div>
                    <div class="fontclass">.icon-chuku1</div>
                </li>
                <li>
                <i class="icon iconfont icon-xiaoshouchuku"></i>
                    <div class="name">销售出库</div>
                    <div class="fontclass">.icon-xiaoshouchuku</div>
                </li>
                <li>
                <i class="icon iconfont icon-chukuguanli"></i>
                    <div class="name">出库管理</div>
                    <div class="fontclass">.icon-chukuguanli</div>
                </li>
                <li>
                <i class="icon iconfont icon-chukudengji"></i>
                    <div class="name">出库登记</div>
                    <div class="fontclass">.icon-chukudengji</div>
                </li>
                 <li>
                <i class="icon iconfont icon-navicon-xsckd"></i>
                    <div class="name">销售出库单</div>
                    <div class="fontclass">.icon-navicon-xsckd</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-navicon-clckd"></i>
                    <div class="name">材料出库单</div>
                    <div class="fontclass">.icon-navicon-clckd</div>
                </li>
                 <li>
                <i class="icon iconfont icon-chukuoff"></i>
                    <div class="name">出库off</div>
                    <div class="fontclass">.icon-chukuoff</div>
                </li>
                <li>
                <i class="icon iconfont icon-chuku1"></i>
                    <div class="name">出库</div>
                    <div class="fontclass">.icon-chuku1</div>
                </li>
                <li>
                <i class="icon iconfont icon-pandian"></i>
                    <div class="name">盘点</div>
                    <div class="fontclass">.icon-pandian</div>
                </li>
                 <li>
                <i class="icon iconfont icon-navicon-pdd"></i>
                    <div class="name">盘点单</div>
                    <div class="fontclass">.icon-navicon-pdd</div>
                </li>
                <li>
                <i class="icon iconfont icon-navicon-kcpd"></i>
                    <div class="name">库存盘点</div>
                    <div class="fontclass">.icon-navicon-kcpd</div>
                </li>
            
                <li>
                <i class="icon iconfont icon-navicon-kcpdd"></i>
                    <div class="name">库存盘点单</div>
                    <div class="fontclass">.icon-navicon-kcpdd</div>
                </li>
                <li>
                <i class="icon iconfont icon-kucunliang"></i>
                    <div class="name">库存量</div>
                    <div class="fontclass">.icon-kucunliang</div>
                </li>
                <li>
                <i class="icon iconfont icon-05_kucunguanli"></i>
                    <div class="name">05_库存管理</div>
                    <div class="fontclass">.icon-05_kucunguanli</div>
                </li>
                 <li>
                <i class="icon iconfont icon-kucun"></i>
                    <div class="name">库存</div>
                    <div class="fontclass">.icon-kucun</div>
                </li>
                <li>
                <i class="icon iconfont icon-webicon203"></i>
                    <div class="name">安全</div>
                    <div class="fontclass">.icon-webicon203</div>
                </li>
                 <li>
                <i class="icon iconfont icon-anquan"></i>
                    <div class="name">安全</div>
                    <div class="fontclass">.icon-anquan</div>
                </li>
                <li>
                <i class="icon iconfont icon-zhilianganquan"></i>
                    <div class="name">质量安全</div>
                    <div class="fontclass">.icon-zhilianganquan</div>
                </li>
                <li>
                <i class="icon iconfont icon-anquan1"></i>
                    <div class="name">安全</div>
                    <div class="fontclass">.icon-anquan1</div>
                </li>
                <li>
                <i class="icon iconfont icon-zhiliangbaozheng"></i>
                    <div class="name">质量保证</div>
                    <div class="fontclass">.icon-zhiliangbaozheng</div>
                </li>
                 <li>
                <i class="icon iconfont icon-zhiliangbz"></i>
                    <div class="name">质量保障</div>
                    <div class="fontclass">.icon-zhiliangbz</div>
                </li>
                 <li>
                <i class="icon iconfont icon-zhilianganquan"></i>
                    <div class="name">质量安全</div>
                    <div class="fontclass">.icon-zhilianganquan</div>
                </li>
                <li>
                <i class="icon iconfont icon-boxingzhiliang"></i>
                    <div class="name">10波形质量</div>
                    <div class="fontclass">.icon-boxingzhiliang</div>
                </li>
                <li>
                <i class="icon iconfont icon-navicon-kcdb"></i>
                    <div class="name">库存调拨</div>
                    <div class="fontclass">.icon-navicon-kcdb</div>
                </li>
                
                
                
			        </ul>
				</div>
				<div class="addEditButtonDiv">
					字体图标：<input class="easyui-textbox" type="text" ID="MENU_ICON" name="MENU_ICON" readonly="readonly" data-options="required:true" value="${pd.MENU_ICON}"> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
				</div>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript">
	
	$(function(){
		//为i标签绑定事件
		$("i").on("click",function(){
			var iconStr=$(this).attr("class");
			var icon=iconStr.substring(13,iconStr.length);
			$("#MENU_ICON").textbox("setValue", icon);
		});
	});
	
	//保存
	 function save(){
		
		$("#iconForm").form("submit",{
			success:function(data){
				var data = eval("("+data+")");  
				if("success"==data.result){
					$("#saveEditCenter").hide();
					top.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错！","error");
				}
			}
		});
	} 
</script>


</html>
