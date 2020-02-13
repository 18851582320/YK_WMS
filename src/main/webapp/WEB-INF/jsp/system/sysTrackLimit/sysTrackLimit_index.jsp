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
	
	<style type="text/css">
		.box-list{ border: 1px solid #e3e3e3;; width: 100%; height: 100%;overflow: auto;}
		.box-list ul li{ padding-top: 15px; padding-bottom: 15px; padding-left: 30px; }
		.box-list ul li span{ color: #3f3f3f; font-size: 16px;}
		.box-list ul li a i{ margin-right: 30px; color:#3f3f3f ; float: right; margin-right: 30px;}
		.box-list ul li a i:hover{ color: red;}
		.OperationA{
			color: #1C88B9;
		}
		.OperationDel{
			color: #d15b47;
		}
	</style>
	
</head>
<body>
	<!-- dataGrid -->
	<div>
		<div id="dataDiv" style="">
			
			<div style="height: 40px;font-size: 12px;">
				<div style="float: left;width: 2%;height: 100%;"></div>
				<div style="float: left;width: 98%;height: 100%;">
					被分配人员：
					<select id="mainUser" name="mainUser" class="easyui-combogrid" limitToList="true"  style="width: 154px;height: 26px;"   
				        data-options="    
				            required:true,
				            panelWidth:260,    
				            value:'',    
				            idField:'STAFF_ID',    
				            textField:'NAME', 
				            queryParams:{
				            	 
				            },   
				            url:'<%=basePath%>staff/listAll.do',    
				            columns:[[    
				                {field:'STAFF_ID',title:'流水号',width:60,hidden:true},
				                {field:'NAME',title:'姓名',width:80},        
				                {field:'DEPARTMENT_NAME',title:'部门',width:100},    
				                {field:'dutyName',title:'职务',width:100}    
				            ]]    
				        ">
				     </select>
				     
				     
				     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-primary" onclick="saveDept();"><i class="iconfont icon-baocun8"></i>&nbsp;保存选择的部门</a>
				     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;保存选择的人员</a>
				     
				</div>
			</div>
			
			<div id="middleDiv">
				<div style="float: left;width: 2%;height: 100%;"></div>
				<div style="float: left;width: 40%;height: 100%;">
					<table class="easyui-treegrid" id="dataInfoTable" data-options="fit:true,showPageList: false,showRefresh: false,displayMsg: ''" title="待分配部门">
						
					</table>
				</div>
				<div style="float: left;width: 2%;height: 100%;"></div>
				<div style="float: left;width: 20%;height: 100%;" >
					<table class="easyui-datagrid" id="staffTable" data-options="fit:true" title="待分配人员">
						
					</table>
				</div>
				<div style="float: left;width: 2%;height: 100%;"></div>
				<div style="float: left;width: 32%;height: 100%;" >
					<table class="easyui-datagrid" id="yiFenPeiTable" data-options="fit:true" title="已分配">
						
					</table>
				</div>
			</div>
		</div>
	</div>
	 
</body>


<script type="text/javascript">
	
	var trackLimitTypeCode="${pd.trackLimitTypeCode}";//默认层级为1(系统系统数据最多为2级)

	//动态设定datagrid的高度
	function DataDivT(){
		var h=$(window).height();
		var w=$(window).width();
		var marginLeft=parent.getMarginLeft();
		$("#dataDiv").css("height",h+"px");
		$("#middleDiv").css("height",(h-40)+"px");
		$("#dataDiv").css("width",(w-marginLeft)+"px");
		
		var wid=parseInt((w-marginLeft)*0.48);
	}

	$(function(){
		
		//动态设定datagrid的高度
		DataDivT();
		window.onresize=function(){  
			DataDivT();
		};
		
		
		$("#dataInfoTable").treegrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        rownumbers: true,
	        singleSelect:false,
	        idField:"DEPARTMENT_ID",
	        treeField:"NAME",
	        fitColumns:false,
	        lines:true,
	        url:'<%=basePath%>department/listNoPage.do',     
	        frozenColumns:[[  
	            {field:"DEPARTMENT_ID",checkbox:true},
	        ]],  
	        columns:[[  
	        	{title:"部门名称",field:"NAME",align:"left",width:200,sortable:false},
	        	{title:"部门人员",field:"Operation",align:"center",width:60,
	        		formatter:function(value,row){
        				var editHtml="";
        				editHtml+="&nbsp;<a  href=\"javascript:staffInfo('"+row.DEPARTMENT_ID+"');\" class='OperationA' title=\"部门人员\"><i class=\"iconfont icon-zuzhijiagou\"></i>&nbsp;人员</a>";
        				return editHtml;
	        			
	        		}	
	        	}
	        ]]

	    });
		
		
		//加载数据
		$("#yiFenPeiTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        rownumbers: true,
	        fitColumns:false,
	        frozenColumns:[[  
	            {field:"trackLimitId",checkbox:true},
	        ]],  
	        columns:[[
	        	{title:"删除",field:"Operation",align:"center",width:60,
	        		formatter:function(value,row){
        				var editHtml="";
        				editHtml+="&nbsp;<a  href=\"javascript:deleteUser('"+row.trackLimitId+"');\" class='OperationDel' title=\"删除\"><i class=\"iconfont icon-shanchu9\"></i>&nbsp;删除</a>";
        				return editHtml;
	        			
	        		}	
	        	},
	        	{title:"姓名",field:"NAME",align:"center",width:100,sortable:true}, 
	        	{title:"部门",field:"DEPARTMENT_NAME",align:"center",width:100,sortable:true}, 
	        ]]
	    });
		
		
		//加载数据
		$("#staffTable").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        rownumbers: true,
	        fitColumns:false,
	        frozenColumns:[[  
	            {field:"STAFF_ID",checkbox:true},
	        ]],  
	        columns:[[
	        	{title:"姓名",field:"NAME",align:"center",width:120,sortable:true}, 
	        ]]
	    });
		
		
		
		$("#mainUser").combogrid({
			onChange:function(newValue,oldValue){
				$("#yiFenPeiTable").datagrid("options").url = "<%=basePath%>sysTrackLimit/listByMainUserAndType.do";
				$("#yiFenPeiTable").datagrid("load",{mainUser:newValue,trackLimitTypeCode:trackLimitTypeCode});
			}
		});
		
		
		
	});
	
	//部门人员信息
	function staffInfo(DEPARTMENT_ID){
		var mainUser=$("#mainUser").combogrid("getValue");
		if(mainUser){
			$("#staffTable").datagrid("options").url = "<%=basePath%>staff/listForTrackLimit.do";
			$("#staffTable").datagrid("load",{DEPARTMENT_ID:DEPARTMENT_ID,mainUser:mainUser,trackLimitTypeCode:trackLimitTypeCode});
		}else{
			$.messager.alert("系统提示","请先选择被分配人员！","error");
		}
	
	}
	
	function deleteUser(trackLimitId){
		
		$.messager.confirm("提示","确定删除该人员？",function(flag){
			if(flag){
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>sysTrackLimit/deleteAll.do",
					type:"post",
					async:false,
					data:{keyIdS:trackLimitId},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							
							$("#dataInfoTable").treegrid("unselectAll");
							$("#staffTable").datagrid("unselectAll");
							$("#staffTable").datagrid("reload");	
							
							$("#yiFenPeiTable").datagrid("unselectAll");
							$("#yiFenPeiTable").datagrid("reload");	
							
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
		
		
	
	}
	
	
	function saveDept(){
		var mainUser=$("#mainUser").combogrid("getValue");
		
		if(isEmpty(mainUser)){
			$.messager.alert("系统提示","请选择正确的被分配人员！","error");
			return;
		}
		
		
		if(mainUser){
			var row=$("#dataInfoTable").treegrid("getSelections");
			if(row.length>0){
				
				var keyIdS="";
				$(row).each(function(i,item){
					if(keyIdS==""){
						keyIdS=keyIdS+item.DEPARTMENT_ID;
					}else{
						keyIdS=keyIdS+","+item.DEPARTMENT_ID;
					}
				});
				
				
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>sysTrackLimit/saveByDept.do",
					type:"post",
					async:false,
					data:{DEPARTMENT_IDS:keyIdS,mainUser:mainUser,trackLimitTypeCode:trackLimitTypeCode},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							
							$("#dataInfoTable").treegrid("unselectAll");
							$("#staffTable").datagrid("unselectAll");
							$("#staffTable").datagrid("reload");	
							
							$("#yiFenPeiTable").datagrid("unselectAll");
							$("#yiFenPeiTable").datagrid("reload");							
							
							$.messager.show({
								title:"系统提示",
								msg:"保存数据成功",
								timeout:3000,
								showType:"slide"
							});
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
						}
					}
				});
				
				
				
			}else{
				$.messager.alert("系统提示","请选择要保存的部门数据","error");
			}
		}else{
			$.messager.alert("系统提示","请先选择被分配人员！","error");
		}
	}
	
	
	
	
	
	function save(){
		var mainUser=$("#mainUser").combogrid("getValue");
		if(mainUser){
			var row=$("#staffTable").datagrid("getSelections");
			if(row.length>0){
				
				var keyIdS="";
				$(row).each(function(i,item){
					if(keyIdS==""){
						keyIdS=keyIdS+item.STAFF_ID;
					}else{
						keyIdS=keyIdS+","+item.STAFF_ID;
					}
				});
				
				
				top.showMsgProgress(); //显示进度条
				$.ajax({
					url:"<%=basePath%>sysTrackLimit/saveByUser.do",
					type:"post",
					async:false,
					data:{STAFF_IDS:keyIdS,mainUser:mainUser,trackLimitTypeCode:trackLimitTypeCode},
					dataType:"json",
					success:function(data){
						if(data.result=="success"){
							top.closeMsgProgress();//关闭进度条
							
							$("#dataInfoTable").treegrid("unselectAll");
							$("#staffTable").datagrid("unselectAll");
							$("#staffTable").datagrid("reload");	
							
							$("#yiFenPeiTable").datagrid("unselectAll");
							$("#yiFenPeiTable").datagrid("reload");							
							
							$.messager.show({
								title:"系统提示",
								msg:"保存数据成功",
								timeout:3000,
								showType:"slide"
							});
						}else{
							top.closeMsgProgress();//关闭进度条
							$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
						}
					}
				});
				
				
				
			}else{
				$.messager.alert("系统提示","请选择要保存的人员数据","error");
			}
		}else{
			$.messager.alert("系统提示","请先选择被分配人员！","error");
		}
	}
	
	
	
	
	
	
	
</script>

  
</html>
