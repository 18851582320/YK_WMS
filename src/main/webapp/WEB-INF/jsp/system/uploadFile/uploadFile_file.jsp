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
		.previewIframe{margin-top: 5px;margin-left: 5px;margin-right: 5px;margin-bottom:8px;background-color: #fafafa;}
		.picTurePage{width: 100%;height: 85px;}
		.picTurePage .miniFile{width: 76%;height: 100%;margin-left: 13%;}
		.picTurePage .miniFile span{width:12%; height:93%; float:left; background:#ffffff;margin-right: 1%;text-align: center;vertical-align: middle;}
		.picTurePage .miniFile span .iconfont{font-size: 70px;color: blue;font-weight: bold;}
		.picTurePage .miniFile a:hover img{ border:1px solid #FFF}
		.smallarrowL{ width:4%; height:90%; float:left;cursor:pointer;margin-right: 1%;background-size:100% 100%;}
		.smallarrowR{ width:4%;; height:90%; float:right;cursor:pointer;background-size:50px 50px;}
		
	</style>

</head>
<body class="addEditDivBody">
	<div class="addEditDivNoBtn" style="">
		<div id="saveEditCenter" class="saveEditCenter" style="background-color: #CCCCCC;">
			<div class="previewIframe" >
				<iframe id="fileIframe" class="fileIframe"></iframe>
			</div>
			<div class="picTurePage" id="picTurePage">
				<div class="miniFile" id="miniFile">
					
				</div>
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
	});

	//重写通用方法
	function setDivHeightNoBtn(){
		var h=$(window).height();
		var marginTop=parseInt($(".addEditDivNoBtn").css("margin-top"));
		var marginBottom=parseInt($(".addEditDivNoBtn").css("margin-bottom"));
		$("#saveEditCenter").css("height",(h-marginTop-marginBottom)+"px");
		
		var picH=$("#picTurePage").height();
		//$("#previewIframe").css("height",(h-marginTop-marginBottom-picH-13)+"px");
		$("#fileIframe").css("height",(h-marginTop-marginBottom-picH-13-2)+"px");
		
	}
	
	//初始化分页
	function getPage(){
		$.ajax({
			url:"<%=basePath%>uploadFile/previewPage.do",
			type:"post",
			async:false,
			data:{},
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
				var suffix=fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length);
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
			$("#miniFile").html(content);
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
		
		$("#fileIframe").attr("src",'<%=basePath%>department/departmentListIndex.do?PARENT_ID='+DEPARTMENT_ID);
	}
	
	
</script>


</html>
