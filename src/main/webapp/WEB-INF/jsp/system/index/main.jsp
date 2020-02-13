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
	<!-- jsp文件头和头部 -->
	<%@ include file="jquery_easyui.jsp"%>
	<link href="static/image/favicon.ico" type="image/x-icon" rel="shortcut icon">
	<!-- 即时通讯 -->
	<!-- <link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/css/websocket.css" /> -->
	<script type="text/javascript" src="plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
	<script type="text/javascript" src="plugins/websocketInstantMsg/websocket.js"></script>
	<!-- 即时通讯 -->
	
	<style type="text/css">
		.pf-remind{
			width: 44px;height: 55px;line-height:55px;float: right;
		}
		.pf-remind:HOVER{
			cursor: pointer;
		}
		.pf-remind i{
			font-size: 20px;color: #ffffff;
		}
		.pf-remind .pf-remind-num{
			position: absolute;top:8px;right:146px;font-size: 12px;padding: 2px 3px;line-height: .9;font-weight: bold;color: #f45438;text-align: center;
		}
		.ml-list{
		  background-color: #222d32;
		  height:100%;
		  overflow-y: auto;
		}
		
		.ml-list .ml-block ul{
		  /* padding-left: 28px; */
		}
		.ml-list .ml-block ul li{
		  /* padding: 14px 0; */
		  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
		}
		.ml-list .ml-block ul li a.primary-a{
		  padding-left: 20px;
		  display: block;
		  position: relative;
		  font-size: 14px;
		  line-height:40px;
		  color: #b9c1ca;
		}
		
		.ml-list .ml-block ul li a.primary-a:hover{
		  background-color: #1C88B9;
		  color: #fff;
		}
		.ml-list .ml-block ul li.active a.primary-a{
		  color: #fff;
		  background-color: #1C88B9;
		}
		
		.ml-list .ml-block ul li a.primary-a i:first-child{
		  margin-right: 12px;
		}
		.ml-list .ml-block ul li a.primary-a i.icon-xiangyou{
		  position: absolute;
		  top: 50%;
		  right: 20px;
		  color: #fff;
		  transform: translateY(-50%);
		  transition: all 0.2s;
		}
		
		.ml-list .ml-block ul li.active a.primary-a i.icon-xiangyou{
		  transform: translateY(-50%) rotate(90deg);
		}
		.ml-list .ml-block ul li dl{
		  display: none;
		  background-color: #222d32;
		}
		.ml-list .ml-block ul li.active dl{
		  /* height: auto; */
		  /* display: block; */
		}
		.ml-list .ml-block ul li dl dd{
		  border-bottom: 0px solid #34495e;
		  
		}
		.ml-list .ml-block ul li dl dd a{
		  display: block;
		  position: relative;
		  padding-left: 54px;
		  font-size: 14px;
		  color: #fff;
		  line-height: 40px;
		  overflow: hidden; 
		  text-overflow:ellipsis;
		  white-space: nowrap;
		}
		.ml-list .ml-block ul li dl dd a:before{
		  position: absolute;
		  content: "";
		  left: 38px;
		  top: 50%;
		  width: 4px;
		  height: 4px;
		  border: 2px solid #fff;
		  border-radius: 50%;
		  transform: translateY(-50%);
		}
		.ml-list .ml-block ul li dl dd.active,
		.ml-list .ml-block ul li dl dd:hover{
		  background-color: #2b333e;
		}
		.ml-list .ml-block ul li dl dd.active a:before{
		  border-color: #1C88B9;
		}
		
	</style>

  </head>
  <body class="easyui-layout" id="mainall" >
	<!--加载-->
	<div id="background" class="background" style="display: none; "></div> 
	<div id="progressBar" class="progressBar" style="display: none; ">数据处理中，请稍等...</div> 
	<!--加载end-->
	
	<!-- North -->
	<div class="tophead" region="north" style="">
		<div id="pf-hd">
        	<!-- 公司logo-->
        	<div class="pf-logo">
               <img alt="" src=""><span style="font-style: italic;">LOGO</span>&nbsp;&nbsp;<span>&nbsp;&nbsp;云科</span>
            </div>
            
			<!-- 用户 -->
            <div class="pf-user">
                <div class="pf-user-photo">
                    <i class="iconfont icon-touxiang4"></i>
                </div>
                <h4 class="pf-user-name ellipsis">${sessionScope.STAFF_NAME}</h4>
                <i class="iconfont icon-xia"></i>

                <div class="pf-user-panel">
                    <ul class="pf-user-opt">
                        <li class="pf-modify-pwd" onclick="javascript:updatePwd();">
                            <a href="javascript:;">
                                <i class="iconfont icon-mima5"></i>
                                <span class="pf-opt-name">修改密码</span>
                            </a>
                        </li>
                        <!-- <li class="pf-modify-pwd" onclick="javascript:changeLanguage();">
                            <a href="javascript:;">
                                <i class="iconfont icon-mima5"></i>
                                <span class="pf-opt-name">Language</span>
                            </a>
                        </li> -->
                        <li class="pf-logout" onclick="javascript:dologout();">
                            <a >
                                <i class="iconfont icon-tuichu7"></i>
                                <span class="pf-opt-name">退出</span>
                            </a>
                        </li>
                    </ul>
                    <span class="triangle"><i></i></span>
                </div>
            </div>
            
            <div class="pf-remind" title="消息" onclick="openTab('sms')">
            	<i class="iconfont icon-xiaoxi"></i>
            	<span class="pf-remind-num" id="smsNum">0</span>
            </div>
            
        </div>
	</div>
	<!-- North -->
	
	<!-- west -->
	<div class="leftmenu" region="west" split="false" border="false" title="导航" id="west">
  		<section class="ml-list">
	        <div class="ml-block">
	          <ul id="menuUl">
	            
	          </ul>
	        </div>
      	</section>
	</div>
	<!-- west -->
	
	<!--center-->
	<div region="center" split="false" class="right" id="mainPanle">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div iconCls="iconfont icon-shouye3" title="首页" id="home">
				<iframe id="homeIframe" scrolling="no" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
			</div>
		</div>
		<div id="rightmask" class="layermask">
			<div class="progressBar" id="progressBar_right">
			数据处理中，请稍等...
			</div>
		</div>
	</div>
	<!--center-->
	
	<!-- tabs关闭 -->
	<div id="mm" class="easyui-menu" style="width: 150px;display: none;">
		<div id="mm-tabupdate"><i class="iconfont icon-shuaxin3"></i>&nbsp;&nbsp;刷新</div>
		<div id="mm-tabclose"><i class="iconfont icon-guanbi"></i>&nbsp;&nbsp;关闭</div>
		<div id="mm-tabcloseall"><i class="iconfont icon-guanbi"></i>&nbsp;&nbsp;全部关闭</div>
		<div id="mm-tabcloseother"><i class="iconfont icon-guanbi"></i>&nbsp;&nbsp;除此之外全部关闭</div>
		<div id="mm-tabcloseright"><i class="iconfont icon-guanbi"></i>&nbsp;&nbsp;当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft"><i class="iconfont icon-guanbi"></i>&nbsp;&nbsp;当前页左侧全部关闭</div>
		<div id="mm-newOpen"><i class="iconfont icon-guanbi"></i>&nbsp;&nbsp;在新页面中打开</div>
		<div id="mm-exit"><i class="iconfont icon-tuichu7"></i>&nbsp;&nbsp;退出</div>
	</div>
	
