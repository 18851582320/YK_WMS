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
	<link rel="stylesheet" href="plugins/ace/css/font-awesome.css" />
	<link rel="stylesheet" href="plugins/ace/css/ace-fonts.css" />

</head>
<body class="addEditDivBody">
	<div class="addEditDiv">
		<div class="">
			<div id="saveEditCenter" class="saveEditCenter" style="height: 460px;">
				<div style="min-height: 450px;height: ${pdLstSize*36}px;margin: 5px;">
					<table class="easyui-datagrid" id="columnHideSetTable" data-options="fit:true" toolbar="">
						<thead>
							<tr>
								<th data-options="field:'sysDataDeFieldCode',width:150,align:'center'">字段编码</th>
								<th data-options="field:'sysDataDeFieldName',width:150,align:'center'">字段中文名称</th>
								<th data-options="field:'sysDataDeFieldType',width:100,align:'center'">字段类型</th>
								<th data-options="field:'sysDataDeFieldMemo',width:120,align:'center'">字段描述</th>
								<th data-options="field:'opSet',width:130,align:'center'">是否设置为隐藏列</th>
							</tr>
					    </thead>
					    <tbody>
					    	<!-- 按钮循环 -->
							<c:forEach items="${pdLst}" var="pd" varStatus="pdSta">
								<tr>
									<td >${pd.sysDataDeFieldCode }</td>
									<td >${pd.sysDataDeFieldName }</td>
									<td >${pd.sysDataDeFieldType }</td>
									<td >${pd.sysDataDeFieldMemo }</td>
									<td class='center'  style="height: 20px;">
										<label>
											<input name="${pd.sysDataDeId}" id="${pd.sysDataDeId}" onclick="setColumnHide('${pd.sysDataDeId}','${pd.sysDataId}','${pd.sysColumnHideSetId}')" class="ace ace-switch ace-switch-3" type="checkbox" <c:if test="${pd.sysColumnHideSetId ne null }">checked="checked"</c:if> >
											<span class="lbl"></span>
										</label>
									</td>
								</tr>
							</c:forEach>
					    </tbody>
					</table>
				</div>
			</div>
			<div class="addEditButtonDiv">
				&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

	$(function(){
		$("#columnHideSetTable").datagrid({
			onClickRow: function (rowIndex, rowData) {
                $(this).datagrid("unselectRow", rowIndex);
   			}
		});
	});

	function setColumnHide(sysDataDeId,sysDataId,sysColumnHideSetId){
		var isChecked=$("#"+sysDataDeId).prop("checked");
		$.ajax({
			type:"POST",
			url:"<%=basePath%>sysColumnHideSet/saveColumnHideSet.do?sysDataDeId="+sysDataDeId+"&sysDataId="+sysDataId+"&sysColumnHideSetId="+sysColumnHideSetId+"&isChecked="+isChecked,
			dataType:"json",
			cache:false,
			success:function(data){
				if("success"==data.result){
					$.messager.show({
						title:"系统提示",
						msg:"修改数据成功",
						timeout:500,
						showType:"slide"
					});
				}
			}
		});
	}
	
	
</script>


</html>
