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
			<table class="easyui-datagrid" id="ruTaskInfoTable" data-options="fit:true" toolbar="#ruTaskInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="ruTaskInfo_bar" class="dataGridToolBar">
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.RUTASK.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="ruTaskInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="ruTaskInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
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
		$("#ruTaskInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        sortName:"CREATE_TIME_",
	        sortOrder:"desc",
	        multiSort:true,
	        remoteSort: true, 
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>runningTask/querylistPage.do",     
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:100,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:handle('"+row.PROC_INST_ID_+"','"+row.ID_+"','"+row.DGRM_RESOURCE_NAME_+"','"+row.KEY_+"','"+row.BUSINESS_KEY_+"');\" class=\"btn btn-mini btn-success\" title=\"办理\"><i class=\"iconfont icon-bianji\"></i>&nbsp;办理</a>";
						}else{
							return "";
						}
					}	
				},
				{title:"流程名称",field:"PNAME_",align:"center",width:200,sortable:true},
				{title:"流程定义key",field:"KEY_",align:"center",width:150,sortable:true},
	        	{title:"申请人",field:"INITATOR",align:"center",width:120,sortable:true}, 	        	
	        	{title:"当前节点（待办人）",field:"ASSIGNEE_",align:"center",width:150,sortable:true}, 	        	
	        	{title:"当前任务）",field:"NAME_",align:"center",width:200,sortable:true}, 	        	
	        	{title:"创建时间",field:"CREATE_TIME_",align:"center",width:140,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#ruTaskInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#ruTaskInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#ruTaskInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#ruTaskInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });


		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#ruTaskInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="流程查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=ACT_RE_ruTask&sysDataTableName=ACT_RE_ruTask';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#ruTaskInfoTable").datagrid("options").url = "<%=basePath%>runningTask/querylistPage.do";
					 $("#ruTaskInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-流程";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=ACT_RE_ruTask&sysDataTableName=ACT_RE_ruTask';
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
			 columnHideSetDiag.Title ="隐藏列设置-流程";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=ACT_RE_ruTask&sysDataTableName=ACT_RE_ruTask';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#ruTaskInfo-reload").click(function(){
			$("#ruTaskInfoTable").datagrid("unselectAll");
			$("#ruTaskInfoTable").datagrid("reload");
		});
		
	});
	
	
	//办理任务
	function handle(PROC_INST_ID_,ID_,FILENAME,KEY_,BUSINESS_KEY_){
		var url= '<%=basePath%>runningTask/goHandle.do?PROC_INST_ID_='+PROC_INST_ID_+"&ID_="+ID_+"&KEY_="+KEY_+"&BUSINESS_KEY_="+BUSINESS_KEY_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
		var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="办理任务";
		 diag.URL = url;
		 diag.Width = 1100;
		 diag.Height = 650;
		 diag.Modal = true;				//有无遮罩窗口
		 diag.ShowMaxButton = false;	//最大化按钮
	     diag.ShowMinButton = false;		//最小化按钮 
		 diag.CancelEvent = function(){ //关闭事件
			 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none'){
				 $("#ruTaskInfoTable").datagrid("reload");//重新加载数据
			 }
			diag.close();
		 };
		 diag.show();
	}
	
</script>

  
</html>
