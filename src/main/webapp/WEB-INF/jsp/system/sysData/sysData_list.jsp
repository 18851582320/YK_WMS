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
		<shiro:hasPermission name="SYS.sysData.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="dataInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="SYS.sysData.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="SYS.sysData.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="dataInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;<input class="easyui-textbox" style="width: 150px;height: 26px;" id="condition" name="condition" value="" prompt="请输入查询条件"/>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="dataInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>	
		&nbsp;&nbsp;
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
	        fitColumns: true,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>sysData/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"sysDataId",checkbox:true},
	        ]],    
	        columns:[[ 
	        	{title:"操作",field:"Operation",align:"center",width:80,
	        		formatter:function(value,row){
	        			var editQx=${editQx};
	        			if(editQx==1){
	        				return "<a  href=\"javascript:edit('"+row.sysDataId+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>";
	        			}else{
	        				return "";
	        			}
	        			
	        		}	
	        	},
	        	{title:"索引",field:"sysDataIndex",align:"center",width:250,sortable:true},
	        	{title:"表名称",field:"sysDataTableName",align:"center",width:250,sortable:true}, 
	        	{title:"备注",field:"sysDataDescribe",align:"center",width:350,sortable:true}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#dataInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#dataInfoTable").datagrid("selectRow",rowIndex);
	         }
	    });

		//查询
		$("#dataInfo-query").click(function(){
			var condition = $("#condition").val();
			$("#dataInfoTable").datagrid("load",{"keywords":condition});
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
								keyIdS=keyIdS+item.sysDataIndex;
							}else{
								keyIdS=keyIdS+","+item.sysDataIndex;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>sysData/deleteAll.do",
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
			 Diag.Title ="查询条件新增";
			 Diag.URL = '<%=basePath%>sysData/toAdd.do';
			 Diag.Width = 1060;
			 Diag.Height = 550;
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
	function edit(sysDataId){
		 var Diag = new top.Dialog();
		 Diag.Drag=true;
		 Diag.Title ="查询条件编辑";
		 Diag.URL = '<%=basePath%>sysData/toEdit.do?sysDataId='+sysDataId;
		 Diag.Width = 1060;
		 Diag.Height = 550;
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
