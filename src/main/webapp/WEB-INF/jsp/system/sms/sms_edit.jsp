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
			<form action="sms/${method}.do" name="dataForm" id="dataForm" method="post">
				<input type="hidden" name="smsId" id="smsId" value="${pd.smsId}"/>
				<input type="hidden" name="fromUser" id="fromUser" value="${pd.fromUser}"/>
				<input type="hidden" name="toUser" id="toUser" value="${pd.toUser}"/>
				<input type="hidden" name="toUserAll" id="toUserAll" value="${pd.toUserAll}"/>
				<input type="hidden" name="toUserAllBianMa" id="toUserAllBianMa" value="${pd.toUserAllBianMa}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 460px;">
					<table>
						<tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 120px;text-align: right;">标题：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="title"   name="title" value="${pd.title}" prompt="" data-options="required:false"  title="公告标题">
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">接收人员：</td>
						 	<td style="width: 180px;" colspan="4">
						 		<input class="easyui-textbox" editable="false" type="text" id="toUserAllName" name="toUserAllName"  value="${pd.staffName}" prompt="" style="width: 552px;" data-options="required:true"  title="接收人员">	
						 	</td>
						 </tr>
						 <tr style="height: 15px"/>
						 <tr style="height: 70px">
						 	<td style="width: 120px;text-align: right;">消息内容：</td>
						 	<td style="width: 900px;" colspan="7">
						 		 <input class="easyui-textbox" type="text" name="content" value="${pd.content}" style="width: 825px;height: 360px;" prompt="" multiline="true" data-options="required:true"  title="公告内容">
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
	
	
	$(function (){
		$("#toUserAllName").textbox("textbox").bind("click", function () {
			selectStaff('Name','STAFF_ID',STAFFCallback,"mul","","");
		});
		
	});
	
	//选择人员回掉函数
	function STAFFCallback(select){
		
		if(select!=null && select.length>0){
			var toUserAll="";
			var toUserAllName="";
			var toUserAllBianMa="";
			for(var i=0;i<select.length;i++){
				if(i==0){
					toUserAll=select[i].STAFF_ID;
					toUserAllName=select[i].NAME;
					toUserAllBianMa=select[i].BIANMA;
				}else{
					toUserAll=toUserAll+","+select[i].STAFF_ID;
					toUserAllName=toUserAllName+","+select[i].NAME;
					toUserAllBianMa=toUserAllBianMa+","+select[i].BIANMA;
				}
			}
			
			$("#toUserAll").val(toUserAll);
			$("#toUserAllName").textbox("setValue",toUserAllName);
			$("#toUserAllBianMa").val(toUserAllBianMa);
		}
		
		
	};
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#dataForm").form("submit",{
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
					
					var toUserAllBianMa= $("#toUserAllBianMa").val();
					var toUserAllBianMaArray=toUserAllBianMa.split(",");
					for(var i=0;i<toUserAllBianMaArray.length;i++){
						top.senSmsNoMsg(toUserAllBianMaArray[i]);
					}
					
					top.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