</body>


<script type="text/javascript">




function updatePwd(){
	 var userEditDiag = new top.Dialog();
	 userEditDiag.Drag=true;
	 userEditDiag.Title ="密码修改";
	 userEditDiag.URL = '<%=basePath%>user/toPwd.do';
	 userEditDiag.Width = 400;
	 userEditDiag.Height = 110;
	 userEditDiag.CancelEvent = function(){ //关闭事件
		 if(userEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 dologoutNoconfirm("");
		 }
		 userEditDiag.close();
	 };
	 userEditDiag.show();
}

/**
 * 国际化切换
 */
function changeLanguage(){
	var Diag = new top.Dialog();
	Diag.Drag=true;
	Diag.Title ="语言设置";
	Diag.URL = '<%=basePath%>user/changeLanguage.do';
	Diag.Width = 400;
	Diag.Height = 150;
	Diag.CancelEvent = function(){ //关闭事件
		 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 window.location.reload();
		 }
		 Diag.close();
	 };
	Diag.show();
}


//退出
function dologoutNoconfirm(msg){
	$.ajax({
		type:"POST",
		url:"logout",
		data:{msg:msg},
		dataType:"json",
		cache:false,
		success:function(data){
			if("success"==data.result){
				if(msg=="1" || msg=="2"){
					alert("您被系统管理员强制下线或您的帐号在别处登录");
				}
				window.location.href="login_toLogin";
			}else{
				if(msg=="1" || msg=="2"){
					alert("您被系统管理员强制下线或您的帐号在别处登录");
				}
				window.location.href="login_toLogin";
			}
		}
	});
}


