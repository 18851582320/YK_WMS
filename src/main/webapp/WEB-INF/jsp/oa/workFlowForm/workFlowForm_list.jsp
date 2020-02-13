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
	<div  id="dataInfo_bar" style="height: 35px;padding-top: 6px;">
		<shiro:hasPermission name="WF.FORM.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="dataInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="WF.FORM.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="WF.FORM.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="dataInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="dataInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>	
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
		$("#dataInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,  
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>workFlowForm/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"formId",checkbox:true},
	        ]],    
	        columns:[[ 
	        	{title:"操作",field:"Operation",align:"center",width:80,
	        		formatter:function(value,row){
	        			var editQx=${editQx};
	        			if(editQx==1){
	        				return "<a  href=\"javascript:edit('"+row.formId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
	        			}else{
	        				return "";
	        			}
	        			
	        		}	
	        	},
	        	{title:"表单名称",field:"formName",align:"center",width:150,sortable:true},
	        	{title:"模型分类名称",field:"workFlowTypeName",align:"center",width:100}, 
	        	{title:"表单主键",field:"formKeyName",align:"center",width:100,sortable:true},
	        	{title:"表单url",field:"formUrl",align:"center",width:300,sortable:true},
	        	{title:"备注",field:"formMemo",align:"center",width:250,sortable:true}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#dataInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#dataInfoTable").datagrid("selectRow",rowIndex);
	         },
	         //如果设置隐藏列的字段不在列表显示的话会出错，加上这个判断
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         	var opts = $("#dataInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
	         	if(opts!=null){
	         		$("#dataInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
	         	}
	       		</c:forEach>
		    }
	    });

		//查询
		$("#dataInfo-query").click(function(){
			var queryDiag = new top.Dialog();
			queryDiag.Drag=true;
			queryDiag.Title ="流程表单查询";
			queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=WF_workFlowForm&sysDataTableName=WF_workFlowForm';
			queryDiag.Width = 525;
			queryDiag.Height = 500;
			queryDiag.CancelEvent = function(){ //关闭事件
				if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				{
					var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					queryConObj=conObj;//页面保存查询条件
					$("#dataInfoTable").datagrid("options").url = "<%=basePath%>workFlowForm/querylistPage.do";
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
			 querySetDiag.Title ="查询条件设置-流程表单";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=WF_workFlowForm&sysDataTableName=WF_workFlowForm';
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
			 columnHideSetDiag.Title ="隐藏列设置-流程表单";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=WF_workFlowForm&sysDataTableName=WF_workFlowForm';
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
								keyIdS=keyIdS+item.formId;
							}else{
								keyIdS=keyIdS+","+item.formId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>workFlowForm/delete.do",
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
										timeout:3000,
										showType:"slide"
									});
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
			 Diag.Title ="流程表单新增";
			 Diag.URL = '<%=basePath%>workFlowForm/toAdd.do';
			 Diag.Width = 550;
			 Diag.Height = 270;
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
	
	
	//修改
	function edit(formId){
		 var Diag = new top.Dialog();
		 Diag.Drag=true;
		 Diag.Title ="流程表单编辑";
		 Diag.URL = '<%=basePath%>workFlowForm/toEdit.do?formId='+formId;
		 Diag.Width = 550;
		 Diag.Height = 270;
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
