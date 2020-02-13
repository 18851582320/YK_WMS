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
			<form action="model/${method}.do" name="modelForm" id="modelForm" method="post">
				<input type="hidden" name="createUser" id="createUser" value="${pd.createUser}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 230px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">模型名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="modelname" value="${pd.modelname}" prompt="" data-options="required:true"  title="模型名称">
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">模型分类：</td>
						 	<td style="width: 180px;">
								<select id="category"  class="easyui-combogrid" name="category" limitToList="true" style="width: 154px;height: 26px;"   
							        data-options="    
							            required:true,
							            panelWidth:260,    
							            value:'${pd.category}',    
							            idField:'workFlowTypeId',    
							            textField:'workFlowTypeName',    
							            url:'<%=basePath%>workFlowType/listForSelect',    
							            columns:[[    
							                {field:'workFlowTypeId',title:'流水号',width:60,hidden:true},    
							                {field:'workFlowTypeCode',title:'模型分类编码',width:100},    
							                {field:'workFlowTypeName',title:'模型分类名称',width:120}    
							            ]]    
							        ">
							     </select>  
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">流程定义名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="name" value="${pd.name}" prompt="" data-options="required:true"  title="流程名称">
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">流程作者：</td>
							 <td style="width: 180px;">
							 	<input class="easyui-textbox" type="text" id="process_author" name="process_author" value="${pd.createUserName}" prompt="" editable="false" data-options="required:false"  title="流程作者">					 		
							 </td>
						 	
						 </tr>
						<tr style="height: 5px"/>
						<tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">流程定义key：</td>
						 	<td style="width: 180px;" colspan="4">
								<input class="easyui-textbox" type="text" editable="false" id="process_id" name="process_id" value="${pd.process_id}" prompt="" data-options="required:true"  title="流程定义key">
							</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">模型描述：</td>
						 	<td style="width: 180px;" colspan="4">
						 		<input class="easyui-textbox" type="text" id="description" name="description" multiline="true" style="height: 70px;width: 430px;"  value="${pd.description }" title="模型描述" data-options="required:false">
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
		$("#category").combogrid({
			onSelect:function(){
				var grid=$("#category").combogrid("grid");
				var row=grid.datagrid("getSelected");
				$("#process_id").textbox("setValue",row.workFlowTypeCode);
			}
		});
		
		
	});
	
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#modelForm").form("submit",{
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
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
