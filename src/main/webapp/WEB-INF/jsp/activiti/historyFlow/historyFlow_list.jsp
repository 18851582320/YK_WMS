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
	        url:"<%=basePath%>historyFlow/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"ID_",checkbox:true},
	        ]],    
	        columns:[[ 
	        	{title:"操作",field:"Operation",align:"center",width:110,
	        		formatter:function(value,row){
	        			return "<a  href=\"javascript:track('"+row.PROC_INST_ID_+"','"+row.ID_+"','"+row.DGRM_RESOURCE_NAME_+"','"+row.BUSINESS_KEY_+"','"+row.KEY_+"');\" class=\"btn btn-mini btn-info\" title=\"流程信息\"><i class=\"iconfont icon-liuchen\"></i>&nbsp;流程信息</a>";
	        		}	
	        	},
	        	{title:"流程名称",field:"PNAME_",align:"center",width:150,sortable:true},
	        	/* {title:"申请人",field:"INITATOR",align:"center",width:100},  */
	        	{title:"流程版本",field:"VERSION_",align:"center",width:100,sortable:true},
	        	{title:"开始时间",field:"START_TIME_",align:"center",width:140,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}		
	        	},
	        	{title:"结束时间",field:"END_TIME_",align:"center",width:140,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}		
	        	},
	        	{title:"用时",field:"ZTIME",align:"center",width:120,sortable:true},
	        	{title:"状态",field:"DELETE_REASON_",align:"center",width:100,sortable:true,
	        		formatter:function(value,row){
	        			if(isEmpty(value)){
	        				return "正常完成";
	        			}else{
	        				return "作废";
	        			}
	        		},
	        		styler: function(value,row,index){
	        			if(isEmpty(value)){
   	        				return "background-color:#1da02b;color:#fff;";
   	        			}else{
   	        				return "background-color:#f45438;color:#fff;";
   	        			}
   	        		}
	        	}
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
			queryDiag.Title ="历史流程查询";
			queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=ACT_HI_PROCINST&sysDataTableName=ACT_HI_PROCINST';
			queryDiag.Width = 525;
			queryDiag.Height = 500;
			queryDiag.CancelEvent = function(){ //关闭事件
				if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				{
					var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					queryConObj=conObj;//页面保存查询条件
					$("#dataInfoTable").datagrid("options").url = "<%=basePath%>historyFlow/querylistPage.do";
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
			 querySetDiag.Title ="查询条件设置-历史流程";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=ACT_HI_PROCINST&sysDataTableName=ACT_HI_PROCINST';
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
			 columnHideSetDiag.Title ="隐藏列设置-历史流程";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=ACT_HI_PROCINST&sysDataTableName=ACT_HI_PROCINST';
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
		
		
		
	});
	
	
	//流程信息
	function track(PROC_INST_ID_,ID_,FILENAME,BUSINESS_KEY_,KEY_){
		 var Diag = new top.Dialog();
		 Diag.Drag=true;
		 Diag.Title ="历史流程-流程信息";
		 Diag.URL = '<%=basePath%>historyFlow/track.do?PROC_INST_ID_='+PROC_INST_ID_+"&ID_="+ID_+"&BUSINESS_KEY_="+BUSINESS_KEY_+"&KEY_="+KEY_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
		 Diag.Width = 1000;
		 Diag.Height = 650;
		 Diag.CancelEvent = function(){ //关闭事件
			 Diag.close();
		 };
		 Diag.show();
	}
	
	
</script>

  
</html>
