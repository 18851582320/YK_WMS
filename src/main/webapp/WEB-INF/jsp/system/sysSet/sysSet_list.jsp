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
	
	<style type="text/css">
		.editMiddleTr{height: 5px;}
		.editTr{height: 30px;}
		.editTdTitle{width: 180px;text-align: right;}
		.editTdValue{width: 250px;}
		.editTdMid{width: 5px;}
		.editTdInput{width: 250px;height: 26px;}
	</style>
	
</head>
<body>
	<!-- dataGrid -->
	<div class="main_tableDivEdit">
		<div class="">
			<form action="sysSet/edit.do" name="sysSetForm" id="sysSetForm" method="post">
				<div id="saveEditCenter" class="saveEditCenter" style="">
					<table>
						<tr style="height: 5px;"></tr>
						<tr class="editTr">
							<td class="editTdTitle">生产计划创建模式:&nbsp;</td>
							<td class="editTdValue">
								<select id="producePlanCreateType" name="producePlanCreateType"  class="easyui-combobox editTdInput" data-options="value:'${producePlanCreateType}'">
									<option value="1">关联BOM版本</option>
									<option value="2">不关联BOM版本</option>
								</select>
							</td>
							<td class="editTdMid"></td>
							<td class="editTdTitle">生产计划工艺关联模式:&nbsp;</td>
							<td class="editTdValue">
								<select id="producePlanProcessType" name="producePlanProcessType" class="easyui-combobox editTdInput" data-options="value:'${producePlanProcessType}'" >
									<option value="1">关联BOM版本下的工艺版本</option>
									<option value="2">关联物料的默认工艺版本</option>
									<option value="3">关联工艺模板</option>
								</select>
							</td>
						</tr>
						<tr style="height: 5px;"></tr>
						<tr class="editTr">
							<td class="editTdTitle">车间作业派工模式:&nbsp;</td>
							<td class="editTdValue">
								<select id="produceProcessSendType" name="produceProcessSendType"  class="easyui-combobox editTdInput" data-options="value:'${produceProcessSendType}'">
									<option value="1">按订单派工（可分批，工序派工数量相同）</option>
									<option value="2">按工序派工（可批量，工序派工数量不同）</option>
								</select>
							</td>
							<td class="editTdMid"></td>
							<td class="editTdTitle">app最新版本号:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="appVersion" editable="true"  name="appVersion" value="${appVersion}" prompt="" style="width: 250px;" data-options="required:false"  title="">
							</td>
						</tr>
					</table>
				</div>
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
				</div>
			</form>
		</div>
	</div>
</body>


<script type="text/javascript">

	$(function(){
		
		//动态设定datagrid的高度
		settableDivEditHeight();
		window.onresize=function(){  
			settableDivEditHeight();
		}; 
		
	});
	
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#sysSetForm").form("submit",{
			onSubmit: function(){
				var isValid = $(this).form("validate");
				
				var producePlanCreateType=$("#producePlanCreateType").combobox("getValue");
				var producePlanProcessType=$("#producePlanProcessType").combobox("getValue");
				if(producePlanCreateType=="2" && producePlanProcessType=="1"){
					$.messager.alert("系统提示","生产计划创建模式为【不关联BOM版本】时，生产计划工艺关联模式不可选择【关联BOM版本下的工艺版本】","error");
					isValid=false;
				}
				
				if (!isValid){
					top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
				}
				
				return isValid;//返回false终止表单提交
			},
			success:function(data){
				top.closeMsgProgress();//关闭进度条
				var data = eval("("+data+")");  
				if("success"==data.result){
					$.messager.show({
						title:"系统提示",
						msg:"保存成功",
						timeout:3000,
						showType:"slide"
					});
					window.location.reload();
				}else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
	
	
</script>

  
</html>
