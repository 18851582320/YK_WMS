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
			<table class="easyui-datagrid" id="workFlowTypeInfoTable" data-options="fit:true" toolbar="#workFlowTypeInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="workFlowTypeInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.WORKFLOWTYPE.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="workFlowTypeInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.WORKFLOWTYPE.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="workFlowTypeInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="workFlowTypeInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
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
		$("#workFlowTypeInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>workFlowType/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"workFlowTypeId",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editworkFlowType('"+row.workFlowTypeId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
						}else{
							return "";
						}
					}	
				},
				{title:"模型分类编码",field:"workFlowTypeCode",align:"center",width:150,sortable:true}, 
	        	{title:"模型分类名称",field:"workFlowTypeName",align:"center",width:160,sortable:true}, 
	        	{title:"状态",field:"isEnable",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			if(value==0){
	        				return "停用";
	        			}else if(value==1){
	        				return "启用";
	        			}else{
	        				return "停用";
	        			}
	        		}	
	        	},
	        	{title:"模型分类备注",field:"workFlowTypeMemo",align:"center",width:200,sortable:true} 
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#workFlowTypeInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#workFlowTypeInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#workFlowTypeInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#workFlowTypeInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });


		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#workFlowTypeInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="模型分类查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=WF_workFlowType&sysDataTableName=WF_workFlowType';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#workFlowTypeInfoTable").datagrid("options").url = "<%=basePath%>workFlowType/querylistPage.do";
					 $("#workFlowTypeInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-模型分类";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=WF_workFlowType&sysDataTableName=WF_workFlowType';
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
			 columnHideSetDiag.Title ="隐藏列设置-模型分类";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=WF_workFlowType&sysDataTableName=WF_workFlowType';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#workFlowTypeInfo-reload").click(function(){
			$("#workFlowTypeInfoTable").datagrid("unselectAll");
			$("#workFlowTypeInfoTable").datagrid("reload");
		});
		
		
		
		//新增
		$("#workFlowTypeInfo-add").click(function(){
			 var workFlowTypeAddDiag = new top.Dialog();
			 workFlowTypeAddDiag.Drag=true;
			 workFlowTypeAddDiag.Title ="模型分类新增";
			 workFlowTypeAddDiag.URL = '<%=basePath%>workFlowType/toAdd.do';
			 workFlowTypeAddDiag.Width = 550;
			 workFlowTypeAddDiag.Height = 270;
			 workFlowTypeAddDiag.CancelEvent = function(){ //关闭事件
				 if(workFlowTypeAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#workFlowTypeInfoTable").datagrid("reload");//重新加载数据
				 }
				 workFlowTypeAddDiag.close();
			 };
			 workFlowTypeAddDiag.show();
		});
		
	});
	
	
	//修改
	function editworkFlowType(workFlowTypeId){
		 var workFlowTypeEditDiag = new top.Dialog();
		 workFlowTypeEditDiag.Drag=true;
		 workFlowTypeEditDiag.Title ="模型分类编辑";
		 workFlowTypeEditDiag.URL = '<%=basePath%>workFlowType/toEdit.do?workFlowTypeId='+workFlowTypeId;
		 workFlowTypeEditDiag.Width = 550;
		 workFlowTypeEditDiag.Height = 270;
		 workFlowTypeEditDiag.CancelEvent = function(){ //关闭事件
			 if(workFlowTypeEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#workFlowTypeInfoTable").datagrid("reload");//重新加载数据
			 }
			 workFlowTypeEditDiag.close();
		 };
		 workFlowTypeEditDiag.show();
	}
	
	
	
</script>

  
</html>
