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
			<form action="sysDataSet/${method}.do" name="dataForm" id="dataForm" method="post">
				<input type="hidden" name="dataSetId" id="dataSetId" value="${pd.dataSetId }"/>
				<input type="hidden" name="dataSetTypeCode" id="dataSetTypeCode" value="${pd.dataSetTypeCode }"/>
				
				<div id="saveEditCenter" class="saveEditCenter" style="height: 260px;">
					<table>
						 <tr class="editMiddleTr" style="height: 5px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">值:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="dataSetValue"  name="dataSetValue" editable="true" value="${pd.dataSetValue}" style="width: 300px;" data-options="required:true" prompt="">
							</td>
						</tr>
						<tr class="editMiddleTr" style="height: 5px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">备注:&nbsp;</td>
							<td class="editTdValue" >
								<input class="easyui-textbox" type="text" id="dataSetMemo"  name="dataSetMemo" editable="true" value="${pd.dataSetMemo}" data-options="required:false,multiline:true" prompt="" style="width: 300px;height: 120px;">
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
		setAddEditDivHeight();
	});

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
					top.Dialog.close();
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
	} 
</script>


</html>
