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
	
	
	<style type="text/css">
		.picturebox{width:100%; height:100%;position:fixed;margin: 0px;padding: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px; border-radius:0px;background-color: #4C4C4C;}
		.moveToLeft{ background:url(static/image/picarrow.png) center no-repeat; width:3%; height:8%; position:absolute; display: block; top:39%; left:3%; cursor:pointer;z-index:100}
		.moveToRight{ background:url(static/image/picarrow1.png) center no-repeat; width:3%; height:8%; position:absolute;display: block; top:39%; right:3%;cursor:pointer;z-index:100}
		.moveToUp{ background:url(static/image/up.png) center no-repeat; position:absolute; display: block; top:2%; left:46%; cursor:pointer; width:8%; height:4%; z-index:100}
		.moveToDown{background:url(static/image/down.png) center no-repeat;position:absolute;display: block; top:79%;left:46%;cursor:pointer;width:8%; height:4%; z-index:100;}
		.picview{background:rgba(0, 0, 0, 0.5) none repeat scroll 0 0 !important; width:150px; height:52px; position:absolute; right:4%; top:5%;z-index: 50;}
		.radius10{-moz-border-radius: 10px; border-radius:10px; border:1px solid #333;}
		.picviewbtn{ background:url(static/image/picview.png) no-repeat;}
		.fangda{ background-position:0 0; width:51px; height:44px; float:left; margin:8px 0 0 18px; cursor:pointer}
		.suoxiao{ background-position:0 -44px; width:51px; height:45px; float:left;margin:8px 0 0 18px; cursor:pointer}
		.previewFile{ width:98%; height:82%;  background:#CCC; position:relative;overflow:hidden;margin-left: 1%;margin-right: 1%;margin-bottom: 1%;margin-top: 1%;}
		.smallpic{ width:80%; height:12%; margin:0 auto}
		.smallpic span{width:12%; height:100%; float:left; background:#000;margin-right: 1%;text-align: center;vertical-align: middle;}
		.smallpic a:hover img{ border:1px solid #FFF}
		.smallarrowL{ width:4%; height:90%; float:left;cursor:pointer;margin-right: 1%;background-size:100% 100%;margin-top: 0.5%;}
		.smallarrowR{ width:4%;; height:90%; float:right;cursor:pointer;background-size:50px 50px;margin-top: 0.5%;}
	</style>

</head>
<body class="addEditDivBody">
	<div class="addEditDivNoBtn" style="">
		<div class="picturebox" id="picturebox">
			
			<!-- 上下左右 -->
			<span class="moveToLeft" id="moveToLeft" onclick="toLeft()"></span>
			<span class="moveToRight" id="moveToRight" onclick="toRight()"></span>
			<span class="moveToUp" id="moveToUp" onclick="toUp()"></span>
			<span class="moveToDown" id="moveToDown" onclick="toDown()" ></span>
		
			<!-- 放大 缩小-->
			<div class="picview radius10" id="bigOrSmall">
				<span class="picviewbtn fangda" id="fangda" onclick="changeBig()"></span>
				<span class="picviewbtn suoxiao" id="suoxiao" onclick="changeSmall()"></span>
			</div>
		
			<!-- 大文件 -->
			<div class="previewFile" id="previewFile">
				<div id="fileDiv" style="height:100%;width:100%;"></div>
			</div>
			
			<!-- 索洛图 -->
			<div class="smallpic" id="smallpic">
				
			</div>
			
		</div>
	</div>
</body>

<script type="text/javascript">
	var pageSize=7;//每页显示数量
	var currentPage=1;//当前第几页
	var totPage=0;//总共的页数
	var lastIndex=0;//每页显示的最后一跳的数值
	var pageData;
	
	$(function(){
		setDivHeightNoBtn();
		getPage();
		hiddenOrDisPlayMoveButton(2);
		hiddenOrDisPayZoomBtn(2);
	});

	//重写通用方法
	function setDivHeightNoBtn(){
		var h=$(window).height();
		var w=$(window).width();
		var marginTop=parseInt($(".addEditDivNoBtn").css("margin-top"));
		var marginBottom=parseInt($(".addEditDivNoBtn").css("margin-bottom"));
		var marginLeft=parseInt($(".addEditDivNoBtn").css("margin-left"));
		var marginRight=parseInt($(".addEditDivNoBtn").css("margin-right"));
		$("#picturebox").css("height",(h-marginTop-marginBottom)+"px");
		$("#picturebox").css("width",(w-marginLeft-marginRight)+"px");
	}
	
	//初始化分页
	function getPage(){
		
		var upFileIdS="${pd.upFileIdS}";
		var tableName="${pd.tableName}";
		var tableKeyValue="${pd.tableKeyValue}";
		var queryType="${pd.queryType}";
		var queryKey="${pd.queryKey}";
		var goodsId="${pd.goodsId}";
		var gPVersionId="${pd.gPVersionId}";
		var theOrder="${pd.theOrder}";
		var fileTypeId="${pd.fileTypeId}";
		var typeTag="${pd.typeTag}";
		
		$.ajax({
			url:"<%=basePath%>uploadFile/previewPage.do",
			type:"post",
			async:false,
			data:{upFileIdS:upFileIdS,tableName:tableName,tableKeyValue:tableKeyValue,queryType:queryType,queryKey:queryKey,goodsId:goodsId,gPVersionId:gPVersionId,theOrder:theOrder,fileTypeId:fileTypeId,typeTag:typeTag},
			dataType:"json",
			success:function(data){
				if(data!=null){
					pageData=data;
					initPage();
				}
			}
		});
	}
	
	//初始化分页
	function initPage(){
		if(pageData.length>0){
			totPage = parseInt((pageData.length-1)/pageSize)+1;
	    	if(currentPage>totPage){
	    		currentPage = totPage;
	    	}
	    	if(currentPage*pageSize>pageData.length){
	    		lastIndex=pageData.length;
	    	}
       		else{
       			lastIndex=pageSize*currentPage;
       		}
       		
	    	var image="";//显示的预览小图片
	    	var content="";
			for(var i=(currentPage-1)*pageSize;i<lastIndex;i++){
				var fileRealName=pageData[i].fileRealName;
				var suffix=fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length).toLowerCase();
				if(suffix==".pdf"){
					image="static/image/pdf.jpg";
				}else if(suffix==".bmp" || suffix==".jpg" || suffix==".jpeg" || suffix==".png" || suffix==".gif"){
					image='<%=basePath%>'+pageData[i].filePath+''+pageData[i].fileRealName;
				}else if(suffix==".avi" || suffix==".rmvb" || suffix==".rm" || suffix==".mp4" || suffix==".mkv"){
					image="static/image/movie.jpg";
				}else{
					image="static/image/file.jpg";
				}
				content+="<a><span><img src='"+image+"' width='100%' height='100%' onclick='javacript:previewFile("+i+")'/></span></a>";
			}
			content="<em class='smallarrowL' id='goleft' onclick='PageNextOrPre(1)'><img src='static/image/toLeft.png' style='width:100%; height:100%; '/></em>"+content+"<em class='smallarrowR' id='goright' onclick='PageNextOrPre(2)'><img src='static/image/toRight.png' style='width:100%; height:100%; '/></em>";
			$("#smallpic").html(content);
		}
	}
	
	
	//左右翻页
	function PageNextOrPre(pageTag){
		if(pageTag==1){//向左翻页
			if(currentPage<=1){//如果当前页小于等于1
				return;
			}else{
				currentPage--;
			}
		}else if(pageTag==2){//向右翻页
			if(currentPage>=totPage){
				return;
			}else{
				currentPage++;
			}
		}
		
		initPage();
		
	}
	
	//预览文件
	function previewFile(index){
		var  upFileId=pageData[index].upFileId;
		var  tableName=pageData[index].tableName;
		var  tableKeyValue=pageData[index].tableKeyValue;
		var  filePath=pageData[index].filePath;
		var  fileRealName=pageData[index].fileRealName;
		
		var suffix=fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length).toLowerCase();
		var fileUrl='<%=basePath%>'+filePath+fileRealName;
		if(suffix==".pdf"){
			hiddenOrDisPlayMoveButton(2);
			hiddenOrDisPayZoomBtn(2);
			$("#fileDiv").html("<iframe id='frameId' src='"+fileUrl+"' style='width: 100%;height:100%;' frameborder='0' scrolling='no'></iframe>");
		}else if(suffix==".bmp" || suffix==".jpg" || suffix==".jpeg" || suffix==".png" || suffix==".gif"){
			hiddenOrDisPlayMoveButton(1);
			hiddenOrDisPayZoomBtn(1);
			$("#fileDiv").html("<img src='"+fileUrl+"' id='currentFile' />");
		}else{
			hiddenOrDisPlayMoveButton(2);
			hiddenOrDisPayZoomBtn(2);
			
			var fileDivH=parseInt($("#fileDiv").height())-15;
			var fileDivW=parseInt($("#fileDiv").width());
			
			var frameSrc='<%=basePath%>uploadFile/previewFile.do?fileRealName='+fileRealName+'&fileUrl='+fileUrl+'&fileDivH='+fileDivH+'&fileDivW='+fileDivW;
			$("#fileDiv").html("<iframe id='frameId' src='"+frameSrc+"' style='width: 100%;height:100%;' frameborder='0' scrolling='no'></iframe>");
		}
		
	}
	
	
	function hiddenOrDisPlayMoveButton(flag){
		if(flag==1){
			
			$("#moveToLeft").css("display","block");
			$("#moveToRight").css("display","block");
			$("#moveToUp").css("display","block");
			$("#moveToDown").css("display","block");
		}else if(flag==2)
		{
			$("#moveToLeft").css("display","none");
			$("#moveToRight").css("display","none");
			$("#moveToUp").css("display","none");
			$("#moveToDown").css("display","none");
		}
	}
	
	function hiddenOrDisPayZoomBtn(flag){
		if(flag==1){
			$("#bigOrSmall").css("display","block");
		}else if(flag==2){
			$("#bigOrSmall").css("display","none");
		}
	}
	
	
	/**
	 * 放大
	 */
	function changeBig(){
		var currentFile=document.getElementById("currentFile");
		if(!currentFile)return;
		currentFile.width=currentFile.width*1.05;
		currentFile.height=currentFile.height*1.05;
		if(document.getElementById('previewFile').scrollHeight>document.getElementById('previewFile').offsetHeight)//有滚动条
		{
			hiddenOrDisPlayMoveButton(1);
		}
	}
	/**
	 * 缩小
	 */
	function changeSmall(){
		var currentFile=document.getElementById("currentFile");
		if(!currentFile)return;
		currentFile.width=currentFile.width/1.05;
		currentFile.height=currentFile.height/1.05;
		if(document.getElementById('previewFile').scrollHeight<=(document.getElementById('previewFile').offsetHeight+5))//没有滚动条
		{
			hiddenOrDisPlayMoveButton(2);
		}
	}
	
	/**
	 * 向左
	 */
	function toLeft(){
		if(document.getElementById('previewFile').scrollHeight>document.getElementById('previewFile').offsetHeight)//有滚动条
		{
			document.getElementById('previewFile').scrollLeft=document.getElementById('previewFile').scrollLeft-50;
		}
	}
	/**
	 * 向右
	 */
	function toRight(){
		if(document.getElementById('previewFile').scrollHeight>document.getElementById('previewFile').offsetHeight)//有滚动条
		{
			document.getElementById('previewFile').scrollLeft=document.getElementById('previewFile').scrollLeft+50;
		}
	}
	/**
	 * 向上
	 */
	function toUp(){
		if(document.getElementById('previewFile').scrollHeight>document.getElementById('previewFile').offsetHeight)//有滚动条
		{
			document.getElementById('previewFile').scrollTop=document.getElementById('previewFile').scrollTop-50;
		}
	}
	/**
	 * 向下
	 */
	function toDown(){
		if(document.getElementById('previewFile').scrollHeight>document.getElementById('previewFile').offsetHeight)//有滚动条
		{
			document.getElementById('previewFile').scrollTop=document.getElementById('previewFile').scrollTop+50;
		}
	}
	
	
	
	
</script>


</html>
