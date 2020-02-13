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
			<table class="easyui-datagrid" id="modelInfoTable" data-options="fit:true" toolbar="#modelInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="modelInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="OA.MODEL.ADD">
			&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="modelInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set> 
		<shiro:hasPermission name="OA.MODEL.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="OA.MODEL.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="modelInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission> 
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="modelInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="modelInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>			
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
		$("#modelInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        sortName:"LAST_UPDATE_TIME_",
	        sortOrder:"desc",
	        multiSort:true,
	        remoteSort: true, 
	        pagination: true,
	        pageSize: 10,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>model/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"ID_",checkbox:true},
	        ]],  
	        columns:[[ 
				{title:"操作",field:"Operation",align:"center",width:200,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							var editHtml="";
							editHtml="<a  href=\"javascript:editor('"+row.ID_+"');\" class=\"btn btn-mini btn-info\" title=\"流程设计器\"><i class=\"iconfont icon-sheji\"></i>&nbsp;流程设计器</a>";
							editHtml+="&nbsp;&nbsp;<a  href=\"javascript:deployment('"+row.ID_+"','F"+row.ID_+"');\" class=\"btn btn-mini btn-info\" title=\"部署\"><i class=\"iconfont icon-fabu\"></i>&nbsp;部署</a>";
							return editHtml;
						}else{
							return "";
						}
					}	
				},
				{title:"模型名称",field:"NAME_",align:"center",width:200,sortable:true}, 
	        	{title:"分类",field:"workFlowTypeName",align:"center",width:150,sortable:true}, 	        	
	        	{title:"创建时间",field:"CREATE_TIME_",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"2");
	        		}	
	        	}, 
	        	{title:"最后更新时间",field:"LAST_UPDATE_TIME_",align:"center",width:150,sortable:true,
	        		formatter:function(value,row){
	        			return formatDate(value,"1");
	        		}	
	        	}, 
	        	{title:"版本",field:"VERSION_",align:"center",width:100,sortable:true}, 
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#modelInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#modelInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#modelInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#modelInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
	         }

	    });


		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#modelInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="模型查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=ACT_RE_MODEL&sysDataTableName=ACT_RE_MODEL';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 queryConObj=conObj;//页面保存查询条件
					 
					 $("#modelInfoTable").datagrid("options").url = "<%=basePath%>model/querylistPage.do";
					 $("#modelInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-模型";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=ACT_RE_MODEL&sysDataTableName=ACT_RE_MODEL';
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
			 columnHideSetDiag.Title ="隐藏列设置-模型";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=ACT_RE_MODEL&sysDataTableName=ACT_RE_MODEL';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		//刷新
		$("#modelInfo-reload").click(function(){
			$("#modelInfoTable").datagrid("unselectAll");
			$("#modelInfoTable").datagrid("reload");
		});
		
		//删除
		$("#modelInfo-remove").click(function(){
			var row=$("#modelInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var modelIdS="";
						$(row).each(function(i,item){
							var index=$("#modelInfoTable").datagrid("getRowIndex",item);
							if(modelIdS==""){
								modelIdS=modelIdS+item.ID_;
							}else{
								modelIdS=modelIdS+","+item.ID_;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>model/delete.do",
							type:"post",
							async:false,
							data:{modelIdS:modelIdS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#modelInfoTable").datagrid("reload");
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
		$("#modelInfo-add").click(function(){
			 var modelAddDiag = new top.Dialog();
			 modelAddDiag.Drag=true;
			 modelAddDiag.Title ="模型新增";
			 modelAddDiag.URL = '<%=basePath%>model/toAdd.do';
			 modelAddDiag.Width = 550;
			 modelAddDiag.Height = 270;
			 modelAddDiag.CancelEvent = function(){ //关闭事件
				 if(modelAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#modelInfoTable").datagrid("reload");//重新加载数据
				 }
				 modelAddDiag.close();
			 };
			 modelAddDiag.show();
		});
		
	});
	
	//流程设计
	function editor(modelId){
		var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="流程设计器";
		 diag.URL = '<%=basePath%>model/editor.do?modelId='+modelId;
		 diag.Width = 1230;
		 diag.Height = 700;
		 diag.ShowMaxButton = true;	//最大化按钮
	     diag.ShowMinButton = true;		//最小化按钮
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 };
		 diag.show();
	}
	
	//发布
	function deployment(modelId,id){
		$.messager.confirm("提示","确定部署?",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:'<%=basePath%>model/deployment.do?tm='+new Date().getTime(),
					type:"post",
					async:false,
					data: {modelId:modelId},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							$("#modelInfoTable").datagrid("reload");
							$.messager.show({
								title:"系统提示",
								msg:"部署成功，可以到流程定义管理中查看",
								timeout:2000,
								showType:"slide"
							});
						}else if(data.result=="emptyXml"){
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","流程定义XML不能为空！","error");
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","部署出错:"+data.errmsg,"error");
						}
					}
				});
			}
		});
	}
	
	
	
</script>

  
</html>
