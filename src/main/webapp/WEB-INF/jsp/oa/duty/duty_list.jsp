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
			<table class="easyui-datagrid" id="dutyInfoTable" data-options="fit:true" toolbar="#dutyInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="dutyInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.DUTY.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="dutyInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.DUTY.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.DUTY.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="dutyInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
			&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dutyInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="dutyInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
		<shiro:hasPermission name="OA.DUTY.IMPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="dutyInfo-importExcel"  title="从EXCEL导入"><i class="iconfont icon-daoru"></i>&nbsp;导入</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.DUTY.EXPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="dutyInfo-toExcel" title="导出到EXCEL"><i class="iconfont icon-daochu "></i>&nbsp;导出</a>
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
		$("#dutyInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>duty/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"dutyId",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editduty('"+row.dutyId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
						}else{
							return "";
						}
					}	
				},
				{title:"职务编码",field:"dutyCode",align:"center",width:110,sortable:true}, 
	        	{title:"职务名称",field:"dutyName",align:"center",width:130,sortable:true}, 	        	
	        	{title:"描述",field:"dutyMemo",align:"center",width:150,sortable:true}, 
	        	{title:"创建人员",field:"createUserName",align:"center",width:100,sortable:true}, 
	        	{title:"创建时间",field:"createTime",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}, 
	        	{title:"最后修改人",field:"updateUserName",align:"center",width:100,sortable:true}, 
	        	{title:"最后修改时间",field:"updateTime",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#dutyInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#dutyInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#dutyInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#dutyInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		//导出excel
		$("#dutyInfo-toExcel").click(function(){ 
			 var exUrl='<%=basePath%>duty/excel.do?1=1';
			 if(queryConObj){
				 $.each(queryConObj,function(name,value) {
					 exUrl=exUrl+"&"+name+"="+value;
				 });
			 }
			 window.location.href=exUrl;
		});

		//打开上传excel页面
		$("#dutyInfo-importExcel").click(function(){ 
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="职务导入";
			 diag.URL = '<%=basePath%>file/toImportFile.do?controllerName=duty&suffix=xls';  
			 diag.Width = 450;
			 diag.Height = 200;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#dutyInfoTable").datagrid("reload");//重新加载数据
				 }
				
				diag.close();
			 };
			 diag.show();
		});	

		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#dutyInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="职务查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=OA_duty&sysDataTableName=OA_duty';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#dutyInfoTable").datagrid("options").url = "<%=basePath%>duty/querylistPage.do";
					 $("#dutyInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-职务";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=OA_duty&sysDataTableName=OA_duty';
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
			 columnHideSetDiag.Title ="隐藏列设置-职务";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=OA_duty&sysDataTableName=OA_duty';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#dutyInfo-reload").click(function(){
			$("#dutyInfoTable").datagrid("unselectAll");
			$("#dutyInfoTable").datagrid("reload");
		});
		
		//删除
		$("#dutyInfo-remove").click(function(){
			var row=$("#dutyInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var dutyIdS="";
						$(row).each(function(i,item){
							var index=$("#dutyInfoTable").datagrid("getRowIndex",item);
							if(dutyIdS==""){
								dutyIdS=dutyIdS+item.dutyId;
							}else{
								dutyIdS=dutyIdS+","+item.dutyId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>duty/delete.do",
							type:"post",
							async:false,
							data:{dutyIdS:dutyIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#dutyInfoTable").datagrid("reload");
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
		
		
		
		
		//导出excel
		$("#dataInfo-toExcel").click(function(){ 
			 var exUrl='<%=basePath%>bugCreate/excel.do?1=1';
			 if(queryConObj){
				 $.each(queryConObj,function(name,value) {
					 exUrl=exUrl+"&"+name+"="+value;
				 });
			 }
			 window.location.href=exUrl;
		});
		
		//打开上传excel页面
		$("#dataInfo-importExcel").click(function(){ 
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="bug创建导入";
			 diag.URL = '<%=basePath%>file/toImportFile.do?controllerName=bugCreate&suffix=xls';  
			 diag.Width = 450;
			 diag.Height = 200;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#dataInfoTable").datagrid("reload");//重新加载数据
				 }
				
				diag.close();
			 };
			 diag.show();
		});	
		
		
		
		
		
		
		//新增
		$("#dutyInfo-add").click(function(){
			 var dutyAddDiag = new top.Dialog();
			 dutyAddDiag.Drag=true;
			 dutyAddDiag.Title ="职务新增";
			 dutyAddDiag.URL = '<%=basePath%>duty/toAdd.do';
			 dutyAddDiag.Width = 550;
			 dutyAddDiag.Height = 270;
			 dutyAddDiag.CancelEvent = function(){ //关闭事件
				 if(dutyAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#dutyInfoTable").datagrid("reload");//重新加载数据
				 }
				 dutyAddDiag.close();
			 };
			 dutyAddDiag.show();
		});
		
	});
	
	
	//修改
	function editduty(dutyId){
		 var dutyEditDiag = new top.Dialog();
		 dutyEditDiag.Drag=true;
		 dutyEditDiag.Title ="职务编辑";
		 dutyEditDiag.URL = '<%=basePath%>duty/toEdit.do?dutyId='+dutyId;
		 dutyEditDiag.Width = 550;
		 dutyEditDiag.Height = 270;
		 dutyEditDiag.CancelEvent = function(){ //关闭事件
			 if(dutyEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#dutyInfoTable").datagrid("reload");//重新加载数据
			 }
			 dutyEditDiag.close();
		 };
		 dutyEditDiag.show();
	}
	
	
	
</script>

  
</html>
