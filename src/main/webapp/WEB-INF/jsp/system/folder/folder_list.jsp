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
	
	<style>
		.folder-box ul{ text-align: center;}
		.folder-box ul li{ float: left; margin-left: 15px; margin-top: 10px; padding: 10px 10px 15px 10px; border: 1px solid transparent;width: 100px; height: 100px;}
		.folder-box ul li:hover{ background-color: #f3f9fb; border-color:#72c4f5; }
		.folder-box ul li img{ width: 53px; height: 46px;}
		.folder-box ul li p{ color: #555555; font-size: 14px; margin-top: 10px; line-height: 20px;}
		.selectOn{ background-color: #f3f9fb;  border-color:#72c4f5; }
	</style>
	
</head>
<body>
	<!-- dataGrid -->
	<div>
		<!-- 工具栏 -->
		<div  id="childFolder_bar" style="height: 35px;">
			<a class="btn btn-mini btn-primary" id="folder-add"><i class="iconfont icon-tianjia"></i>&nbsp;新建文件夹</a>
			<!-- &nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="folder-remove" onclick="removeFolder()"><i class="iconfont icon-shanchu9"></i>&nbsp;删除文件夹</a> -->
			&nbsp;&nbsp;<a class="btn btn-mini btn-dark" id="folder-rename" onclick="reName()"><i class="iconfont icon-bianji"></i>&nbsp;重命名</a>  
			&nbsp;&nbsp;<a  class="btn btn-mini btn-purple" id="dataInfo-back" ><i class="iconfont icon-fanhui8"></i>&nbsp;返回</a>
			&nbsp;&nbsp;<span style="font-size: 12px;">当前文件夹：</span><span style="font-size: 12px;" id="currentFolder"></span>
		</div>
		<div id="childFolderDiv" style="height: 200px;margin-bottom: 5px;">
			<div class="easyui-panel" title="子文件夹" data-options="fit:true" tools="" id="childFolderPanel">
				<div class="folder-box">
					<ul class="clearfix" id="folder-boxContent">
						
					</ul>
				</div>
			</div>
		</div>
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="dataInfoTable" data-options="fit:true" toolbar="#dataInfo_bar" title="文件">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="dataInfo_bar" class="dataGridToolBar">
			&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-primary"  id="dataInfo-add"><i class="iconfont icon-shangchuan"></i>&nbsp;上传文件</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="dataInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除文件</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue" id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-dark " id="dataInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
			&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
	</div>
	 
</body>


<script type="text/javascript">
	
	var folderId="${folderId}";//当前文件夹id
	var choseFolderId=null;

	//动态设定datagrid的高度
	function setDataGridT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginLeft=parent.getMarginLeft();
		$("#dataGridDiv").css("height",(h-240)+"px");
		$("#dataGridDiv").css("width",(w-marginLeft)+"px");
		$("#childFolderDiv").css("width",(w-marginLeft)+"px");
		
		$("#childFolderPanel").panel("resize",{
			width: w-marginLeft,
			height: 200
		});
		
	}

	$(function(){
		
		//动态设定datagrid的高度
		setDataGridT();
		window.onresize=function(){  
			setDataGridT();
		};
		
		//加载数据
		$("#dataInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        sortName:"folderDocName",
	        sortOrder:"asc",
	        multiSort:true,
	        remoteSort: true, 
	        pagination: true,
	        pageSize: 20,
	        rownumbers: true,
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>folderDoc/querylistPage.do",
	        queryParams:{
	        	folderId:folderId
	        },
	        frozenColumns:[[  
	            {field:"folderDocId",checkbox:true},
	        ]],  
	        columns:[[  
				{title:"操作",field:"Operation",align:"center",width:140,
					formatter:function(value,row){
						return "<a  href=\"javascript:download('"+row.folderDocId+"');\" class=\"btn btn-mini btn-purple\" title=\"下载\"><i class=\"iconfont icon-xiazai\"></i>&nbsp;下载</a>";
					}	
				},
	        	{title:"文件名称",field:"folderDocName",align:"center",width:300}, 
	        	{title:"版本",field:"folderDocVersion",align:"center",width:100},
	        	{title:"备注",field:"folderDocMemo",align:"center",width:150},
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
	         	$("#dataInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#dataInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#dataInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#dataInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
		     }

	    });
		
		
		
		//加载子文件件
		loadChildFolder();
		
		
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#dataInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="文档查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=WD_folderDoc&sysDataTableName=WD_folderDoc';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 conObj["folderId"]=folderId;
					 
					 $("#dataInfoTable").datagrid("options").url = "<%=basePath%>folderDoc/querylistPage.do";
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
			 querySetDiag.Title ="查询条件设置-文档";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=WD_folderDoc&sysDataTableName=WD_folderDoc';
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
			 columnHideSetDiag.Title ="隐藏列设置-文档";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=WD_folderDoc&sysDataTableName=WD_folderDoc';
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
		
		
		
		
		//新建文件夹
		$("#folder-add").click(function(){
			 var Diag = new top.Dialog();
			 Diag.Drag=true;
			 Diag.Title ="新建文件夹";
			 Diag.URL =  '<%=basePath%>folder/toAdd.do?folderId='+folderId;
			 Diag.Width = 550;
			 Diag.Height = 270;
			 Diag.CancelEvent = function(){ //关闭事件
				 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 loadChildFolder();
					 parent.treeReLoad();
				 }
				 Diag.close();
			 };
			 Diag.show();
		});
		
		
		//新建文件
		$("#dataInfo-add").click(function(){
			 var Diag = new top.Dialog();
			 Diag.Drag=true;
			 Diag.Title ="上传文件";
			 Diag.URL =  '<%=basePath%>folderDoc/toAdd.do?folderId='+folderId;
			 Diag.Width = 670;
			 Diag.Height = 330;
			 Diag.CancelEvent = function(){ //关闭事件
				 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#dataInfoTable").datagrid("reload");
				 }
				 Diag.close();
			 };
			 Diag.show();
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
								keyIdS=keyIdS+item.folderDocId;
							}else{
								keyIdS=keyIdS+","+item.folderDocId;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>folderDoc/delete.do",
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
		
		
		
		//返回：返回上一层文件夹
		$("#dataInfo-back").click(function(){
			$.ajax({
				url:"<%=basePath%>folder/loadParentFolder.do",
				type:"post",
				data:{folderId:folderId},
				dataType:"json",
				success:function(data){
					if(data){
						if(data.parentFolderId=="0"){
							
						}else{
							folderId=data.parentFolderId;
							$("#currentFolder").text(data.folderName);
							loadChildFolder();
							$("#dataInfoTable").datagrid("load",{folderId:folderId});
						}
					}
				}
			});
		});
		
		
	});
	
	
	function download(folderDocId){
		window.location.href='<%=basePath%>folderDoc/download.do?folderDocId='+folderDocId;
	}
	
	//重命名
	function reName(){
		 
		if(choseFolderId==null){
			$.messager.alert("系统提示","请单击选择一个文件夹","error");
			return;
		}
		var Diag = new top.Dialog();
		 Diag.Drag=true;
		 Diag.Title ="修改件夹";
		 Diag.URL =  '<%=basePath%>folder/toEdit.do?folderId='+choseFolderId;
		 Diag.Width = 550;
		 Diag.Height = 270;
		 Diag.CancelEvent = function(){ //关闭事件
			 if(Diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 loadChildFolder();
				 parent.treeReLoad();
			 }
			 Diag.close();
		 };
		 Diag.show();
	}
	
	
	function loadFolderName(){
		$.ajax({
			url:"<%=basePath%>folder/loadParentFolder.do",
			type:"post",
			data:{folderId:folderId},
			dataType:"json",
			success:function(data){
				if(data){
					$("#currentFolder").text(data.folderName);
				}
			}
		});
	}
	
	//双击显示子文件夹
	function showFolder(obj){
		choseFolderId=null;
		folderId=obj.id;
		loadChildFolder();
		$("#dataInfoTable").datagrid("load",{folderId:folderId});
	}
	
	
	function setFolder(obj){
		choseFolderId=obj.id;
		$("ul > li").css({
			"background-color":" transparent",
			"border-color":"transparent"
		});
		$("#"+choseFolderId).css({
			"background-color":" #f3f9fb",
			"border-color":"#72c4f5"
		});
	}
	
	
	//加载子文件夹
	function loadChildFolder(){
		$.ajax({
			url:"<%=basePath%>folder/loadChildFolder.do",
			type:"post",
			data:{parentFolderId:folderId},
			dataType:"json",
			success:function(data){
				if(data){
					var html="";
					$.each(data,function(index,obj){
						html+="<li id='"+obj.folderId+"' onclick='setFolder(this)' ondblclick='showFolder(this)')><img src='static/image/folder.png'/><p>"+obj.folderName+"</p></li>";
					});
					$("#folder-boxContent").html(html);
				}
			}
		});
		
		
		loadFolderName();
		
	}
	
	
</script>

  
</html>
