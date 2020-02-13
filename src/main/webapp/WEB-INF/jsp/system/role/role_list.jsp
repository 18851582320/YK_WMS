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
	
	<style type="text/css">
		.datagrid-cell-c2-menuqx{
			margin: 0;
			padding: 0 1px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 28px;
			line-height: 28px;
			font-size: 12px;
		}
		.datagrid-cell-c2-chakanqx{
			margin: 0;
			padding: 0 1px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 28px;
			line-height: 28px;
			font-size: 12px;
		}
		.datagrid-cell-c2-addqx{
			margin: 0;
			padding: 0 1px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 28px;
			line-height: 28px;
			font-size: 12px;
		}
		.datagrid-cell-c2-editqx{
			margin: 0;
			padding: 0 1px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 28px;
			line-height: 28px;
			font-size: 12px;
		}
		.datagrid-cell-c2-deleteqx{
			margin: 0;
			padding: 0 1px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 28px;
			line-height: 28px;
			font-size: 12px;
		}
		.datagrid-cell-c2-importqx{
			margin: 0;
			padding: 0 1px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 28px;
			line-height: 28px;
			font-size: 12px;
		}
		.datagrid-cell-c2-exportqx{
			margin: 0;
			padding: 0 1px;
			white-space: nowrap;
			word-wrap: normal;
			overflow: hidden;
			height: 28px;
			line-height: 28px;
			font-size: 12px;
		}
	</style>
	
