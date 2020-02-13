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
			<div id="saveEditCenter" class="saveEditCenter">
				<div style="overflow: scroll; scrolling: yes;width: 300px;height:400px;">
					<ul id="tree" class="tree" style="overflow:auto;">
						
					</ul>
				</div>
			</div>
			
			<div class="addEditButtonDiv">
				<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
				&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	
	var zTree;
	
	$(function(){
		
		//设置中间区域的高度		
		setAddEditDivHeight();
		
		
		var treeNodes = eval(${treeNodes});
		$("#tree").tree({
			checkbox:true,
			cascadeCheck:false,
		   	data:treeNodes,
		   	onCheck:function(node,checked){
                var childNode = $("#tree").tree("getChildren",node.target);
                if(checked){
                	 for(var i= 0;i<childNode.length;i++){
                		 $("#tree").tree("check", childNode[i].target);
            		 }
                }else{
                	for(var i= 0;i<childNode.length;i++){
                		$("#tree").tree("uncheck", childNode[i].target);
            		 }
                }
               
        	}
		}); 
	
	
	});
	
	
	//保存
	 function save()
	{
		var nodes = $("#tree").tree('getChecked',['checked','indeterminate']);
		
		var tmpNode;
		var ids = "";
		for(var i=0; i<nodes.length; i++){
			tmpNode = nodes[i];
			if(i!=nodes.length-1){
				ids += tmpNode.id+",";
			}else{
				ids += tmpNode.id;
			}
		}
		var ROLE_ID = "${ROLE_ID}";
		var url = "<%=basePath%>role/saveMenuRights.do";
		var postData;
		postData = {"ROLE_ID":ROLE_ID,"menuIds":ids};
		
		top.showMsgProgress(); //显示进度条
		$.ajax({
			type:"POST",
			url:url,
			data:postData,
			dataType:"json",
			cache:false,
			success:function(data){
				top.closeMsgProgress();//关闭进度条
				if("success"==data.result){
					top.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	 }
	
	
</script>

</html>
