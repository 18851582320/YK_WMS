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
<body>
	<!-- dataGrid -->
	<div class="main_tableDiv">
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="logInfoTable" data-options="fit:true" toolbar="#logInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="logInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="SYS.LOG.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="logInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="logInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="logInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
	</div>
	 
</body>


<script type="text/javascript">

	$(function(){
		
		//动态设定datagrid的高度
		setDataGridHeight();
		window.onresize=function(){  
			setDataGridHeight();
		};
		
		
		//加载数据
		$("#logInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        sortName: "CZTIME",  
	        sortOrder: "desc",
	        multiSort:true,
	        remoteSort: true,  
	        pagination: true,
	        pageSize: 20,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>log/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"LOG_ID",checkbox:true},
	        ]],  
	        columns:[[  
	        	{title:"用户名",field:"USERNAME",align:"center",width:150,sortable:true}, 
	        	{title:"姓名",field:"NAME",align:"center",width:150,sortable:true}, 
	        	{title:"事件",field:"CONTENT",align:"center",width:400,sortable:true}, 
	        	{title:"操作时间",field:"CZTIME",align:"center",width:140,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}, 
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#logInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#logInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#userInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#userInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#logInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="日志查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=SYS_LOG&sysDataTableName=SYS_LOG';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#logInfoTable").datagrid("options").url = "<%=basePath%>log/querylistPage.do";
					 $("#logInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-日志";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=SYS_LOG&sysDataTableName=SYS_LOG';
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
			 columnHideSetDiag.Title ="隐藏列设置-日志";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=SYS_LOG&sysDataTableName=SYS_LOG';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#logInfo-reload").click(function(){
			$("#logInfoTable").datagrid("unselectAll");
			$("#logInfoTable").datagrid("reload");
		});
		
		//删除
		$("#logInfo-remove").click(function(){
			var row=$("#logInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var LOG_IDS="";
						$(row).each(function(i,item){
							var index=$("#logInfoTable").datagrid("getRowIndex",item);
							$("#logInfoTable").datagrid("deleteRow",index);
							if(LOG_IDS==""){
								LOG_IDS=LOG_IDS+item.LOG_ID;
							}else{
								LOG_IDS=LOG_IDS+","+item.LOG_ID;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>log/delete.do",
							type:"post",
							async:false,
							data:{LOG_IDS:LOG_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#logInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"删除数据成功",
										timeout:2000,
										showType:"slide"
									});
								}else if(data.result=="canNotDelete"){
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","所选数据中有数据被人员信息所引用，不能删除！","error");
								}else{
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","删除出错！","error");
								}
							}
						});
						
					}
				});
			}else{
				$.messager.alert("系统提示","请选择要删除的数据","error");
			}
		});
		
		
	});
	
	
	
</script>

  
</html>
