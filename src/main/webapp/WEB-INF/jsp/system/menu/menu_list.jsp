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
	<div>
		<div id="dataGridDiv" class="dataGridDiv">
			<table class="easyui-datagrid" id="menuInfoTable" data-options="fit:true" toolbar="#menuInfo_bar">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="menuInfo_bar" class="dataGridToolBar">
		<shiro:hasPermission name="SYS.MENU.ADD">
			&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-primary" id="menuInfo-add"><i class="iconfont icon-tianjia"></i>&nbsp;增加</a>
		</shiro:hasPermission>
		<c:set var="editQx"  value="0"></c:set>
		<shiro:hasPermission name="SYS.MENU.EDIT">
			<c:set var="editQx" value="1"></c:set>
		</shiro:hasPermission>
		<shiro:hasPermission name="SYS.MENU.DELETE">
			&nbsp;&nbsp;<a class="btn btn-mini btn-danger" id="menuInfo-remove"><i class="iconfont icon-shanchu9"></i>&nbsp;删除</a>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="menuInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
		&nbsp;&nbsp;<a href="javascript:void(0)" class="btn btn-mini btn-purple"  id="menuInfo-back"><i class="iconfont icon-fanhui8"></i>&nbsp;返回</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-dark " id="menuInfo-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="queryConditionSet" ><i class="iconfont icon-shezhi"></i>&nbsp;查询设置</a>
		&nbsp;&nbsp;<a class="btn btn-mini btn-grey" id="columnHideSet" ><i class="iconfont icon-bukejian"></i>&nbsp;隐藏设置</a>
	</div>
	 
</body>