//退出
function dologout(){
	$.messager.confirm("确认","确定退出？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				url:"logout",
				dataType:"json",
				cache:false,
				success:function(data){
					if("success"==data.result){
						window.location.href="login_toLogin";
					}else{
						window.location.href="login_toLogin";
					}
				}
			});
		}
	});
	
}


//退出
function dologoutError(){
	$.ajax({
		type:"POST",
		url:"logout",
		dataType:"json",
		cache:false,
		success:function(data){
			if("success"==data.result){
				window.location.href="login_toLogin";
			}else{
				window.location.href="login_toLogin";
			}
		}
	});
}


//打开消息和待办任务页面(不好的地方，菜单名称和url和图标直接写死了)
function openTab(tag){
	if(tag=="sms"){
		addTab("我的消息","sms/receiveList.do","icon-navicon-dxjs","");
	}
}



//显示进度
function showMsgProgress(){
	$.messager.progress({
		title:"提示",
		msg:"正在处理，请稍候。。。",
		interval:1000
	}); 
}

//关闭进度
function closeMsgProgress(){
	$.messager.progress("close");
}

function getMainHeight(){
	var vMainHeight = document.body.scrollHeight;
	return vMainHeight;
}


function createDialog(){
	var vMainHeight = document.body.scrollHeight;
	return vMainHeight;
}

var memuData;
var USER_ID;			//用户ID
var user = "SS";		//用于即时通讯（ 当前登录用户）
var websocket;			//websocket对象
var wimadress="";		//即时聊天服务器IP和端口
var oladress="";		//在线管理和站内信服务器IP和端口
var headInfoCount="1";    //获取顶部信息标识

//下线
function offLine(msg){
	dologoutNoconfirm(msg);//退出
}

//默认同步执行
//$.ajaxSetup({async: false});

//DOM载入执行
$(function(){
	tabClose();
	tabCloseEven();
	//初始化顶部主菜单
	InitMenu();
});

//获取顶部相关信息
function getHeadInfo(){
	
	if(headInfoCount=="1"){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>head/getHeadInfo.do?tm='+new Date().getTime(),
	    	data: encodeURI(""),
			dataType:"json",
			cache: false,
			success: function(data){
				
				$.each(data.list, function(i, list){
					 
					 user = list.USERNAME;
					 USER_ID = list.USER_ID;		//用户ID
					 
				 });
				
				 headInfoCount="2";
				 wimadress = data.wimadress;				//即时聊天服务器IP和端口
				 oladress = data.oladress;					//在线管理和站内信服务器IP和端口
				 online();								//连接在线
				 getRemindInfo();//获取提醒信息（待审业务和内部消息）
			}
		});
	}
	
}


//获取待办任务和内部消息数量
function getRemindInfo(){
	
	$.ajax({
		type: "POST",
		url: '<%=basePath%>head/getRemindInfo.do',
    	data: [],
		dataType:"json",
		cache: false,
		success: function(data){
			 $("#smsNum").html(data.smsNum);
		}
	});
}

