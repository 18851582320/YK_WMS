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
			<form action="codeRule/${method}.do" name="codeRuleForm" id="codeRuleForm" method="post">
				<input type="hidden" name="codeRuleId" id="id" value="${pd.codeRuleId}"/>
				<div id="saveEditCenter" class="saveEditCenter" style="height: 190px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 130px;text-align: right;">表名称：</td>
						 	<td style="width: 200px;">
						 		<input class="easyui-textbox" type="text" name="tableName" editable="${pd.codeRuleId ne null?false:true}" value="${pd.tableName}" prompt="" data-options="required:true"  title="表名称">
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 130px;text-align: right;">字段名称：</td>
						 	<td style="width: 200px;">
						 		<input class="easyui-textbox" type="text" name="fieldName" editable="${pd.codeRuleId ne null?false:true}" value="${pd.fieldName}" prompt="" data-options="required:true"  title="字段名称">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 130px;text-align: right;">编码类型：</td>
						 	<td style="width: 200px;">
						 		<select id="codeRuleType" name="codeRuleType"  editable="false" class="easyui-combobox" limitToList="true" style="width: 154px;height: 26px;" data-options="required:true">
									<option value="1">手动输入</option>
									<option value="2">前缀+自增</option>
									<option value="3">前缀+年月日+流水号</option>
								</select>
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 130px;text-align: right;">前缀：</td>
						 	<td style="width: 200px;">
						 		<input class="easyui-textbox" type="text" name="prefix" value="${pd.prefix}" prompt="" data-options="required:false"  title="前缀">
						 	</td>
						 </tr>
						  <tr style="height: 5px"/>
						 <tr style="height: 30px">
						 	<td style="width: 130px;text-align: right;">流水号位数：</td>
						 	<td style="width: 200px;">
						 		<input class="easyui-numberbox" type="number" name="serialNumLength" id="serialNumLength" value="${pd.serialNumLength}" prompt="" title="请输入正整数" style="width: 154px;" data-options="required:true,min:4,precision:0" />
						 	</td>
						 	<td style="width: 5px;">&nbsp;</td>
						 	<td style="width: 130px;text-align: right;">说明：</td>
						 	<td style="width: 200px;">
						 		<input class="easyui-textbox" type="text" name="explain" value="${pd.explain}" editable="${pd.codeRuleId ne null?false:true}" prompt="" data-options="required:true"  title="说明">
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
		//设置默认选中
		var codeRuleType="${pd.codeRuleType}";
		if(codeRuleType){
			$("#codeRuleType").combobox("setValue", codeRuleType);
		}
		
	});
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#codeRuleForm").form("submit",{
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
