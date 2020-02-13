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
						<td style="width:730px;" id="resourceFrameId">
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
				<shiro:hasPermission name="OA.WFLISTENER.SEARCH">
					&nbsp;<input class="easyui-textbox" style="width: 150px;height: 26px;" id="condition" name="condition" value="" prompt="请输入查询条件"/>
					&nbsp;<a class="btn btn-mini btn-dark " id="selectTable-query" ><i class="iconfont icon-chaxun5"></i>&nbsp;查询</a>
				</shiro:hasPermission>
			</div>
			
			<div class="addEditButtonDiv">
				<a class="btn btn-mini btn-primary" onclick="saveSelect();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
				&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="closeSelectDialog()"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

var rtn;

$(function(){
	
	var type="${type}";
	
	//加载数据
	$("#selectTable").datagrid({  
        autoRowHeight:false,  
        loadMsg:"数据加载中，请稍后......",
        multiSort:true,
        remoteSort: true, 
        singleSelect: ${singleSelect},  
        pagination: true,
        pageSize: 10,
        rownumbers: true,
        fitColumns:false,
        pageList:[10,20,30,50,100,300],
        url:'<%=basePath%>workFlowListener/listPage.do?type='+type,     
        frozenColumns:[[  
            {field:"wfListenerId",checkbox:true},
        ]],  
        columns:[[ 
        	{title:"类型",field:"wfListenerType",align:"center",width:160,sortable:true,
				formatter:function(value,row){
					//有修改权限
					if(value==1){
						return "执行监听器";
					}else{
						return "用户任务监听器";
					}
				}
        	}, 	        	
        	{title:"流程监控器名称",field:"wfListenerName",align:"center",width:160,sortable:true},
        	{title:"事件",field:"wfListenerEvent",align:"center",width:160,sortable:true},
        	{title:"执行类型",field:"wfListenerValueType",align:"center",width:160,sortable:true,
				formatter:function(value,row){
					//有修改权限
					if(value==1){
						return "Java Class";
					}else{
						return "expression";
					}
				}
        	},
        	{title:"执行内容",field:"wfListenerValue",align:"center",width:200,sortable:true},
        	{title:"流程监控器备注",field:"wfListenerMemo",align:"center",width:200,sortable:true} 
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
		
		$("#selectTable").datagrid("options").url = '<%=basePath%>workFlowListener/listPage.do?type='+type;
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
