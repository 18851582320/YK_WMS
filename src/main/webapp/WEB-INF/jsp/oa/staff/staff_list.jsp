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
			<table class="easyui-datagrid" id="staffInfoTable" data-options="fit:true" toolbar="#staffInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="staffInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.STAFF.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="staffInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.STAFF.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.STAFF.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="staffInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="staffInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="staffInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
		<shiro:hasPermission name="OA.STAFF.IMPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="staffInfo-importExcel"  title="从EXCEL导入"><i class="iconfont icon-daoru"></i>&nbsp;导入</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.STAFF.EXPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="staffInfo-toExcel" title="导出到EXCEL"><i class="iconfont icon-daochu "></i>&nbsp;导出</a>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
	</div>
	 
</body>


<script type="text/javascript">
	var queryConObj=null;

	$(function(){
		
		//动态设定datagrid的高度
		setDataGridHeight();
		window.onresize=function(){  
			setDataGridHeight();
		};
		
		
		//加载数据
		$("#staffInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
			sortName: "BIANMA",  
	        sortOrder: "desc",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns: true,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>staff/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"STAFF_ID",checkbox:true},
	        ]],  
	        columns:[[  
				{title:"操作",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editstaff('"+row.STAFF_ID+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
						}else{
							return "";
						}
					}	
				},
				{title:"员工编码",field:"BIANMA",align:"center",width:120,sortable:true}, 
	        	{title:"姓名",field:"NAME",align:"center",width:120}, 
	        	{title:"性别",field:"SEX",align:"center",width:100}, 
	        	{title:"部门",field:"DEPARTMENT_NAME",align:"center",width:120}, 
	        	{title:"直属上级",field:"higherUserName",align:"center",width:120}, 
	        	{title:"电话",field:"TEL",align:"center",width:120}, 
	        	{title:"Id卡号",field:"IdCard",align:"center",width:150}, 
	        	{title:"用户名",field:"USERNAME",align:"center",width:120},
	        	{title:"备注",field:"BZ",align:"center",width:150}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#staffInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#staffInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#staffInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#staffInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#staffInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="员工查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=oa_staff&sysDataTableName=oa_staff';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#staffInfoTable").datagrid("options").url = "<%=basePath%>staff/querylistPage.do";
					 $("#staffInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-员工";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=oa_staff&sysDataTableName=oa_staff';
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
			 columnHideSetDiag.Title ="隐藏列设置-员工";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=oa_staff&sysDataTableName=oa_staff';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		
		//刷新
		$("#staffInfo-reload").click(function(){
			$("#staffInfoTable").datagrid("unselectAll");
			$("#staffInfoTable").datagrid("reload");
		});
		
		//删除
		$("#staffInfo-remove").click(function(){
			var row=$("#staffInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var staff_IDS="";
						
						var checkTag=true;
						$(row).each(function(i,item){
							var index=$("#staffInfoTable").datagrid("getRowIndex",item);
							
							//管理员不允许删除
							if(item.BIANMA=="admin"){
								checkTag=false;
								return;
							}
							
							if(staff_IDS==""){
								staff_IDS=staff_IDS+item.STAFF_ID;
							}else{
								staff_IDS=staff_IDS+","+item.STAFF_ID;
							}
						});
						
						if(!checkTag){
							$.messager.alert("系统提示","不能删除admin员工！","error");
							return;
						}
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>staff/delete.do",
							type:"post",
							async:false,
							data:{staff_IDS:staff_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#staffInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"删除数据成功",
										timeout:3000,
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
		$("#staffInfo-add").click(function(){
			 var staffAddDiag = new top.Dialog();
			 staffAddDiag.Drag=true;
			 staffAddDiag.Title ="员工新增";
			 staffAddDiag.URL = '<%=basePath%>staff/toAdd.do';
			 staffAddDiag.Width = 600;
			 staffAddDiag.Height = 450;
			 staffAddDiag.CancelEvent = function(){ //关闭事件
				 if(staffAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#staffInfoTable").datagrid("reload");//重新加载数据
				 }
				 staffAddDiag.close();
			 };
			 staffAddDiag.show();
		});
		
		
		
		//导入
		$("#staffInfo-importExcel").click(function(){
			var staffExcelDiag = new top.Dialog();
			staffExcelDiag.Drag=true;
			staffExcelDiag.Title ="员工导入";
			staffExcelDiag.URL = '<%=basePath%>file/toImportFile.do?controllerName=staff&suffix=xls';  
			staffExcelDiag.Width = 450;
			staffExcelDiag.Height = 200
			staffExcelDiag.CancelEvent = function(){ //关闭事件
				 if(staffExcelDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#staffInfoTable").datagrid("reload");//重新加载数据
				 }
				 staffExcelDiag.close();
			 };
			 staffExcelDiag.show();
		});
		
		
		
		//导出
		$("#staffInfo-toExcel").click(function(){
			 var exUrl='<%=basePath%>staff/excel.do?1=1';
			 if(queryConObj){
				 $.each(queryConObj,function(name,value) {
					 exUrl=exUrl+"&"+name+"="+value;
				 });
			 }
			 window.location.href=exUrl;
		
		});
		
		
		
		
	});
	
	
	//修改
	function editstaff(STAFF_ID){
		 var staffEditDiag = new top.Dialog();
		 staffEditDiag.Drag=true;
		 staffEditDiag.Title ="员工编辑";
		 staffEditDiag.URL = '<%=basePath%>staff/toEdit.do?STAFF_ID='+STAFF_ID;
		 staffEditDiag.Width = 600;
		 staffEditDiag.Height = 450;
		 staffEditDiag.CancelEvent = function(){ //关闭事件
			 if(staffEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#staffInfoTable").datagrid("reload");//重新加载数据
			 }
			 staffEditDiag.close();
		 };
		 staffEditDiag.show();
	}
	
	
	
</script>

  
</html>
