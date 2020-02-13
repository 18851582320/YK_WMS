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
				<table style="width:100%;height: 100%;" border="0">
					<tr>
						<td style="width:730px;" id="departmentFrameId">
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
				&nbsp;<input class="easyui-textbox" style="width: 150px;height: 26px;" id="condition" name="condition" value="" prompt="请输入查询条件"/>
				&nbsp;<a class="btn btn-mini btn-dark " id="selectTable-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
			</div>
			
			<div class="addEditButtonDiv">
				<a class="btn btn-mini btn-primary" onclick="saveSelect();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
				&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="closeSelectDialog();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

$(function(){
	
	/* setAddEditDivHeight(); */
	
	
	//加载数据
	$("#selectTable").datagrid({  
        autoRowHeight:false,  
        loadMsg:"数据加载中，请稍后......",
        sortName: "NAME",  
        sortOrder: "desc",
        multiSort:true,
        remoteSort: true, 
        singleSelect: ${singleSelect},  
        pagination: true,
        pageSize: 300,
        rownumbers: true,
        fitColumns:false,
        pageList:[10,20,30,50,100,300],
        url:'<%=basePath%>staff/listPageSelect.do?',     
        frozenColumns:[[  
            {field:"STAFF_ID",checkbox:true},
        ]],  
        columns:[[  
          		{title:"员工编码",field:"BIANMA",align:"center",width:120,sortable:true},
          		{title:"用户名",field:"USERNAME",align:"center",width:120,sortable:true}, 
	        	{title:"姓名",field:"NAME",align:"center",width:120,sortable:true}, 
	        	{title:"性别",field:"SEX",align:"center",width:100,sortable:true}, 
	        	{title:"部门",field:"DEPARTMENT_NAME",align:"center",width:120,sortable:true} 
        ]]
    });
	
	
	//查询
	$("#selectTable-query").click(function(){
		var condition = $("#condition").val();
		$("#selectTable").datagrid("options").url = "<%=basePath%>staff/listPageSelect.do?";
		$("#selectTable").datagrid("load",{"keywords":condition});
		
	});
	
	
});

/**
 * 保存选择的数据
 */
function saveSelect(){
	var row=$("#selectTable").datagrid("getSelections");
	if(row.length>0){
		if (window.opener){
			window.close();
			window.opener.test("ceshi");
		}
		
	}else{
		$.messager.alert("系统提示","请至少选择一条数据","error");
	}
}


/**
 *关闭窗口 
 */
function closeSelectDialog(){
	window.close();
}

	
</script>

</html>