<script type="text/javascript">
	var level=1;//默认层级为1(系统菜单最多为2级)
	var MENU_ID=0;//默认上级ID为0
	var PRE_MENU_ID=0;//第一层的parent_id(返回按钮使用)

	//动态设定datagrid的高度
	function menuDataGridT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginLeft=parent.getMarginLeft();
		$("#dataGridDiv").css("height",h+"px");
		$("#dataGridDiv").css("width",(w-marginLeft)+"px");
	}

	$(function(){
		
		//动态设定datagrid的高度
		menuDataGridT();
		window.onresize=function(){  
			menuDataGridT();
		}; 
		
		
		//加载数据
		$("#menuInfoTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        sortName:"MENU_TYPE,MENU_ORDER",
	        sortOrder:"asc,asc",
	        multiSort:true,
	        remoteSort: true, 
	        pagination: true,
	        pageSize: 20,
	        rownumbers: true,
	        fitColumns: true,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>menu/querylistPage.do",     
	        frozenColumns:[[  
	            {field:"MENU_ID",checkbox:true},
	        ]],  
	        columns:[[  
				{title:"操作",field:"Operation",align:"center",width:140,
					formatter:function(value,row){
						var editQx=${editQx};
						//有修改权限
						if(editQx==1){
							return "<a  href=\"javascript:editmenu('"+row.MENU_ID+"');\" class=\"btn btn-mini btn-info\" title=\"编辑\"><i class=\"iconfont icon-bianji\"></i>&nbsp;编辑</a>"+
							"&nbsp;&nbsp;<a  href=\"javascript:editmenuIcon('"+row.MENU_ID+"');\" class=\"btn btn-mini btn-primary\" title=\"编辑图标\"><i class=\"iconfont icon-tupian3\"></i>&nbsp;图标</a>";
						}else{
							return "";
						}
					}	
				},
	        	{title:"菜单名称",field:"MENU_NAME",align:"center",width:120,
	        		formatter:function(value,row){
	        			return "<a href=\"javascript:goSonmenu('"+row.PARENT_ID+"','"+row.MENU_ID+"')\">"+row.MENU_NAME+"</a>";
	        		}	
	        	}, 
	        	{title:"资源路径",field:"MENU_URL",align:"center",width:250}, 
	        	{title:"权限代码",field:"MENU_AUTHORIZATION",align:"center",width:150}, 
	        	{title:"菜单分类",field:"MENU_TYPENAME",align:"center",width:80,sortable:true,
	        		formatter:function(value,row){
	        			if(row.MENU_TYPE=="1"){
	        				return "平台"; 
	        			}else{
	        				return ""; 
	        			}
	        		}
        		}, 
	        	{title:"序号",field:"MENU_ORDER",align:"center",width:50}, 
	        	{title:"状态",field:"MENU_STATE",align:"center",width:50,
	        		formatter:function(value){
	        			if(value=="1"){
	        				return "<i class=\"iconfont icon-kejian green\"></i>"; 
	        			}else{
	        				return "<i class=\"iconfont icon-bukejian red\"></i>"; 
	        			}
	        		}		
	        	}, 
	        	{title:"图标",field:"MENU_ICON",align:"center",width:50,
	        		formatter:function(value){
	        				return "<i class=\"iconfont "+value+"\"></i>"; 
	        		}		
	        	}
	        ]],
	        onClickRow:function(rowIndex, rowData){
	         	//取消选中当前页所有的行
	         	$("#menuInfoTable").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#menuInfoTable").datagrid("selectRow",rowIndex);
	         },
	         onLoadSuccess:function(data){
	         	<c:forEach items="${hideLst}" var="pd">
	         		var opts = $("#menuInfoTable").datagrid("getColumnOption","${pd.sysDataDeFieldCode}"); 
		         	if(opts!=null){
		         		$("#menuInfoTable").datagrid("hideColumn","${pd.sysDataDeFieldCode}");
		         	}
	       		</c:forEach>
		     }

	    });
		
		//查询：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#menuInfo-query").click(function(){
			 var queryDiag = new top.Dialog();
			 queryDiag.Drag=true;
			 queryDiag.Title ="菜单查询";
			 queryDiag.URL = '<%=basePath%>sysQueryConditionSet/getQueryCondition.do?sysDataIndex=sys_menu&sysDataTableName=sys_menu';
			 queryDiag.Width = 525;
			 queryDiag.Height = 500;
			 queryDiag.CancelEvent = function(){ //关闭事件
				 if(queryDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 var conObj = queryDiag.innerFrame.contentWindow.getQueryConObj();
					 
					 $("#menuInfoTable").datagrid("options").url = "<%=basePath%>menu/querylistPage.do";
					 $("#menuInfoTable").datagrid("load",conObj);
				 }
				 queryDiag.close();
			 };
			 queryDiag.show();
			
		});
		
		//查询条件设置：参数：sysDataIndex：系统数据索引，sysDataTableName：系统数据表名
		$("#queryConditionSet").click(function(){
			 var querySetDiag = new top.Dialog();
			 querySetDiag.Drag=true;
			 querySetDiag.Title ="查询条件设置-菜单";
			 querySetDiag.URL = '<%=basePath%>sysQueryConditionSet/queryConditionSet.do?sysDataIndex=sys_menu&sysDataTableName=sys_menu';
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
			 columnHideSetDiag.Title ="隐藏列设置-菜单";
			 columnHideSetDiag.URL = '<%=basePath%>sysColumnHideSet/getColumnHideSet.do?sysDataIndex=sys_menu&sysDataTableName=sys_menu';
			 columnHideSetDiag.Width = 800;
			 columnHideSetDiag.Height = 500;
			 columnHideSetDiag.CancelEvent = function(){ //关闭事件
				 columnHideSetDiag.close();
			 };
			 columnHideSetDiag.show();
		});
		
		
		//刷新
		$("#menuInfo-reload").click(function(){
			$("#menuInfoTable").datagrid("unselectAll");
			$("#menuInfoTable").datagrid("reload");
		});
		
		//删除
		$("#menuInfo-remove").click(function(){
			var row=$("#menuInfoTable").datagrid("getSelections");
			if(row.length>0){
				$.messager.confirm("提示","确定删除选中的"+row.length+"条记录？",function(flag){
					if(flag){
						var MENU_IDS="";
						$(row).each(function(i,item){
							var index=$("#menuInfoTable").datagrid("getRowIndex",item);
							//$("#menuInfoTable").datagrid("deleteRow",index);
							if(MENU_IDS==""){
								MENU_IDS=MENU_IDS+item.MENU_ID;
							}else{
								MENU_IDS=MENU_IDS+","+item.MENU_ID;
							}
						});
						
						top.showMsgProgress(); //显示进度条
						$.ajax({
							url:"<%=basePath%>menu/delete.do",
							type:"post",
							async:false,
							data:{MENU_IDS:MENU_IDS},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
									top.closeMsgProgress();//关闭进度条
									$("#menuInfoTable").datagrid("reload");
									$.messager.show({
										title:"系统提示",
										msg:"删除数据成功",
										timeout:2000,
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
		$("#menuInfo-add").click(function(){
			 
			 if(level>2){
				 $.messager.alert("系统提示","系统只支持二级菜单！","error");
				 return;
			 }
			
			 var menuAddDiag = new top.Dialog();
			 menuAddDiag.Drag=true;
			 menuAddDiag.Title ="新增菜单";
			 menuAddDiag.URL =  '<%=basePath%>menu/toAdd.do?MENU_ID='+MENU_ID;
			 menuAddDiag.Width = 400;
			 menuAddDiag.Height = 300;
			 menuAddDiag.CancelEvent = function(){ //关闭事件
				 if(menuAddDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
				 {
					 $("#menuInfoTable").datagrid("reload");//重新加载数据
				 }
				 menuAddDiag.close();
			 };
			 menuAddDiag.show();
		});
		
		//返回
		$("#menuInfo-back").click(function(){
			if(level==1){
				return;
			}else{
				if(level==2){
					$("#menuInfoTable").datagrid("options").url = "<%=basePath%>menu/menulistPage.do";
					$("#menuInfoTable").datagrid("load",{"MENU_ID":PRE_MENU_ID});
					MENU_ID=PRE_MENU_ID;
				}
				
				level=level-1;
				
			}
		});
		
	});
	
	//显示子菜单
	function goSonmenu(PARENT_ID,MUNEID){
		if(level==2){
			return;
		}else{
			level=level+1;
			MENU_ID=MUNEID;
			$("#menuInfoTable").datagrid("options").url = "<%=basePath%>menu/menulistPage.do";
			$("#menuInfoTable").datagrid("load",{"MENU_ID":MUNEID});
		}
		
	}
	
	
	//修改菜单
	function editmenu(MENU_ID){
		 var menuEditDiag = new top.Dialog();
		 menuEditDiag.Drag=true;
		 menuEditDiag.Title ="菜单编辑";
		 menuEditDiag.URL = '<%=basePath%>menu/toEdit.do?MENU_ID='+MENU_ID;
		 menuEditDiag.Width = 400;
		 menuEditDiag.Height = 300;
		 menuEditDiag.CancelEvent = function(){ //关闭事件
			 if(menuEditDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#menuInfoTable").datagrid("reload");//重新加载数据
			 }
			 menuEditDiag.close();
		 };
		 menuEditDiag.show();
	}
	
	//修改菜单图标
	function editmenuIcon(MENU_ID){
		var menuIconDiag = new top.Dialog();
		menuIconDiag.Drag=true;
		menuIconDiag.Title ="菜单图标编辑";
		menuIconDiag.URL = '<%=basePath%>menu/toIcon.do?MENU_ID='+MENU_ID;
		menuIconDiag.Width = 800;
		menuIconDiag.Height = 400;
		menuIconDiag.CancelEvent = function(){ //关闭事件
			 if(menuIconDiag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
			 {
				 $("#menuInfoTable").datagrid("reload");//重新加载数据
			 }
			 menuIconDiag.close();
		 };
		 menuIconDiag.show();
	}
	
	
</script>

  
</html>