</head>
<body>
	<!-- dataGrid -->
	<div class="main_tableDiv">
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="roleInfoTable" data-options="fit:true" toolbar="#roleInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="roleInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="SYS.ROLE.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="roleInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set>
		<shiro:hasPermission name="SYS.ROLE.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="SYS.ROLE.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="roleInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="roleInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="roleInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
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
		$("#roleInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 100,
	        rownumbers: true,
	        fitColumns: true,
	        pageList:[10,20,30,50,100,300,500,1000],
	        url:"<%=basePath%>role/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"ROLE_ID",checkbox:true},
	        ]],  
	        columns:[[
				{title:"操作",field:"Operation",align:"center",width:100,
					formatter:function(value,row){
						return "<a  href=\"javascript:editRole('"+row.ROLE_ID+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
					}	
				},
	        	{title:"角色 编码",field:"RNUMBER",align:"center",width:120,sortable:true}, 
	        	{title:"角色 名称",field:"ROLE_NAME",align:"center",width:300,sortable:true}, 
	        	{title:"菜单权限",field:"menuqx",align:"center",width:120,
	        		formatter:function(value,row){
	        			return"<a href=\"javascript:editRights('"+row.ROLE_ID+"');\" class=\"btn btn-mini btn-purple\"><i class=\"iconfont icon-quanxian21\"></i>&nbsp;菜单权限</a>"; 
	        		}	
	        	},
	        	{title:"增加",field:"addqx",align:"center",width:80,
	        		formatter:function(value,row){
	        			return"<a  href=\"javascript:roleButton('"+row.ROLE_ID+"','add_qx')\" class=\"btn btn-warning btn-mini\" title=\"分配新增权限\"><i class=\"iconfont icon-quanxian\"></i></a>"; 
	        		}		
	        	},
	        	{title:"修改",field:"editqx",align:"center",width:80,
	        		formatter:function(value,row){
	        			return"<a href=\"javascript:roleButton('"+row.ROLE_ID+"','edit_qx')\" class=\"btn btn-warning btn-mini\" title=\"分配修改权限\"><i class=\"iconfont icon-quanxian\"></i></a>"; 
	        		}	
	        	}, 
	        	{title:"删除",field:"deleteqx",align:"center",width:80,
	        		formatter:function(value,row){
	        			return"<a href=\"javascript:roleButton('"+row.ROLE_ID+"','del_qx')\" class=\"btn btn-warning btn-mini\" title=\"分配删除权限\"><i class=\"iconfont icon-quanxian\"></i></a>"; 
	        		}	
	        	}, 
	        	{title:"导入",field:"importqx",align:"center",width:80,
	        		formatter:function(value,row){
	        			return"<a href=\"javascript:roleButton('"+row.ROLE_ID+"','import_qx')\" class=\"btn btn-warning btn-mini\" title=\"分配导入权限\"><i class=\"iconfont icon-quanxian\"></i></a>"; 
	        		}	
	        	},
	        	{title:"导出",field:"exportqx",align:"center",width:80,
	        		formatter:function(value,row){
	        			return"<a href=\"javascript:roleButton('"+row.ROLE_ID+"','export_qx')\" class=\"btn btn-warning btn-mini\" title=\"分配导出权限\"><i class=\"iconfont icon-quanxian\"></i></a>"; 
	        		}	
	        	}
	        	
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#roleInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#roleInfoTable").datagrid("selectRow",rowIndex);
	        },
	        onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#roleInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#roleInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	        }

	    });
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#roleInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="角色查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=sys_role&sysDataTableName=sys_role';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 $("#roleInfoTable").datagrid("options").url = "<%=basePath%>role/querylistPage.do";
					 $("#roleInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-角色";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=sys_role&sysDataTableName=sys_role';
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
			 columnHideSetDiag.Title ="隐藏列设置-用户";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=sys_role&sysDataTableName=sys_role';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#roleInfo-reload").click(function(){
			$("#roleInfoTable").datagrid("unselectAll");
			$("#roleInfoTable").datagrid("reload");
		});
		
		//删除
		$("#roleInfo-remove").click(function(){
			var row=$("#roleInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var ROLE_IDS="";
						$(row).each(function(i,item){
							var index=$("#roleInfoTable").datagrid("getRowIndex",item);
							//$("#roleInfoTable").datagrid("deleteRow",index);
							if(ROLE_IDS==""){
								ROLE_IDS=ROLE_IDS+item.ROLE_ID;
							}else{
								ROLE_IDS=ROLE_IDS+","+item.ROLE_ID;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>role/delete.do",
							type:"post",
							async:false,
							data:{ROLE_IDS:ROLE_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#roleInfoTable").datagrid("reload");
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
		$("#roleInfo-add").click(function(){
			 var roleAddDiag = new top.Dialog();
			 roleAddDiag.Drag=true;
			 roleAddDiag.Title ="角色新增";
			 roleAddDiag.URL = '<%=basePath%>role/toAdd.do';
			 roleAddDiag.Width = 400;
			 roleAddDiag.Height = 140;
			 roleAddDiag.CancelEvent = function(){ //关闭事件
				 if(roleAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#roleInfoTable").datagrid("reload");//重新加载数据
				 }
				 roleAddDiag.close();
			 };
			 roleAddDiag.show();
		});
		
	});
	
	
	//设置按钮权限
	function roleButton(ROLE_ID,QxType){
		var Title="";
		if(QxType=="add_qx"){//增加按钮权限
			Title = "授权新增权限";
		}else if(QxType=="del_qx"){//删除按钮权限
			Title = "授权删除权限";
		}else if(QxType=="edit_qx"){
			Title="授权修改权限";
		}else if(QxType=="cha_qx"){
			Title = "授权查看权限";
		}else if(QxType=="import_qx"){
			Title = "授权导入权限";
		}else if(QxType=="export_qx"){
			Title = "授权导出权限";
		}
		
		var url="<%=basePath%>role/roleButtonRight.do?";
		var paramStr="ROLE_ID="+ROLE_ID+"&QxType="+QxType;
		url=url+paramStr;
		
		 var rbDiag = new top.Dialog();
		 rbDiag.Drag = true;
		 rbDiag.Title = Title;
		 rbDiag.URL = url;
		 rbDiag.Width = 330;
		 rbDiag.Height = 450;
		 rbDiag.CancelEvent = function(){ //关闭事件
			 rbDiag.close();
		 };
		 rbDiag.show();
		
	}
	
	//设置菜单权限
	function editRights(ROLE_ID){
		 var menuRiDiag = new top.Dialog();
		 menuRiDiag.Drag = true;
		 menuRiDiag.Title = "菜单权限";
		 menuRiDiag.URL = '<%=basePath%>role/menuqx.do?ROLE_ID='+ROLE_ID;
		 menuRiDiag.Width = 320;
		 menuRiDiag.Height = 450;
		 menuRiDiag.CancelEvent = function(){ //关闭事件
			 menuRiDiag.close();
		 };
		 menuRiDiag.show();
	}
	
	//修改角色
	function editRole(ROLE_ID){
		 var roleEditDiag = new top.Dialog();
		 roleEditDiag.Drag=true;
		 roleEditDiag.Title ="角色编辑";
		 roleEditDiag.URL = '<%=basePath%>role/toEdit.do?ROLE_ID='+ROLE_ID;
		 roleEditDiag.Width = 400;
		 roleEditDiag.Height = 140;
		 roleEditDiag.CancelEvent = function(){ //关闭事件
			 if(roleEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#roleInfoTable").datagrid("reload");//重新加载数据
			 }
			 roleEditDiag.close();
		 };
		 roleEditDiag.show();
	}
	
	
	
</script>

  
</html>
