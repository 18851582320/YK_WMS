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
<body class="addEditDivBody">
	<div class="addEditDiv">
		<div class="">
			<div id="saveEditCenter" class="saveEditCenter" style="height: 460px;">
				<table style="width:100%;height: 100%;" border="0" id="resourceTable">
					<tr>
						<td style="width:730px;" id="teamFrameId">
							<div style="height: 450px;margin: 5px;">
								<table class="easyui-datagrid" id="selectTable" data-options="fit:true" toolbar="#selectTable_bar">
									
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!-- 工具栏 -->
			<div  id="selectTable_bar" class="dataGridToolBar">
				<shiro:hasPermission name="PLAN.WORKSHOP.SEARCH">
					&nbsp;<input class="easyui-textbox" style="width: 150px;height: 26px;" id="condition" name="condition" value="" prompt="请输入查询条件"/>
					&nbsp;<a class="btn btn-mini btn-dark " id="selectTable-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
				</shiro:hasPermission>
			</div>
			
			<div class="addEditButtonDiv">
				<a class="btn btn-mini btn-primary" onclick="saveSelect();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
				&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="closeSelectDialog();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

var PARENT_ID="";
var rtn;

$(function(){
	//加载数据
	$("#selectTable").datagrid({  
        autoRowHeight:false,  
        loadMsg:"数据加载中，请稍后......",
        sortName: "teamId",  
        sortOrder: "desc",
        multiSort:true,
        remoteSort: true,
        singleSelect: ${singleSelect},  
        pagination: true,
        pageSize: 10,
        rownumbers: true,
        fitColumns:false,
        pageList:[10,20,30,50,100,300],
        url:'<%=basePath%>team/listPage.do?PARENT_ID='+PARENT_ID,     
        frozenColumns:[[  
            {field:"teamId",checkbox:true},
        ]],  
        columns:[[  
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
         	$("#selectTable").datagrid("unselectAll");
         	//选中一行，行索引从 0 开始
         	$("#selectTable").datagrid("selectRow",rowIndex);
         }

    });
	
	
	//查询
	$("#selectTable-query").click(function(){
		var condition = $("#condition").val();
		
		$("#selectTable").datagrid("options").url = "<%=basePath%>team/listPage.do?";
		$("#selectTable").datagrid("load",{"keywords":condition});
		
	});
	
	
	
});


/**
 * 保存选择的数据
 */
function saveSelect(){
	var row=$("#selectTable").datagrid("getSelections");
	if(row.length>0){
		rtn=row;
		$("#saveEditCenter").hide();
		
		var winType="${winType}";
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
		
	}else{
		$.messager.alert("系统提示","请至少选择一条数据","error");
	}
}

/**
 * 返回数据
 */
function getSelect(){
	if(rtn.length>0){
		return rtn;
	}else{
		return null;
	}
}


/**
 *关闭窗口 
 */
function closeSelectDialog(){
	var winType="${winType}";
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
	
</script>

</html>
