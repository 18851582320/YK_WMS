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
			<form action="menu/${method}.do" name="menuForm" id="menuForm" method="post">
				<input type="hidden" name="MENU_ID" id="menuId" value="${pd.MENU_ID }"/>
				<input type="hidden" name="MENU_STATE" id="MENU_STATE" value="${pd.MENU_STATE==null?1: pd.MENU_STATE}"/>
				<c:if test="${pd.MENU_ID == null}">
					<input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${null == pd.PARENT_ID ? MENU_ID:pd.PARENT_ID}"/>
				</c:if>
				
				<div id="saveEditCenter" class="saveEditCenter" style="height: 260px;">
					<table>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">上级菜单：</td>
						 	<td style="width: 180px;">
								<c:choose>
									<c:when test="${pd.MENU_ID == null}">
										<b>${null == pds.MENU_NAME ?'(无) 此项为顶级菜单':pds.MENU_NAME}</b>
									</c:when>
									<c:otherwise>
										<select id="PARENT_ID"  class="easyui-combogrid" name="PARENT_ID" style="width: 154px;height: 26px;"   
								        data-options="    
								            required:false,
								            panelWidth:260,
								            required:true,        
								            value:'${pd.PARENT_ID}',    
								            idField:'MENU_ID',    
								            textField:'MENU_NAME',    
								            url:'<%=basePath%>menu/listForSelect',    
								            columns:[[    
								                {field:'MENU_ID',title:'流水号',width:60,hidden:true},    
								                {field:'MENU_NAME',title:'菜单名称',width:150},    
								            ]]    
								        ">
								     </select>
									</c:otherwise>
								</c:choose>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">菜单名称：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-textbox" type="text" name="MENU_NAME" value="${pd.MENU_NAME }" prompt="" data-options="required:true"  title="角色名称" style="width: 162px;"> 
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">链接：</td>
						 	<td style="width: 180px;">
						 		<c:if test="${null != pds.MENU_NAME}">
									<input class="easyui-textbox" type="text" name="MENU_URL" id="menuUrl" value="${pd.MENU_URL }" prompt=""  data-options="required:true" style="width: 162px;"/>
								</c:if>
								<c:if test="${null == pds.MENU_NAME}">
									<input class="easyui-textbox" type="text" name="MENU_URL" id="menuUrl" value="#" readonly="readonly" prompt="" style="width: 162px;"/>
								</c:if>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">权限编码：</td>
						 	<td style="width: 180px;">
								<input class="easyui-textbox" type="text" name="MENU_AUTHORIZATION" id="MENU_AUTHORIZATION" value="${pd.MENU_AUTHORIZATION }"   data-options="" style="width: 162px;"/>
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">序号：</td>
						 	<td style="width: 180px;">
						 		<input class="easyui-numberbox" type="number" name="MENU_ORDER" id="menuOrder" style="width: 162px;" value="${pd.MENU_ORDER}" prompt="" title="请输入正整数" data-options="required:true,min:0,precision:0" />
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">状态：</td>
						 	<td style="width: 180px;">
								<input name="MENU_STATE_radio" type="radio"  id="MENU_STATE_radio" <c:if test="${pd.MENU_STATE == 1 or pd.MENU_ID ==null }">checked="checked"</c:if> onclick="setType(1);"/>显示
								&nbsp;&nbsp;<input name="MENU_STATE_radio" type="radio"  id="MENU_STATE_radio" <c:if test="${pd.MENU_STATE == 0 }">checked="checked"</c:if> onclick="setType(0);"/>隐藏
						 	</td>
						 </tr>
						 <tr style="height: 5px"/>
						 <tr>
						 	<td style="width: 90px;text-align: right;">菜单分类：</td>
						 	<td style="width: 180px;">
								<select id="MENU_TYPE" name="MENU_TYPE" class="easyui-combobox" limitToList="true" style="width: 162px;height: 26px;" data-options="required:true">
									<option value="1" <c:if test="${pd.MENU_TYPE == 1 or pd.MENU_ID ==null }">selected</c:if>>平台</option>
									<%-- <option value="2" <c:if test="${pd.MENU_TYPE == 2}">selected</c:if>>工位机</option> --%>
								</select>
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
	
	
	function setType(stateValue){
		$("#MENU_STATE").val(stateValue);
	}

	//保存
	 function save(){
		 top.showMsgProgress(); //显示进度条
		$("#menuForm").form("submit",{
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
