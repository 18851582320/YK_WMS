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
			<div id="saveEditCenter" class="saveEditCenter" style="height: 460px;">
				<table>
					<c:forEach items="${pdLst}" var="pd" varStatus="pdSta">
						<c:choose>
							<c:when test="${pd.sysDataDeFieldType eq 'nvarchar' or pd.sysDataDeFieldType eq 'varchar'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 200px;">
								 		<input class="easyui-textbox" type="text"  name="${pd.sysDataDeFieldCode}" id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }"  value=""  style="width: 330px;height: 26px;">
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
							<c:when test="${pd.sysDataDeFieldType eq 'DATE' or pd.sysDataDeFieldType eq 'date'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 340px;">
								 		<input class="easyui-datebox" type="text"  id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }" name="${pd.sysDataDeFieldCode}"  style="width: 156px;height: 26px;">
								 		到
								 		<input class="easyui-datebox" type="text"  id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }⊙⊙⊙⊙⊙⊙2" name="${pd.sysDataDeFieldCode}"  style="width: 156px;height: 26px;">
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
							<c:when test="${pd.sysDataDeFieldType eq 'datetime' or pd.sysDataDeFieldType eq 'DATETIME'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 340px;">
								 		<input class="easyui-datetimebox" type="text"  id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }" name="${pd.sysDataDeFieldCode}"  style="width: 156px;height: 26px;">
								 		到
								 		<input class="easyui-datetimebox" type="text"  id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }⊙⊙⊙⊙⊙⊙2" name="${pd.sysDataDeFieldCode}"  style="width: 156px;height: 26px;">
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
							<c:when test="${pd.sysDataDeFieldType eq 'decimal'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 340px;">
								 		<input type="text" class="easyui-numberbox" id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }" name="${pd.sysDataDeFieldCode}"  data-options="min:0,precision:6" style="width: 156px;height: 26px;"></input>
								 		到
								 		<input type="text" class="easyui-numberbox" id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }⊙⊙⊙⊙⊙⊙2" name="${pd.sysDataDeFieldCode}"  data-options="min:0,precision:6" style="width: 156px;height: 26px;"></input>    
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
							<c:when test="${pd.sysDataDeFieldType eq 'int'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 340px;">
								 		<input type="text" class="easyui-numberbox" id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }" name="${pd.sysDataDeFieldCode}"  data-options="min:0,precision:0" style="width: 156px;height: 26px;"></input> 
								 		到
								 		<input type="text" class="easyui-numberbox" id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }⊙⊙⊙⊙⊙⊙2" name="${pd.sysDataDeFieldCode}"  data-options="min:0,precision:0" style="width: 156px;height: 26px;"></input>   
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
							<c:when test="${pd.sysDataDeFieldType eq 'boolean' or pd.sysDataDeFieldType eq 'BOOLEAN'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 340px;">
								 		<select id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }" name="${pd.sysDataDeFieldCode}" class="easyui-combobox" limitToList="true" style="width: 330px;height: 26px;">
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
							<c:when test="${pd.sysDataDeFieldType eq 'booleanVarchar' or pd.sysDataDeFieldType eq 'BOOLEANVARCHAR'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 340px;">
								 		<select id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }" name="${pd.sysDataDeFieldCode}" class="easyui-combobox" limitToList="true" style="width: 330px;height: 26px;">
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
							<c:when test="${pd.sysDataDeFieldType eq 'specialSelect' or pd.sysDataDeFieldType eq 'SPECIALSELECT'}">
								<tr style="height: 5px"/>
								<tr style="height: 30px">
									<td style="width: 120px;text-align: right;">${pd.sysDataDeFieldName}:&nbsp;</td>
								 	<td style="width: 340px;">
								 		<select id="${pd.sysDataDeFieldCode}⊙⊙⊙⊙⊙⊙${pd.sysDataDeFieldType }" name="${pd.sysDataDeFieldCode}" class="easyui-combobox" limitToList="true" style="width: 330px;height: 26px;"
								 			data-options="    
									            valueField:'speSelectValue',    
									            textField:'speSelectText',    
									            url:'<%=basePath%>sysQueryConditionSet/specialSelect.do?sysDataIndex=${pd.sysDataIndex}&sysDataDeFieldCode=${pd.sysDataDeFieldCode}'    
									        ">
										</select>
								 	</td>
								 	<td style="width: 5px;">&nbsp;</td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				
				</table>
			</div>
			<div class="addEditButtonDiv">
				是否精确查询： <input class="easyui-switchbutton" id="isAccurateQuery" data-options="onText:'是',offText:'否'">
				&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="queryBtn" onclick="saveQuery();"><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
				&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	var conObj;
	
	//注册回车登录事件
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#queryBtn").trigger("click");
		}
	});
	
	
	function saveQuery(){
		conObj={};
		var fieldName="";
		var inputObjs =$("#saveEditCenter").find("input[type='text']"); 
		if(inputObjs!=null && inputObjs.length>0){
			for(var i=0;i<inputObjs.length;i++){
				var id=inputObjs[i].id;
				if(id.indexOf("⊙⊙⊙⊙⊙⊙")!=-1){
					var arr=id.split("⊙⊙⊙⊙⊙⊙");
					if(arr!=null){
						if(arr.length==2){
							fieldName=arr[0];
							conObj[fieldName]=inputObjs[i].value;
						}
						if(arr.length==3){
							fieldName=arr[0]+""+arr[2];
							conObj[fieldName]=inputObjs[i].value;
						}
					}
				}else{
					continue;
				}
			}
		}
		
		
		var selectObjs =$("#saveEditCenter").find("select"); 
		if(selectObjs!=null && selectObjs.length>0){
			for(var i=0;i<selectObjs.length;i++){
				var id=selectObjs[i].id;
				if(id.indexOf("⊙⊙⊙⊙⊙⊙")!=-1){
					var arr=id.split("⊙⊙⊙⊙⊙⊙");
					if(arr!=null){
						if(arr.length==2){
							fieldName=arr[0];
							if(arr[1]=="boolean" || arr[1]=="BOOLEAN"){
								if(selectObjs[i].value=="是"){
									conObj[fieldName]="1";
								}
								else if(selectObjs[i].value=="否"){
									conObj[fieldName]="0";
								}else{
									conObj[fieldName]=$("#"+id).combobox("getValue");
								}
							}else if(arr[1]=="specialSelect" || arr[1]=="SPECIALSELECT"){
								conObj[fieldName]=$("#"+id).combobox("getValue");
							}else{
								conObj[fieldName]=$("#"+id).combobox("getValue");
							}
						}
					}
				}else{
					continue;
				}
			}
		}
		
		
		var isAccurateQuery=$("#isAccurateQuery").switchbutton("options").checked;
		conObj["isAccurateQuery"]=isAccurateQuery;
		
		$("#saveEditCenter").hide();
		top.Dialog.close();
		
	}
	
	//获取查询条件
	function getQueryConObj(){
		return conObj;
	}
	
	
</script>


</html>
