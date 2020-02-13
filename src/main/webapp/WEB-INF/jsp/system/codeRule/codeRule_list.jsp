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
			<table class="easyui-datagrid" id="codeRuleInfoTable" data-options="fit:true" toolbar="#codeRuleInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="codeRuleInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="SYS.CodeRule.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="codeRuleInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="SYS.CodeRule.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="SYS.CodeRule.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="codeRuleInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="codeRuleInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="codeRuleInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-shezhi"></i>&nbsp;隐藏设置</a>
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
		$("#codeRuleInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
			sortName: "createTime",  
	        sortOrder: "asc",
	        multiSort:true,
	        remoteSort: true,  
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns: true,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>codeRule/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"codeRuleId",checkbox:true},
	        ]],  
	        columns:[[  
				{title:"操作",field:"Operation",align:"center",width:100,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editCodeRule('"+row.codeRuleId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
						}else{
							return "";
						}
					}	
				},
				{title:"说明",field:"explain",align:"center",width:200,sortable:true}, 
	        	{title:"表名称",field:"tableName",align:"center",width:150,sortable:true}, 
	        	{title:"字段名称",field:"fieldName",align:"center",width:150,sortable:true}, 
	        	{title:"编码类型",field:"codeRuleType",align:"center",width:150,
	        		formatter:function(value){
	        			if(value=="1"){
	        				return "手动输入"; 
	        			}if(value=="2"){
	        				return "前缀+自增"; 
	        			}if(value=="3"){
	        				return "前缀+年月日+流水号"; 
	        			}
	        		}		
	        	}, 
	        	{title:"前缀",field:"prefix",align:"center",width:150}, 
	        	{title:"流水号位数",field:"serialNumLength",align:"center",width:100}
	        	/* {title:"创建时间",field:"createTime",align:"center",width:130,sortable:false,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}
	        	} */
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#codeRuleInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#codeRuleInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#codeRuleInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#codeRuleInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#codeRuleInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="编码规则查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=SYS_codeRuleSet&sysDataTableName=SYS_codeRuleSet';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 $("#codeRuleInfoTable").datagrid("options").url = "<%=basePath%>codeRule/querylistPage.do";
					 $("#codeRuleInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-编码规则";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=SYS_codeRuleSet&sysDataTableName=SYS_codeRuleSet';
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
			 columnHideSetDiag.Title ="隐藏列设置-编码规则";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=SYS_codeRuleSet&sysDataTableName=SYS_codeRuleSet';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		
		//刷新
		$("#codeRuleInfo-reload").click(function(){
			$("#codeRuleInfoTable").datagrid("unselectAll");
			$("#codeRuleInfoTable").datagrid("reload");
		});
		
		//删除
		$("#codeRuleInfo-remove").click(function(){
			var row=$("#codeRuleInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var codeRuleIdS="";
						$(row).each(function(i,item){
							var index=$("#codeRuleInfoTable").datagrid("getRowIndex",item);
							//$("#codeRuleInfoTable").datagrid("deleteRow",index);
							if(codeRuleIdS==""){
								codeRuleIdS=codeRuleIdS+item.codeRuleId;
							}else{
								codeRuleIdS=codeRuleIdS+","+item.codeRuleId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>codeRule/delete.do",
							type:"post",
							async:false,
							data:{codeRuleIdS:codeRuleIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#codeRuleInfoTable").datagrid("reload");
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
		$("#codeRuleInfo-add").click(function(){
			 var codeRuleAddDiag = new top.Dialog();
			 codeRuleAddDiag.Drag=true;
			 codeRuleAddDiag.Title ="编码规则新增";
			 codeRuleAddDiag.URL = '<%=basePath%>codeRule/toAdd.do';
			 codeRuleAddDiag.Width = 550;
			 codeRuleAddDiag.Height = 230;
			 codeRuleAddDiag.CancelEvent = function(){ //关闭事件
				 if(codeRuleAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#codeRuleInfoTable").datagrid("reload");//重新加载数据
				 }
				 codeRuleAddDiag.close();
			 };
			 codeRuleAddDiag.show();
		});
		
	});
	
	
	//修改
	function editCodeRule(codeRuleId){
		 var codeRuleEditDiag = new top.Dialog();
		 codeRuleEditDiag.Drag=true;
		 codeRuleEditDiag.Title ="编码规则编辑";
		 codeRuleEditDiag.URL = '<%=basePath%>codeRule/toEdit.do?codeRuleId='+codeRuleId;
		 codeRuleEditDiag.Width = 550;
		 codeRuleEditDiag.Height = 230;
		 codeRuleEditDiag.CancelEvent = function(){ //关闭事件
			 if(codeRuleEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#codeRuleInfoTable").datagrid("reload");//重新加载数据
			 }
			 codeRuleEditDiag.close();
		 };
		 codeRuleEditDiag.show();
	}
	
	
	
</script>

  
</html>
