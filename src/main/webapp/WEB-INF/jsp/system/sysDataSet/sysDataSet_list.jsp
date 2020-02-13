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
	<div>
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="dataInfoTable" data-options="fit:true" toolbar="#dataInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="dataInfo_bar" class="dataGridToolBar">
		&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="dataInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="dataInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark " id="dataInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
	</div>
	 
</body>


<script type="text/javascript">
	
	var dataSetTypeCode="${pd.dataSetTypeCode}";//默认层级为1(系统系统数据最多为2级)

	//动态设定datagrid的高度
	function menuDataGridT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginLeft=parent.getMarginLeft();
		$("#dataGridDiv").css("height",h+"px");
		$("#dataGridDiv").css("width",(w-marginLeft)+"px");
	}

	$(function(){
		
		//动态设定datagrid的高度
		menuDataGridT();
		window.onresize=function(){  
			menuDataGridT();
		}; 
		
		//加载数据
		$("#dataInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true, 
	        pagination: true,
	        pageSize: 20,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>sysDataSet/querylistPage.do",     
	        queryParams:{
	        	dataSetTypeCode:dataSetTypeCode
	        },
	        frozenColumns:[[  
	            {field:"dataSetId",checkbox:true},
	        ]],  
	        columns:[[  
				{title:"操作",field:"Operation",align:"center",width:140,
					formatter:function(value,row){
						return "<a  href=\"javascript:edit('"+row.dataSetId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
					}	
				},
	        	{title:"值",field:"dataSetValue",align:"center",width:250}, 
	        	{title:"备注",field:"dataSetMemo",align:"center",width:250}, 
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
			 queryDiag.Title ="系统数据查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=SYS_dataSet&sysDataTableName=SYS_dataSet';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj["dataSetTypeCode"]=dataSetTypeCode;
					 
					 $("#dataInfoTable").datagrid("options").url = "<%=basePath%>sysDataSet/querylistPage.do";
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
			 querySetDiag.Title ="查询条件设置-系统数据";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=SYS_dataSet&sysDataTableName=SYS_dataSet';
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
			 columnHideSetDiag.Title ="隐藏列设置-系统数据";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=SYS_dataSet&sysDataTableName=SYS_dataSet';
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
		
		//删除
		$("#dataInfo-remove").click(function(){
			var row=$("#dataInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var keyIdS="";
						$(row).each(function(i,item){
							if(keyIdS==""){
								keyIdS=keyIdS+item.dataSetId;
							}else{
								keyIdS=keyIdS+","+item.dataSetId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>sysDataSet/deleteAll.do",
							type:"post",
							async:false,
							data:{keyIdS:keyIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#dataInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"删除数据成功",
										timeout:2000,
										showType:"slide"
									});
								}else if(data.result=="canNotDelete"){
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","所选数据中有数据被引用，不能删除！","error");
								}else{
									top.closeMsgProgress();//关闭进度条
									$.messager.alert("系统提示","删除出错:"+data.errmsg,"error");
								}
							}
						});
						
					}
				});
			}else{
				$.messager.alert("系统提示","请选择要删除的数据","error");
			}
		});
		
		
		//新增
		$("#dataInfo-add").click(function(){
			
			 var Diag = new top.Dialog();
			 Diag.Drag=true;
			 Diag.Title ="新增系统数据";
			 Diag.URL =  '<%=basePath%>sysDataSet/toAdd.do?dataSetTypeCode='+dataSetTypeCode;
			 Diag.Width = 530;
			 Diag.Height = 300;
			 Diag.CancelEvent = function(){ //关闭事件
				 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#dataInfoTable").datagrid("reload");//重新加载数据
				 }
				 Diag.close();
			 };
			 Diag.show();
		});
		
		
	});
	
	
	
	//修改系统数据
	function edit(dataSetId){
		 var Diag = new top.Dialog();
		 Diag.Drag=true;
		 Diag.Title ="系统数据编辑";
		 Diag.URL = '<%=basePath%>sysDataSet/toEdit.do?dataSetId='+dataSetId;
		 Diag.Width = 530;
		 Diag.Height = 300;
		 Diag.CancelEvent = function(){ //关闭事件
			 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#dataInfoTable").datagrid("reload");//重新加载数据
			 }
			 Diag.close();
		 };
		 Diag.show();
	}
	
	
	
</script>

  
</html>