//刷新首页
function refreshHome(){
	var homeWindow = $("#homeIframe")[0].contentWindow; 
	homeWindow.refreshData();
}



//加入在线列表
function online(){
	if (window.WebSocket) {
		websocket = new WebSocket(encodeURI("ws://"+oladress)); //oladress在main.jsp页面定义
		websocket.onopen = function() {
			//连接成功
			websocket.send("[join]"+user);
		};
		websocket.onerror = function() {
			//连接失败
		};
		websocket.onclose = function() {
			//连接断开
		};
		//消息接收
		websocket.onmessage = function(message) {
			var message = JSON.parse(message.data);
			
			if(message.type == "goOut"){
				offLine("1");
			}else if(message.type == "thegoout"){
				offLine("2");
			}else if(message.type=="senSmsNoMsg"){
				getRemindInfo();
				refreshHome();
				$.messager.show({
					title:"消息提醒",
					msg:"您有新的信息,请注意查看！",
					timeout:0,
					showType:"slide"
				});
			}else if(message.type=="senSmsWithMsg"){
				getRemindInfo();
				refreshHome();
				$.messager.show({
					title:"消息提醒",
					msg:message.msg,
					timeout:0,
					showType:"slide"
				});
			}else if(message.type=="senSmsNoMsgNoTitle"){
				getRemindInfo();
				refreshHome();
			}
			
		};
	}
}

//通知某人，没有消息内容
function senSmsNoMsg(user){
	websocket.send("[senSmsNoMsg]"+user);
}

//通知某人，有消息内容
function senSmsWithMsg(user,msg){
	websocket.send("[senSmsWithMsg]"+user+"[IWFMPEPS]"+msg);
}

//通知某人，只是刷新
function senSmsNoMsgNoTitle(user){
	websocket.send("[senSmsNoMsgNoTitle]"+user);
}


/**
 * 加载菜单
 */
function InitMenu(){
	
	var ajaxbg = $("#background,#progressBar"); 
	ajaxbg.show(); 
	
	$.ajax({
    	type: "post",  //以post方式与后台沟通
    	url : "main/menu",//与此页面沟通
    	data:"MENU_TYPE=1",
    	dataType:'json',//返回的值以 JSON方式 解释
    	error:function(jqXHR, textStatus, errorThrown){
	    	if(textStatus=="timeout"){  
	    		$.messager.alert('加载超时','对不起，加载用户信息超时！<br>请重新登录', "info", function(){dologoutError()});    
	        }else{ 
	        	setTimeout(function(){$.messager.alert("加载失败", "对不起，加载信息失败！<br>请重新登录", "info", function(){dologoutError()});}, 8000);
	        }  
    	},
	    beforeSend:function(){
	    	ajaxbg.show(); 
	    },
    	success: function(json){	
     		memuData=json;
     		var data=json;
     		ajaxbg.hide(); 
       		
			getHeadInfo();	//获取顶部信息
			
			$("#homeIframe").attr("src",'<%=basePath%>/main/home.do');
			
			var menulist = "";
			//初始化菜单
			$.each(json,function(i,sm){
				
				if(sm.responseText){
					menulist+='<li class="primary"><a href="javascript:void(0)" class="primary-a"><i class="iconfont '+sm.iconfont+'"></i>'+sm.text+'<i class="iconfont icon-xiangyou"></i></a><dl>';
					$.each(sm.responseText,function(k,o){
						menulist+='<dd><a ref="' + o.id + '" iconFontClass="'+o.iconfont+'" rel="'+ o.url + '" href="javascript:void(0)">'+o.text+'</a></dd>';
					});
					menulist+='</dl></li>';
				}else{
					menulist+='<li class="primary"><a href="javascript:void(0)" class="primary-a"><i class="iconfont '+sm.iconfont+'"></i>'+sm.text+'<i class="iconfont icon-xiangyou"></i></a><dl></dl></li>';
				}
				
			});
			
			$("#menuUl").html(menulist);
			
			IniLeftClickEvent();
		
		}
  	});
}


