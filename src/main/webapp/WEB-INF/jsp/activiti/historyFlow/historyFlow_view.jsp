<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<div id="saveEditCenter" class="saveEditCenter" style="height: 598px;">
			<div id="tt" class="easyui-tabs" fit="true">
				
				<!-- tab1 -->
				<div title="审批事项" id="tab1Div" style="display:none;">
					<div style="height: 550px;overflow: auto;margin: 5px;border: 1px dotted red;">
						<table id="table1" style="width: 100%;">
				        	<tr>
				        		<td colspan="2" style="text-align: center;">表单如下：</td>
				        	</tr>
				        	<tr>
				        		<td colspan="2" style="text-align: center;height: 1000px;">
				        			<iframe src="${formUrl}" style="width: 100%;height: 100%;"></iframe>
				        		</td>
				        	</tr>
				        </table>
					</div>
					<div style="height: 5px;">
					</div>
				</div>
				
				
				<!-- tab2 -->
				<div title="审批过程" data-options="closable:false" style="padding:20px;overflow:auto;display:none;">
					<table id="table2" class="easyui-datagrid">
					    <thead>
							<tr>
								<th data-options="field:'ACT_NAME_',width:150,align:'center'">任务节点</th>
								<th data-options="field:'ASSIGNEE_',width:100,align:'center'">办理人</th>
								<th data-options="field:'START_TIME_',width:150,align:'center'">审批开始时间</th>
								<th data-options="field:'END_TIME_',width:150,align:'center'">审批结束时间</th>
								<th data-options="field:'ZTIME',width:100,align:'center'">用时</th>
								<th data-options="field:'TEXT_',width:240,align:'center'">审批意见</th>
							</tr>
					    </thead>
					    <tbody>
					    	<c:forEach items="${hitaskList}" var="var" varStatus="vs">
					    		<tr>
					    			<td>${var.ACT_NAME_}</td>
					    			<td>${var.ASSIGNEE_}</td>
					    			<td>${fn:substring(var.START_TIME_ ,0,19)}</td>
					    			<td>
					    				<c:if test="${var.END_TIME_ == NULL}">正在审批……</c:if>
										<c:if test="${var.END_TIME_ != NULL}">${fn:substring(var.END_TIME_ ,0,19)}</c:if>
					    			</td>
					    			<td>${var.ZTIME}</td>
					    			<td>
					    				${fn:replace(var.MESSAGE_,',YK_WMS,',':')}
					    			</td>
					    		</tr>
					    	</c:forEach>
						</tbody>
					</table>
				</div>
				
				<!-- tab3 -->
				<div title="流程图" data-options="closable:false" style="padding:20px;display:none;">   
			        <table id="table3">
			        	<tr>
			        		<td  style="text-align: center;"><img alt="${pd.FILENAME }" src="${pd.imgSrc }"></td>
			        	</tr>
			        </table>     
			    </div>
				
			</div>
		</div>
		<div class="addEditButtonDiv">
			<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
		</div>
	</div>
</body>

<script type="text/javascript">

	$(function(){
		dealTableT();
		window.onresize=function(){  
			dealTableT();
		};
	});
	
	
	function dealTableT(){
		
		var h=$(window).height()-4;
		var marginTop=parseInt($(".addEditDiv").css("margin-top"));
		var marginBottom=parseInt($(".addEditDiv").css("margin-bottom"));
		var addEditButtonDivHeight=parseInt($(".addEditButtonDiv").height())+6;
		$("#saveEditCenter").css("height",(h-marginTop-marginBottom-addEditButtonDivHeight)+"px");
		
	}
	
</script>


</html>
