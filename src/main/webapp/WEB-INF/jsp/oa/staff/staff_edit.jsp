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
			<form action="staff/${method}.do" name="staffForm" id="staffForm" method="post">
				<input type="hidden" name="codeRuleType" id="codeRuleType" value="${codeRuleType}"/>
				<input type="hidden" name="STAFF_ID" id="STAFF_ID" value="${pd.STAFF_ID}"/>
				<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID}"/>
				<input type="hidden" name="teamId" id="teamId" value="${pd.teamId}"/>
				<input type="hidden" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 410px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">员工编码：</td>
						 	<td style="width: 180px;">
						 		<c:choose>
						 			<c:when test="${pd.STAFF_ID == null}">
						 				<input class="easyui-textbox" type="text" id="BIANMA" editable="${codeRuleType eq 1?true:false}"  name="BIANMA" value="${code}" prompt="" data-options="required:true"  title="员工编码">
						 			</c:when>
						 			<c:otherwise>
						 				<input class="easyui-textbox" type="text" id="BIANMA" editable="false"  name="BIANMA" value="${pd.BIANMA}" prompt="" data-options="required:true"  title="员工编码">
						 			</c:otherwise>
						 		</c:choose>
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">姓名：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="NAME" name="NAME" value="${pd.NAME}" prompt="" data-options="required:true"  title="姓名 ">
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">用户名：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="USERNAME" name="USERNAME" value="${pd.USERNAME}" editable="${pd.STAFF_ID ne null?false:true}" prompt="" data-options="required:true"  title="用户名 ">
						 	</td>
						 	
						 	<c:if test="${pd.STAFF_ID eq null}">
							 	<td style="width: 5px;">&nbsp;</td>
							 	<td style="width: 90px;text-align: right;">密码：</td>
							 	<td style="width: 180px;">
							 		<input class="easyui-textbox" type="text" id="PASSWORD" name="PASSWORD" value="111111" prompt="" data-options="required:true"  title="密码 ">
							 	</td>
						 	</c:if>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">角色：</td>
						 	<td style="width: 180px;" colspan="4">
						 		<select id="ROLE_ID"  class="easyui-combogrid" limitToList="true" name="ROLE_ID" style="width: 430px;height: 26px;"   
							        data-options="    
							            required:false,
							            panelWidth:260,
							            multiple:true,
							            required:true,        
							            value:'${pd.ROLE_ID}',    
							            idField:'ROLE_ID',    
							            textField:'ROLE_NAME',    
							            url:'<%=basePath%>role/listForSelect',    
							            columns:[[    
							                {field:'ROLE_ID',title:'流水号',width:60,hidden:true},    
							                {field:'RNUMBER',title:'角色编码',width:100},    
							                {field:'ROLE_NAME',title:'角色名称',width:120}    
							            ]]    
							        ">
							     </select>
						 	</td>
						  </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">部门：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" editable="false" type="text" id="DEPARTMENT_NAME" name="DEPARTMENT_NAME"  value="${pd.DEPARTMENT_NAME}" prompt="" data-options="required:true"  title="部门">
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">性别：</td>
						 	<td style="width: 180px;">
						 		<select id="SEX" name="SEX" class="easyui-combobox" limitToList="true" style="width: 154px;height: 26px;" data-options="required:true">
									<option value="男" <c:if test="${pd.SEX == '男'}">selected</c:if>>男</option>
									<option value="女" <c:if test="${pd.SEX == '女'}">selected</c:if>>女</option>
								</select>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">电话：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="TEL" name="TEL" value="${pd.TEL}" prompt="" data-options="required:false"  title="电话">
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 90px;text-align: right;">卡号：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" id="IdCard" name="IdCard" value="${pd.IdCard}" prompt="" data-options="required:false"  title="卡号">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">直属上级：</td>
						 	<td style="width: 180px;">
						 		<select id="higherUser" name="higherUser" class="easyui-combogrid" limitToList="true"  style="width: 154px;height: 26px;"   
							        data-options="    
							            required:false,
							            panelWidth:200,    
							            value:'${pd.higherUser}',    
							            idField:'STAFF_ID',    
							            textField:'NAME', 
							            queryParams:{
							            	 
							            },   
							            url:'<%=basePath%>staff/listAll.do',    
							            columns:[[    
							                {field:'STAFF_ID',title:'流水号',width:60,hidden:true},
							                {field:'NAME',title:'姓名',width:80},        
							                {field:'DEPARTMENT_NAME',title:'部门',width:100}    
							            ]]    
							        ">
							     </select>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 90px;text-align: right;">备注：</td>
						 	<td style="width: 180px;" colspan="4">
						 		<input class="easyui-textbox" type="text" id="BZ" name="BZ" value="${pd.BZ}" style="width: 430px;height:70px" prompt="" data-options="required:false,multiline:true"  title="备注">
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

	/* $(function(){
		$('#cc').combogrid('setValue', '002');
	}); */
	
	$(function (){
		$("#DEPARTMENT_NAME").textbox("textbox").bind("click", function () {
			selectDepartment("DEPARTMENT_Name","DEPARTMENT_ID",selectDepartmentBack,"sin");
		});
	});

	//选择部门回调函数，select：json对象
	function selectDepartmentBack(select){
		$("#DEPARTMENT_ID").val(select[0].DEPARTMENT_ID);
		$("#DEPARTMENT_NAME").textbox("setValue", select[0].NAME);
	}
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#staffForm").form("submit",{
			onSubmit: function(){
				var isValid = $(this).form("validate");
				if (!isValid){
					top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
				}
				
				
				
				//判断下拉选择
				if(isValid){
					//必输判断
					var ROLE_ID=$("#ROLE_ID").combogrid("getValues");
					if(isEmpty(ROLE_ID)){
						$.messager.alert("系统提示","请选择正确的角色信息！","error");
						top.closeMsgProgress(); 
						return false;
					}
					
					//不必输入的有text再判断是否有值
					var higherUserText=$("#higherUser").combogrid("getText");
					var higherUser=$("#higherUser").combogrid("getValue");
					if(!isEmpty(higherUserText)){
						if(isEmpty(higherUser)){
							$.messager.alert("系统提示","请选择正确的直属上级！","error");
							top.closeMsgProgress(); 
							return false;
						}
					}
					
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
					getFormCodeByRule("OA_STAFF","BIANMA","BIANMA","codeRuleType");
				}else if("userNameRepeat"==data.result){
					$.messager.alert("系统提示","用户名重复","error");
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>


</html>