function IniLeftClickEvent(){
	
	$(".ml-list ul li a.primary-a").off('click').on('click', function() {
	    var $parent_li = $(this).parent()
	    if($parent_li.attr('class').indexOf('active') > -1) {
	      $parent_li.removeClass('active').find('dl').slideUp()
	    } else {
	      $parent_li.addClass('active').siblings().removeClass('active').find('dl').slideUp()
	      $parent_li.find('dl').slideDown()
	    }
	});
	
	
	$(".ml-list ul li dl dd a").off('click').on('click', function() {
	    var $parent_li = $(this).parent().parent().parent()
	    $parent_li.siblings().find('dl dd').removeClass('active')
	    $(this).parent().siblings().removeClass('active')
	    var _class = $(this).parent().attr('class') || ''
	    if(_class.indexOf('active') === -1) $(this).parent().addClass('active')
	    
	    
	    var tabTitle=$(this).text();
		var url=$(this).attr("rel");
		var menuid=$(this).attr("ref");
		var listicon=$(this).attr("iconFontClass");
		
		addTab(tabTitle,url,listicon,menuid);
	    
	    
	})
	
	
}


/**点击菜单：增加一个选项卡*/
function addTab(subtitle,url,icon,menuid) {
	
	var userName="${sessionScope.USERNAME}";
	if(userName==null || !userName){
		$.messager.alert("提示","长时间未操作或获取不到用户信息！<br>请重新登录", "error", function(){
			dologoutError();
		}); 
	}
	
	//动态添加国际化语言参数
	var language="${sessionScope.language}";
	
	if(url.indexOf("?")>=0){
		if(url.lastIndexOf("&")==-1){
			url+="&language="+language;
		}
		if(url.lastIndexOf("&")==(url.length()-1)){
			url+="language="+language;
		}
	}else{
		url+="?language="+language;
	}
	
	
	$.ajax({
		type:"post",
		url:url,
		timeout:30*1000,
		datatype:'html',
		error:function(jqXHR, textStatus, errorThrown){
				if(textStatus=="error"){
					$.messager.alert("加载出错","对不起,加载出错！","error");
				}
				if(textStatus=="timeout"){
					$.messager.alert("加载超时","对不起,加载信息超时！<br>请刷新页面","error");
				}
				
				$("#rightmask").hide();
		    	$("#progressBar_right").hide();
		},
		beforeSend:function(){
			$("#rightmask").show();
			$("#progressBar_right").show();
		},
		success:function(html){
	    	$("#rightmask").hide();
	    	$("#progressBar_right").hide();
	    	if(html){
	    		if(!$("#tabs").tabs("exists",subtitle)){//不存在该选项卡
	    			$("#tabs").tabs("add",{
	    				title:subtitle,
	    				content:createFrame(url),
	    				closable:true,
	    				iconCls:"iconfont "+icon
	    			});
	    		}else{//存在该选项卡
	    			$("#tabs").tabs("select",subtitle);
	    			$('#mm-tabupdate').click();
	    		}
	    		
	    		tabClose();
	    		$("#tabs").tabs({
	    			onSelect:function(title){
	    				if(title=="首页"){
	    					refreshHome();
	    				}
	    			}
	    		});
	    		
	    		$("#tabs").tabs('select',subtitle);
	    		
	    	}
	    	else{
	    	}
		}
	});
}



function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$("#mm").menu("show", {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$("#mm").data("currtab", subtitle);
		$("#tabs").tabs("select", subtitle);
		return false;
	});
}


// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url){
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			});
		}
		
		var id = currTab.panel('options').id;
		if(id=="home"){
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame('<%=basePath%>/main/home.do')
				}
			});
		}
		
	});
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) {	
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	
	$('#mm-newOpen').click(function() {
		alert(basePath);
		window.open(window.location.href);
	});
	
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			//$.messager.alert('提示','右侧没有选项卡','error');
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			$.messager.alert('提示','左侧没有选项卡','error');
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}


//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}


function createFrame(url) {	
	var s = '<iframe scrolling="no" frameborder="0"  src="' + url + '" style="width:100%;height:100%;	"></iframe>';
	return s;
}


</script>

  
</html>
