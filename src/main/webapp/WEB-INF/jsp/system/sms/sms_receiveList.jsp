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
<body>
	<!-- dataGrid -->
	<div class="main_tableDiv">
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="dataInfoTable" data-options="fit:true" toolbar="#dataInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="dataInfo_bar" class="dataGridToolBar">
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="dataInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
		&nbsp;&nbsp;是否阅读:&nbsp;<select id="stateSelect" name="stateSelect" editable="false" class="easyui-combobox" limitToList="true" value="" style="width:100px; height:26px;">
								<option value="1">否</option>
								<option value="2">是</option>
							</select>
	</div>
	 
</body>


<script type="text/javascript">
	var queryConObj=null;
	var stateSelect="1"
	
	queryConObj={};
	queryConObj["isRead"]="false";

	$(function(){
		
		//动态设定datagrid的高度
		setDataGridHeight();
		window.onresize=function(){  
			setDataGridHeight();
		};
		
		//加载数据
		$("#dataInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        sortName:"sendTime",
	        sortOrder:"desc",
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>sms/queryReceivelistPage.do",     
	        frozenColumns:[[  
	            {field:"smsId",checkbox:true},
	        ]],  
	        queryParams:{
	        	isRead:false
	        },
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						return "<a  href=\"javascript:detail('"+row.type+"','"+row.keyValue+"','"+row.smsId+"');\" class=\"btn btn-mini btn-info\" title=\"查看\"><i class=\"iconfont icon-bianji\"></i>&nbsp;查看</a>";
					}	
				},
				{title:"发送人",field:"fromUserName",align:"center",width:150,sortable:true}, 
	        	{title:"标题",field:"title",align:"center",width:250,sortable:true}, 	        	
	        	{title:"接收人",field:"toUserName",align:"center",width:100,sortable:true}, 
	        	{title:"发送时间",field:"sendTime",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	},
	        	{title:"是否阅读",field:"isRead",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			if(value=='true'){
	        				return "是";
	        			}else{
	        				return "否";
	        			}
	        		},
	        		styler: function(value,row,index){
   	        			if(value=="true"){
   	        				return "background-color:#1da02b;color:#fff;";
   	        			}
   	        		}
	        	}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#dataInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#dataInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#dataInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#dataInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });


		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#dataInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="消息查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=SYS_sms&sysDataTableName=SYS_sms';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					if(stateSelect==1){
						queryConObj["isRead"]="false";
					}else{
						queryConObj["isRead"]="true";
					}
					 
					 $("#dataInfoTable").datagrid("options").url = "<%=basePath%>sms/queryReceivelistPage.do";
					 $("#dataInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-消息";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=SYS_sms&sysDataTableName=SYS_sms';
			 querySetDiag.Width = 800;
			 querySetDiag.Height = 500;
			 querySetDiag.CancelEvent = function(){ //关闭事件
				 querySetDiag.close();
			 };
			 querySetDiag.show();
		});
		
		//隐藏设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#columnHideSet").click(function(){
			 var columnHideSetDiag = new top.Dialog();
			 columnHideSetDiag.Drag=true;
			 columnHideSetDiag.Title ="隐藏列设置-消息";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=SYS_sms&sysDataTableName=SYS_sms';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#dataInfo-reload").click(function(){
			$("#dataInfoTable").datagrid("unselectAll");
			$("#dataInfoTable").datagrid("reload");
		});
		
		$("#stateSelect").combobox({
			onChange:function(newValue,oldValue){
				queryConObj={};
				stateSelect=newValue;
				if(stateSelect==1){
					queryConObj["isRead"]="false";
				}else{
					queryConObj["isRead"]="1";
				}
				$("#dataInfoTable").datagrid("load",queryConObj);
			}
		});
		
	});
	
	
	function detail(type,keyValue,smsId){
		 
		var url="";		
		var title="";
		var w=0;
		var h=0;
		 if(type=="sms"){
			 url='<%=basePath%>sms/toDetailAndEdit.do?smsId='+smsId;
			 title="消息";
			 w=1000;
			 h=500;
		 }else if(type=="task"){
			 url='<%=basePath%>tkTask/toReceiveOrNo.do?taskId='+keyValue+'&smsId='+smsId;
			 title="任务";
			 w=740;
			 h=600;
		 }else if(type=="taskBack"){
			 url='<%=basePath%>tkTask/toTaskBack.do?taskId='+keyValue+'&smsId='+smsId;
			 title="任务退回";
			 w=740;
			 h=600;
		 }else if(type=="taskNoCheck"){
			 url='<%=basePath%>tkTask/toTaskNoCheck.do?taskId='+keyValue+'&smsId='+smsId;
			 title="任务不验收";
			 w=740;
			 h=600;
		 }else if(type=="taskCheck"){
			 url='<%=basePath%>tkTask/toCheckFromSms.do?taskId='+keyValue+'&smsId='+smsId;
			 title="任务验收";
			 w=740;
			 h=600;
		 }else if(type=="saleLeads"){
			 url='<%=basePath%>saleLeads/detail.do?saleLeadsId='+keyValue+'&smsId='+smsId;
			 title="销售线索";
			 w=740;
			 h=500;
		 }else if(type=="BXSP"){
			 url='<%=basePath%>subExpense/toShenHe.do?subExpenseId='+keyValue+'&smsId='+smsId;
			 title="销售线索";
			 w=1050;
			 h=620;
		 }
		 
		 var Diag = new top.Dialog();
		 Diag.Drag=true;
		 Diag.Title =title;
		 Diag.URL = url;
		 Diag.Width = w;
		 Diag.Height = h;
		 Diag.CancelEvent = function(){ //关闭事件
			 $("#dataInfoTable").datagrid("reload");//重新加载数据
			 top.getRemindInfo();
			 top.refreshHome();
			 Diag.close();
		 };
		 Diag.show(); 
	}
	
	
</script>

  
</html>
