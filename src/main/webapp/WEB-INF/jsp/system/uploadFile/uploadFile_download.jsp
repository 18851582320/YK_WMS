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
<body class="detailDivBody">
	<div class="detailDiv">
		<div class="detailDataGridDiv" id="detailDataGridDiv">
			<table class="easyui-datagrid" id="dataInfoTable" data-options="fit:true" toolbar="#dataInfo_bar">
				
			</table>
		</div>
		<!-- 工具栏 -->
		<div  id="dataInfo_bar" class="dataGridToolBar">
			<c:if test="${pd.isLook ne 'isLook'}">
				&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="dataInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
				&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="dataInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
			</c:if>
			&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="dataInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>	
			&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
		</div>
		<div class="detailButtonDiv">
			<a class="btn btn-mini btn-danger" onclick="closeSelectDialog();"><i class="iconfont icon-guanbi"></i>&nbsp;关闭</a>
		</div>
	</div>
</body>

<script type="text/javascript">

	var queryConObj=null;
	
	$(function(){
		
		setDetailDivHeight();
		
		var tableName= "${pd.tableName}";
		var tableKeyValue= "${pd.tableKeyValue}";
		
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
	        url:'<%=basePath%>uploadFile/downloadlistPage.do',
	        queryParams:{
	        	tableName:tableName,
	        	tableKeyValue:tableKeyValue
	        },
	        frozenColumns:[[  
	            {field:"upFileId",checkbox:true},
	        ]],  
	    	columns:[[
	    		{title:"操作",field:"Operation",align:"center",width:150,
	        		formatter:function(value,row){
	        			var editHtml="";
        				editHtml= "<a  href=\"javascript:download('"+row.upFileId+"');\" class=\"btn btn-mini btn-info\" title=\"下载\"><i class=\"iconfont icon-xiazai2\"></i>&nbsp;下载</a>";
	        			var fileName = row.fileName;
	        			var fileType  = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
	        			if(fileType=="jpg"||fileType=="png"||fileType=="bmp"||fileType=="jpeg"||fileType=="pdf"||fileType=="mp4"){
	        				editHtml+= "&nbsp;&nbsp;<a  href=\"javascript:preview('"+row.upFileId+"');\" class=\"btn btn-mini btn-info\" title=\"预览\"><i class=\"iconfont icon-chaxun5\"></i>&nbsp;预览</a>";
	        			}else{
	        				editHtml+= "&nbsp;&nbsp;<a  class=\"btn btn-mini btn-grey\" title=\"预览\"><i class=\"iconfont icon-chaxun5\"></i>&nbsp;预览</a>";
	        			}
	        			return editHtml;
	        		}	
	        	},
				{title:"文件名称",field:"fileName",align:"center",width:400,sortable:true}, 
				{title:"上传人员",field:"updateStaffName",align:"center",width:100,sortable:true},
				{title:"上传时间",field:"uploadTime",align:"center",width:130,sortable:true,
					formatter:function(value,row){
						return formatDate(value,"1");
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
			queryDiag.Title ="附件查询";
			queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=SYS_uploadFile_download&sysDataTableName=SYS_uploadFile_download';
			queryDiag.Width = 525;
			queryDiag.Height = 500;
			queryDiag.CancelEvent = function(){ //关闭事件
				if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				{
					var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					queryConObj=conObj;//页面保存查询条件
					
					queryConObj["tableName"]=tableName;
					queryConObj["tableKeyValue"]=tableKeyValue;
					
					$("#dataInfoTable").datagrid("options").url = "<%=basePath%>uploadFile/downloadlistPage.do";
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
			 querySetDiag.Title ="查询条件设置-附件";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=SYS_uploadFile_download&sysDataTableName=SYS_uploadFile_download';
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
			 columnHideSetDiag.Title ="隐藏列设置-附件";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=SYS_uploadFile_download&sysDataTableName=SYS_uploadFile_download';
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
								keyIdS=keyIdS+item.upFileId;
							}else{
								keyIdS=keyIdS+","+item.upFileId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>uploadFile/deleteAll.do",
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
			 var Diag = new Dialog();
			 Diag.Drag=true;
			 Diag.Title ="附件新增";
			 Diag.URL = '<%=basePath%>uploadFile/toAddFile.do?tableName='+tableName+'&tableKeyValue='+tableKeyValue;
			 Diag.Width = 600;
			 Diag.Height = 150;
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
	
	//预览
	function preview(upFileId){
		var Diag = new top.Dialog();
		Diag.Drag=true;
		Diag.Title ="附件预览";
		Diag.URL = '<%=basePath%>uploadFile/preview.do?winH=800&upFileIdS='+upFileId;
		Diag.Width = 1400;
		Diag.Height = 800;
		Diag.CancelEvent = function(){ //关闭事件
			Diag.close();
		};
		Diag.show();
	}


	/**
	 *关闭窗口 
	 */
	function closeSelectDialog(){
		var winType="${pd.winType}";
		if(winType){
			if(winType=="top"){
				top.Dialog.close();
			}if(winType=="parent"){
				parent.Dialog.close();
			}else{
				top.Dialog.close();
			}
		}else{
			top.Dialog.close();
		}
		
	}
	
	
	function download(upFileId){
		window.location.href='<%=basePath%>uploadFile/downloadFile.do?upFileId='+upFileId;
	}
	

	
</script>

</html>