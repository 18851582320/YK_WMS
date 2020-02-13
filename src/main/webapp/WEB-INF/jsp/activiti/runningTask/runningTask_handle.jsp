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
					<form action="runningTask/handle.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ID_" id="ID_" value="${pd.ID_}"/>
						<input type="hidden" name="ASSIGNEE_" id="ASSIGNEE_" value=""/>
						<input type="hidden" name="PROC_INST_ID_" id="PROC_INST_ID_" value="${pd.PROC_INST_ID_}"/>
						<input type="hidden" name="msg" id="msg" value="yes"/>
						<div style="height: 500px;overflow: auto;margin: 5px;border: 1px dotted red;">
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
						<div style="height: 55px;">
							<table>
					        	<tr>
					        		<td style="text-align: center;width: 100px;">
					        			审批意见：
					        		</td>
					        		<td style="text-align: center;">
					        			<input class="easyui-textbox" type="text" id="OPINION" name="OPINION" value="${pd.OPINION}" style="height: 50px;width: 860px;" prompt="" multiline="true" data-options="required:true"  title="描述">
					        		</td>
					        	</tr>
					        </table>
						</div>
					</form>
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
			<a class="btn btn-mini btn-success" onclick="handle('yes');"><i class="iconfont icon-baocun8"></i>&nbsp;批准</a>
			&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-primary" onclick="handle('no');"><i class="iconfont icon-fanhui6"></i>&nbsp;驳回</a>
			&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
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
	
	//保存
	function handle(msg){
		top.showMsgProgress(); //显示进度条
		$("#msg").val(msg);
		$("#Form").form("submit",{
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
					
					var nextAssignee=data.nextAssignee;
					if(nextAssignee){//给下个待办对象发送消息
						top.sendTaskMsg(nextAssignee);
					}
					
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
