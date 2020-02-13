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
			<form action="workFlowListener/${method}.do" name="workFlowListenerForm" id="workFlowListenerForm" method="post">
				<input type="hidden" name="wfListenerId" id="wfListenerId" value="${pd.wfListenerId}"/>
				<input type="hidden" name="wfListenerEvent" id="wfListenerEvent" value="${pd.wfListenerEvent}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 360px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="wfListenerName" value="${pd.wfListenerName}" prompt="" data-options="required:true"  title="名称">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						  <tr>
						 	<td style="width: 90px;text-align: right;">类型：</td>
						 	<td style="width: 180px;">
						 		<select id="wfListenerType" name="wfListenerType" class="easyui-combobox" limitToList="true"  style="width: 154px;height: 26px;" data-options="required:true">
									<option value="" <c:if test="${pd.wfListenerId ==null }">selected</c:if>></option>
									<option value="1" <c:if test="${pd.wfListenerType == 1}">selected</c:if>>执行监听器</option>
									<option value="2" <c:if test="${pd.wfListenerType == 2}">selected</c:if>>用户任务监听器</option>
								</select>
						 	</td>
						  </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">类型：</td>
						 	<td style="width: 180px;">
						 					<label id="wfListenerEventRadio1"><input name="wfListenerEventRadio" type="radio"   <c:if test="${pd.wfListenerEvent == 'start' }">checked="checked"</c:if> onclick="setType('start');" value="start" />start</label> 
								&nbsp;&nbsp;<label id="wfListenerEventRadio2"><input name="wfListenerEventRadio" type="radio"   <c:if test="${pd.wfListenerEvent == 'end' }">checked="checked"</c:if> onclick="setType('end');" value="end"/>end</label> 
								&nbsp;&nbsp;<label id="wfListenerEventRadio3"><input name="wfListenerEventRadio" type="radio"   <c:if test="${pd.wfListenerEvent == 'take' }">checked="checked"</c:if> onclick="setType('take');" value="take"/>take</label> 
											<label id="wfListenerEventRadio4"><input name="wfListenerEventRadio" type="radio"   <c:if test="${pd.wfListenerEvent == 'create' }">checked="checked"</c:if> onclick="setType('create');" value="create"/>create</label> 
								&nbsp;&nbsp;<label id="wfListenerEventRadio5"><input name="wfListenerEventRadio" type="radio"   <c:if test="${pd.wfListenerEvent == 'assignment' }">checked="checked"</c:if> onclick="setType('assignment');" value="assignment"/>assignment</label> 
								&nbsp;&nbsp;<label id="wfListenerEventRadio6"><input name="wfListenerEventRadio" type="radio"   <c:if test="${pd.wfListenerEvent == 'complete' }">checked="checked"</c:if> onclick="setType('complete');" value="complete"/>complete</label> 
						 	</td>
						  </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">执行类型：</td>
						 	<td style="width: 180px;">
						 		<select id="wfListenerValueType" name="wfListenerValueType" class="easyui-combobox" limitToList="true" style="width: 154px;height: 26px;" data-options="required:true">
									<option value="" <c:if test="${pd.wfListenerId ==null }">selected</c:if>></option>
									<option value="1" <c:if test="${pd.wfListenerValueType == 1}">selected</c:if>>Java Class</option>
									<option value="2" <c:if test="${pd.wfListenerValueType == 2}">selected</c:if>>expresssion</option>
								</select>
						 	</td>
						  </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">执行内容：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="wfListenerValue" name="wfListenerValue" multiline="true" style="height: 70px;width: 350px;"  value="${pd.wfListenerValue }" title="执行内容" data-options="required:false">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">备注：</td>
						 	<td style="width: 180px">
						 		<input class="easyui-textbox" type="text" id="wfListenerMemo" name="wfListenerMemo" multiline="true" style="height: 70px;width: 350px;"  value="${pd.wfListenerMemo }" title="备注" data-options="required:false">
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
		
		var wfListenerId="${pd.wfListenerId}";
		var wfListenerType="${pd.wfListenerType}";
		
		$("#wfListenerType").combobox({    
			onChange:function(newValue,oldVale){
				if(newValue=="1"){
					
					$("#wfListenerEventRadio1").show();
					$("#wfListenerEventRadio2").show();
					$("#wfListenerEventRadio3").show();
					
					$("#wfListenerEventRadio4").hide();
					$("#wfListenerEventRadio5").hide();
					$("#wfListenerEventRadio6").hide();
					
					$("input:radio[name='wfListenerEventRadio']").eq(0).attr("checked",'checked');
					$("#wfListenerEvent").val("start");
					
				}
				if(newValue=="2"){
					$("#wfListenerEventRadio1").hide();
					$("#wfListenerEventRadio2").hide();
					$("#wfListenerEventRadio3").hide();
					
					$("#wfListenerEventRadio4").show();
					$("#wfListenerEventRadio5").show();
					$("#wfListenerEventRadio6").show();
					
					$("input:radio[name='wfListenerEventRadio']").eq(3).attr("checked",'checked');
					$("#wfListenerEvent").val("create");
				}
			}   
		});  
		
		if(wfListenerId){//编辑页面
			if(wfListenerType=="1"){
				$("#wfListenerEventRadio4").hide();
				$("#wfListenerEventRadio5").hide();
				$("#wfListenerEventRadio6").hide();
			}else if(wfListenerType=="2"){
				$("#wfListenerEventRadio1").hide();
				$("#wfListenerEventRadio2").hide();
				$("#wfListenerEventRadio3").hide();
			}
		}else{//增加页面
			$("#wfListenerEventRadio1").hide();
			$("#wfListenerEventRadio2").hide();
			$("#wfListenerEventRadio3").hide();
			$("#wfListenerEventRadio4").hide();
			$("#wfListenerEventRadio5").hide();
			$("#wfListenerEventRadio6").hide();
		}
		
	});
	
	
	function setType(stateValue){
		$("#wfListenerEvent").val(stateValue);
	}
	
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#workFlowListenerForm").form("submit",{
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
				}else if("codeRepeat"==data.result){
					$.messager.alert("系统提示","编码重复，请重新保存","error");
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
