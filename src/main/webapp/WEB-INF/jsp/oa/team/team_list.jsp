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
			<table class="easyui-datagrid" id="teamInfoTable" data-options="fit:true" toolbar="#teamInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="teamInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.TEAM.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="teamInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.TEAM.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.TEAM.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="teamInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="teamInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="teamInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
		<shiro:hasPermission name="OA.TEAM.IMPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="teamInfo-importExcel"  title="从EXCEL导入"><i class="iconfont icon-daoru"></i>&nbsp;导入</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.TEAM.EXPORT">
			&nbsp;&nbsp;<a class="btn btn-success btn-mini" id="teamInfo-toExcel" title="导出到EXCEL"><i class="iconfont icon-daochu "></i>&nbsp;导出</a>
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
		$("#teamInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        multiSort:true,
	        remoteSort: true,
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>team/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"teamId",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:80,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editTeam('"+row.teamId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
						}else{
							return "";
						}
					}	
				},
				{title:"班组编码",field:"teamCode",align:"center",width:110,sortable:true}, 
	        	{title:"班组名称",field:"teamName",align:"center",width:130,sortable:true}, 	        	
	        	{title:"描述",field:"teamMemo",align:"center",width:150,sortable:true}, 
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
	         	$("#teamInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#teamInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#teamInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#teamInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });
		
		//导出excel
		$("#teamInfo-toExcel").click(function(){ 
			 var exUrl='<%=basePath%>team/excel.do?1=1';
			 if(queryConObj){
				 $.each(queryConObj,function(name,value) {
					 exUrl=exUrl+"&"+name+"="+value;
				 });
			 }
			 window.location.href=exUrl;
		});

		//打开上传excel页面
		$("#teamInfo-importExcel").click(function(){ 
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="班组导入";
			 diag.URL = '<%=basePath%>file/toImportFile.do?controllerName=team&suffix=xls';  
			 diag.Width = 450;
			 diag.Height = 200;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#teamInfoTable").datagrid("reload");//重新加载数据
				 }
				
				diag.close();
			 };
			 diag.show();
		});	

		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#teamInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="班组查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=OA_team&sysDataTableName=OA_team';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#teamInfoTable").datagrid("options").url = "<%=basePath%>team/querylistPage.do";
					 $("#teamInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-班组";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=OA_team&sysDataTableName=OA_team';
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
			 columnHideSetDiag.Title ="隐藏列设置-班组";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=OA_team&sysDataTableName=OA_team';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#teamInfo-reload").click(function(){
			$("#teamInfoTable").datagrid("unselectAll");
			$("#teamInfoTable").datagrid("reload");
		});
		
		//删除
		$("#teamInfo-remove").click(function(){
			var row=$("#teamInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var teamIdS="";
						$(row).each(function(i,item){
							var index=$("#teamInfoTable").datagrid("getRowIndex",item);
							if(teamIdS==""){
								teamIdS=teamIdS+item.teamId;
							}else{
								teamIdS=teamIdS+","+item.teamId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>team/delete.do",
							type:"post",
							async:false,
							data:{teamIdS:teamIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#teamInfoTable").datagrid("reload");
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
		$("#teamInfo-add").click(function(){
			 var teamAddDiag = new top.Dialog();
			 teamAddDiag.Drag=true;
			 teamAddDiag.Title ="班组新增";
			 teamAddDiag.URL = '<%=basePath%>team/toAdd.do';
			 teamAddDiag.Width = 550;
			 teamAddDiag.Height = 270;
			 teamAddDiag.CancelEvent = function(){ //关闭事件
				 if(teamAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#teamInfoTable").datagrid("reload");//重新加载数据
				 }
				 teamAddDiag.close();
			 };
			 teamAddDiag.show();
		});
		
	});
	
	
	//修改
	function editTeam(teamId){
		 var teamEditDiag = new top.Dialog();
		 teamEditDiag.Drag=true;
		 teamEditDiag.Title ="班组编辑";
		 teamEditDiag.URL = '<%=basePath%>team/toEdit.do?teamId='+teamId;
		 teamEditDiag.Width = 550;
		 teamEditDiag.Height = 270;
		 teamEditDiag.CancelEvent = function(){ //关闭事件
			 if(teamEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#teamInfoTable").datagrid("reload");//重新加载数据
			 }
			 teamEditDiag.close();
		 };
		 teamEditDiag.show();
	}
	
	
	
</script>

  
</html>
