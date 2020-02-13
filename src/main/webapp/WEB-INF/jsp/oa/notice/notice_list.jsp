<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
			<table class="easyui-datagrid" id="noticeInfoTable" data-options="fit:true" toolbar="#noticeInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="noticeInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.NOTICE.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="noticeInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;<spring:message code="common_add" /></a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.NOTICE.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.NOTICE.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="noticeInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;<spring:message code="common_delete" /></a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="noticeInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;<spring:message code="common_reload" /></a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="noticeInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;<spring:message code="common_query" /></a>			
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;<spring:message code="common_queryConditionSet" /></a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;<spring:message code="common_columnHideSet" /></a>
	</div>
	 
</body>


<script type="text/javascript">
	var queryConObj=null;
	var language="${sessionScope.language}";
	
	
	var notice_="${notice_}";
	var common_add="${common_add}";
	var common_edit="${common_edit}";
	var common_import="${common_import}";
	var common_export="${common_export}";
	var common_query="${common_query}";
	var common_delete="${common_delete}";
	var common_confirm="${common_confirm}";
	var common_queryConditionSet="${common_queryConditionSet}";
	var common_columnHideSet="${common_columnHideSet}";
	var common_alert="${common_alert}";
	var common_alertDelete="${common_alertDelete}";
	var common_alertRecordNum="${common_alertRecordNum}";
	var common_alertDeleteOk="${common_alertDeleteOk}";
	var common_alertDeleteError="${common_alertDeleteError}";
	var common_alertDeleteErrorGuanLian="${common_alertDeleteErrorGuanLian}";
	var common_alertDeleteNoSelect="${common_alertDeleteNoSelect}";
	var common_loadMsg="${common_loadMsg}";
	

	$(function(){
		
		//动态设定datagrid的高度
		setDataGridHeight();
		window.onresize=function(){  
			setDataGridHeight();
		};
		
		//加载数据
		$("#noticeInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:common_loadMsg,
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>notice/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"noticeId",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"${common_Operation}",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editnotice('"+row.noticeId+"');\" class=\"btn btn-mini btn-info\" title=\"\"><i class=\"iconfont icon-bianji\"></i>&nbsp;<spring:message code='common_edit' /></a>";
						}else{
							return "";
						}
					}	
				},
				{title:"${notice_noticeTitle}",field:"noticeTitle",align:"center",width:150,sortable:true}, 
	        	{title:"${notice_noticeContent}",field:"noticeContent",align:"center",width:250,sortable:true}, 	        	
	        	{title:"${common_createUser}",field:"createUserName",align:"center",width:100,sortable:true}, 
	        	{title:"${common_createTime}",field:"createTime",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}, 
	        	{title:"${notice_endTime}",field:"endTime",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	},
	        	{title:"${notice_isStop}",field:"isStop",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			if(value==1){
	        				return "是";
	        			}else{
	        				return "否";
	        			}
	        		}	
	        	}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#noticeInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#noticeInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#noticeInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#noticeInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		//导出excel
		$("#noticeInfo-toExcel").click(function(){ 
			 var exUrl='<%=basePath%>notice/excel.do?1=1';
			 if(queryConObj){
				 $.each(queryConObj,function(name,value) {
					 exUrl=exUrl+"&"+name+"="+value;
				 });
			 }
			 window.location.href=exUrl;
		});

		//打开上传excel页面
		$("#noticeInfo-importExcel").click(function(){ 
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title =notice_+common_import;
			 diag.URL = '<%=basePath%>file/toImportFile.do?controllerName=notice&suffix=xls&language='+language;  
			 diag.Width = 450;
			 diag.Height = 200;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#noticeInfoTable").datagrid("reload");//重新加载数据
				 }
				
				diag.close();
			 };
			 diag.show();
		});	

		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#noticeInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title =notice_+common_query;
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=OA_companyNotice&sysDataTableName=OA_companyNotice&language='+language;
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#noticeInfoTable").datagrid("options").url = "<%=basePath%>notice/querylistPage.do";
					 $("#noticeInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title =notice_+common_queryConditionSet;
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=OA_companyNotice&sysDataTableName=OA_companyNotice&language='+language;
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
			 columnHideSetDiag.Title =notice_+common_columnHideSet;
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=OA_companyNotice&sysDataTableName=OA_companyNotice&language'+language;
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#noticeInfo-reload").click(function(){
			$("#noticeInfoTable").datagrid("unselectAll");
			$("#noticeInfoTable").datagrid("reload");
		});
		
		//删除
		$("#noticeInfo-remove").click(function(){
			var row=$("#noticeInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm(common_alert,common_alertDelete+row.length+common_alertRecordNum,function(flag){
					if(flag){
						var noticeIdS="";
						$(row).each(function(i,item){
							var index=$("#noticeInfoTable").datagrid("getRowIndex",item);
							if(noticeIdS==""){
								noticeIdS=noticeIdS+item.noticeId;
							}else{
								noticeIdS=noticeIdS+","+item.noticeId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>notice/delete.do",
							type:"post",
							async:false,
							data:{noticeIdS:noticeIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#noticeInfoTable").datagrid("reload");
									$.messager.show({
										title:common_alert,
										msg:common_alertDeleteOk,
										timeout:3000,
										showType:"slide"
									});
								}else if(data.result=="canNotDelete"){
									top.closeMsgProgress();//关闭进度条
									$.messager.alert(common_alert,common_alertDeleteErrorGuanLian,"error");
								}else{
									top.closeMsgProgress();//关闭进度条
									$.messager.alert(common_alert,common_alertDeleteError+data.errmsg,"error");
								}
							}
						});
						
					}
				});
			}else{
				$.messager.alert(common_alert,common_alertDeleteNoSelect,"error");
			}
		});
		
		
		//新增
		$("#noticeInfo-add").click(function(){
			 var noticeAddDiag = new top.Dialog();
			 noticeAddDiag.Drag=true;
			 noticeAddDiag.Title =notice_+common_add;
			 noticeAddDiag.URL = '<%=basePath%>notice/toAdd.do?language='+language;
			 noticeAddDiag.Width = 1000;
			 noticeAddDiag.Height = 500;
			 noticeAddDiag.CancelEvent = function(){ //关闭事件
				 if(noticeAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#noticeInfoTable").datagrid("reload");//重新加载数据
				 }
				 noticeAddDiag.close();
			 };
			 noticeAddDiag.show();
		});
		
	});
	
	
	//修改
	function editnotice(noticeId){
		 var noticeEditDiag = new top.Dialog();
		 noticeEditDiag.Drag=true;
		 noticeEditDiag.Title =notice_+common_edit;
		 noticeEditDiag.URL = '<%=basePath%>notice/toEdit.do?noticeId='+noticeId+'&language='+language;
		 noticeEditDiag.Width = 1000;
		 noticeEditDiag.Height = 500;
		 noticeEditDiag.CancelEvent = function(){ //关闭事件
			 if(noticeEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#noticeInfoTable").datagrid("reload");//重新加载数据
			 }
			 noticeEditDiag.close();
		 };
		 noticeEditDiag.show();
	}
	
	
	
</script>

  
</html>
