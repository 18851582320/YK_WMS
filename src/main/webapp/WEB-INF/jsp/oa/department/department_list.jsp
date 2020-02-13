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
	
	<!-- dataGrid -->
	<div class="main_tableDiv">
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-treegrid" id="dataInfoTable" data-options="fit:true" toolbar="#dataInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="dataInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.DEPT.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="dataInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="dataInfo-addChild"><i class="iconfont icon-tianjia"></i>&nbsp;增加子部门</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set>
		<shiro:hasPermission name="OA.DEPT.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.DEPT.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="dataInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission>
			&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-dark " id="dataInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
		<shiro:hasPermission name="OA.DEPT.IMPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="dataInfo-importExcel"  title="从EXCEL导入"><i class="iconfont icon-daoru"></i>&nbsp;导入</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.DEPT.EXPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="dataInfo-toExcel" title="导出到EXCEL"><i class="iconfont icon-daochu "></i>&nbsp;导出</a>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
		
	</div>
	 
</body>


<script type="text/javascript">

	var PARENT_ID=0;
	
	var queryConObj=null;
	
	$(function(){
		
		//动态设定datagrid的高度
		setDataGridHeight();
		window.onresize=function(){  
			setDataGridHeight();
		};
		
		
		//加载数据
		$("#dataInfoTable").treegrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 100,
	        rownumbers: true,
	        singleSelect:false,
	        idField:"DEPARTMENT_ID",
	        treeField:"NAME",
	        fitColumns: true,
	        lines: true,
	        pageList:[10,20,30,50,100,300],
	        url:'<%=basePath%>department/querylistPage.do',     
	        frozenColumns:[[  
	            {field:"DEPARTMENT_ID",checkbox:true},
	        ]],  
	        columns:[[  
				{title:"操作",field:"Operation",align:"center",width:100,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editdepartment('"+row.DEPARTMENT_ID+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>"
						}else{
							return "";
						}
					}	
				},
				
	        	{title:"部门名称",field:"NAME",align:"left",width:200,sortable:false},
	        	{title:"部门编码",field:"BIANMA",align:"center",width:150,sortable:false}, 
	        	{title:"英文名称",field:"NAME_EN",align:"center",width:150,sortable:false}, 
	        	{title:"上级编码",field:"parentBIANMA",align:"center",width:150,sortable:false}, 
	        	{title:"上级名称",field:"parentNAME",align:"center",width:150,sortable:false}, 
	        	{title:"备注",field:"BZ",align:"center",width:250,sortable:false}, 
	        ]],
	        onClickRow:function(rowData){
	         	$("#dataInfoTable").treegrid("unselectAll");
	         	$("#dataInfoTable").treegrid("select",rowData.DEPARTMENT_ID);
	        },
	        onLoadSuccess:function(data){
	        	 $("#dataInfoTable").treegrid("unselectAll");
	        	 <c:forEach items="${hideLst}" var="pd">
		         	var opts = $("#dataInfoTable").treegrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#dataInfoTable").treegrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	       		
		    }

	    });
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#dataInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="组织架构查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=oa_department&sysDataTableName=oa_department';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#dataInfoTable").treegrid("options").url = "<%=basePath%>department/querylistPage.do";
					 $("#dataInfoTable").treegrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-组织架构";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=oa_department&sysDataTableName=oa_department';
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
			 columnHideSetDiag.Title ="隐藏列设置-组织架构";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=oa_department&sysDataTableName=oa_department';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#dataInfo-reload").click(function(){
			$("#dataInfoTable").treegrid("unselectAll");
			$("#dataInfoTable").treegrid("reload");
		});
		
		//删除
		$("#dataInfo-remove").click(function(){
			var row=$("#dataInfoTable").treegrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var department_IDS="";
						$(row).each(function(i,item){
							var index=$("#dataInfoTable").treegrid("getRowIndex",item);
							if(department_IDS==""){
								department_IDS=department_IDS+item.DEPARTMENT_ID;
							}else{
								department_IDS=department_IDS+","+item.DEPARTMENT_ID;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>department/delete.do",
							type:"post",
							async:false,
							data:{department_IDS:department_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#dataInfoTable").treegrid("reload");
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
			 Diag.Title ="新增部门";
			 Diag.URL =  '<%=basePath%>department/toAdd.do?PARENT_ID='+PARENT_ID;
			 Diag.Width = 580;
			 Diag.Height = 250;
			 Diag.CancelEvent = function(){ //关闭事件
				 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#dataInfoTable").treegrid("reload");//重新加载数据
				 }
				 Diag.close();
			 };
			 Diag.show();
		});
		
		
		//新增
		$("#dataInfo-addChild").click(function(){
			 
			var rows=$("#dataInfoTable").datagrid("getSelections");
			if(rows.length>0){
				if(rows.length>1){
					$.messager.alert("系统提示","只能选择一条上级部门数据！","error");
				}else{
					 
					 var Diag = new top.Dialog();
					 Diag.Drag=true;
					 Diag.Title ="新增部门";
					 Diag.URL =  '<%=basePath%>department/toAdd.do?PARENT_ID='+rows[0].DEPARTMENT_ID;
					 Diag.Width = 580;
					 Diag.Height = 250;
					 Diag.CancelEvent = function(){ //关闭事件
						 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
						 {
							 $("#dataInfoTable").treegrid("reload");//重新加载数据
						 }
						 Diag.close();
					 };
					 Diag.show();
				}
			}else{
				$.messager.alert("系统提示","请选择一条上级部门数据！","error");
			}
			
		});
		
		
		//导入
		$("#dataInfo-importExcel").click(function(){
			var excelDiag = new top.Dialog();
			excelDiag.Drag=true;
			excelDiag.Title ="组织架构导入";
			excelDiag.URL = '<%=basePath%>file/toImportFile.do?controllerName=department&suffix=xls';  
			excelDiag.Width = 450;
			excelDiag.Height = 200
			excelDiag.CancelEvent = function(){ //关闭事件
				 if(excelDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#dataInfoTable").treegrid("reload");//重新加载数据
				 }
				 excelDiag.close();
			 };
			 excelDiag.show();
		});
		
		
		//导出
		$("#dataInfo-toExcel").click(function(){
			 var exUrl='<%=basePath%>department/excel.do?1=1';
			 if(queryConObj){
				 $.each(queryConObj,function(name,value) {
					 exUrl=exUrl+"&"+name+"="+value;
				 });
			 }
			 window.location.href=exUrl;
		});
		
		
	});
	
	//修改部门
	function editdepartment(DEPARTMENT_ID){
		 var Diag = new top.Dialog();
		 Diag.Drag=true;
		 Diag.Title ="部门编辑";
		 Diag.URL = '<%=basePath%>department/toEdit.do?DEPARTMENT_ID='+DEPARTMENT_ID;
		 Diag.Width = 580;
		 Diag.Height = 250;
		 Diag.CancelEvent = function(){ //关闭事件
			 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#dataInfoTable").treegrid("reload");//重新加载数据
			 }
			 Diag.close();
		 };
		 Diag.show();
	}
	
	
</script>

  
</html>
