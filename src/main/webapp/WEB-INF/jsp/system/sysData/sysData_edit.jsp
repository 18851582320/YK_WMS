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
	<div class="addEditDiv" style="background-color:#fafafa;">
		<div class="">
			<form action="sysData/${method}.do" name="dataForm" id="dataForm" method="post">
				<input type="hidden" name="sysDataId" id="sysDataId" value="${pd.sysDataId}"/>
				<input type="hidden" name=insertedStr id="insertedStr" value=""/>
				<input type="hidden" name="updatedStr" id="updatedStr" value=""/>
				<input type="hidden" name="deletedStr" id="deletedStr" value=""/>
				<div id="saveEditCenter" class="saveEditCenter" style="height:510px;">
					<table>
						<tr class="editMiddleTr" style="height: 5px;"></tr>
						<tr class="editTr" style="">
							<td class="editTdTitle">索引:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="sysDataIndex"  name="sysDataIndex" editable="true" value="${pd.sysDataIndex}" prompt="" data-options="required:true" >
							</td>
							<td class="editTdMid"></td>
							<td class="editTdTitle">表名称:&nbsp;</td>
							<td class="editTdValue">
								<input class="easyui-textbox" type="text" id="sysDataTableName"  name="sysDataTableName" editable="true" value="${pd.sysDataTableName}" prompt="" data-options="required:true" >
							</td>
						</tr>
						<tr class="editMiddleTr" style="height: 5px;"></tr>
						<tr style="height: 30px">
						 	<td class="editTdTitle">备注:&nbsp;</td>
						 	<td class="editTdValue" colspan="8">
						 		<input class="easyui-textbox" type="text" id="sysDataDescribe" name="sysDataDescribe" value="${pd.sysDataDescribe}" style="width: 478px;height:45px"  prompt="" data-options="required:true,multiline:true" >
						 	</td>
						</tr>
					</table>
					
					<div class="addEditDatdgrid" style="height: 390px;margin-left: 5px;margin-right: 5px;margin-top: 20px;">
						<table class="easyui-datagrid" id="addEditDataGrid" data-options="fit:true" toolbar="#addEdit_bar">
						</table>
					</div>
				</div>
				
				<!-- 工具栏 -->
				<div  id="addEdit_bar" style="height: 30px;padding-top: 5px;">
					<a href="javascript:void(0)" class="btn btn-mini btn-success" id="batchAddRow" onclick="addRow()"><i class="iconfont icon-tianjia"></i>&nbsp;增加行</a>
					&nbsp;
					<a class="btn btn-mini btn-danger" id="removeRow" onclick="removeRow()"><i class="iconfont icon-shanchu9"></i>&nbsp;删除行</a>
				</div>
				<div class="addEditButtonDiv">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="iconfont icon-baocun8"></i>&nbsp;保存</a>
					&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><i class="iconfont icon-guanbi"></i>&nbsp;取消</a>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	var lastIndex;
	var fieldV;
	var indexV;
	var sysDataIndex = $("#sysDataIndex").val();
	
	
	$(function(){
		
		setAddEditDivHeight();
		
		$("#addEditDataGrid").datagrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        remoteSort: false,  
	        pagination: false,
	        rownumbers: true,
	        fitColumns:false,
	        url:'<%=basePath%>sysDataDetail/listAllByKeyId.do',
	        queryParams:{sysDataIndex:sysDataIndex},
	        frozenColumns:[[  
	            {field:"sysDataDeId",checkbox:true},
	        ]],  
	        columns:[[  
	        	{title:"索引",field:"sysDataIndex",align:"center",width:100,hidden:true},
	        	{title:"顺序号",field:"sysDataDeFieldOrder",align:"center",width:100,
	        		editor:{
						type:"numberbox",  
						options:{
							required:true,
							precision:0,
							min:1
						}
					}	
	        	},
	        	{title:"实际查询名称",field:"queryFieldName",align:"center",width:150,
	        		editor:{
						type:"textbox",
						options:{
							required:true
						}
					}	
	        	},
	        	{title:"字段编码",field:"sysDataDeFieldCode",align:"center",width:150,
	        		editor:{
						type:"textbox",
						options:{
							required:true
						}
					}	
	        	},
	        	{title:"字段名称",field:"sysDataDeFieldName",align:"center",width:150,
	        		editor:{
						type:"textbox",
						options:{
							required:true
						}
					}	
	        	},
	        	{title:"字段类型",field:"sysDataDeFieldType",align:"center",width:120,
	        		editor:{
    					type:"combobox",
    					options:{
    						data:[
    							{value:"nvarchar",text:"nvarchar"},
    							{value:"int",text:"int"},
    							{value:"decimal",text:"decimal"},
    							{value:"datetime",text:"datetime"},
    							{value:"date",text:"date"},
    							{value:"booleanVarchar",text:"booleanVarchar"},
    							{value:"boolean",text:"boolean"},
    							{value:"specialSelect",text:"specialSelect"}
    						],
    						required:true,
    						editable:false,
    						panelHeight:"auto"
    					}
    				}
	        	},
	        	{title:"是否作为查询条件",field:"asQueryCondition",align:"center",width:110,
	        		editor:{
    					type:"combobox",
    					options:{
    						data:[
    							{value:"是",text:"是"},
    							{value:"否",text:"否"}
    						],
    						required:true,
    						editable:false,
    						panelHeight:"auto"
    					}
    				}
	        	},
	        	{title:"不作查询,nvarchar是否精确",field:"isJingQueQuery",align:"center",width:160,
	        		editor:{
    					type:"combobox",
    					options:{
    						data:[
    							{value:"否",text:"否"},
    							{value:"是",text:"是"}
    						],
    						required:true,
    						editable:false,
    						panelHeight:"auto"
    					}
    				}
	        	},
	        	{title:"字段备注",field:"sysDataDeFieldMemo",align:"center",width:200,
					editor:{
						type:"textbox",
						options:{
							required:false
						}
					}	
	        	}
	        ]],
	        onBeforeLoad:function(rowIndex){
	        	$(this).datagrid("rejectChanges");//回滚所有从创建或者上一次调用acceptChanges函数后更改的数据
	        },
	        onClickRow : function(rowIndex) {
				//取消选中当前页所有的行
	         	$("#addEditDataGrid").datagrid("unselectAll");
	         	//选中一行，行索引从 0 开始
	         	$("#addEditDataGrid").datagrid("selectRow",rowIndex);
	         	if(!isEmpty(lastIndex)){
	         		$("#addEditDataGrid").datagrid("endEdit", lastIndex);
	         	}
	         	$("#addEditDataGrid").datagrid("beginEdit", rowIndex);
				lastIndex = rowIndex;
			},
			onUnselect:function(rowIndex, rowData){
				if(!$("#addEditDataGrid").datagrid("validateRow",rowIndex)){
					$("#addEditDataGrid").datagrid("refreshRow",rowIndex);
				}
				$("#addEditDataGrid").datagrid("endEdit", rowIndex);
			},
			onClickCell: function(index,field,value){
				indexV=index;
				fieldV=field;
			}
	    });

	});
	
	
	function addRow(){
		$("#addEditDataGrid").datagrid("appendRow",{
			sysDataDeFieldOrder: "",
			queryFieldName: "",
			sysDataDeFieldCode: "",
			sysDataDeFieldName: "",
			sysDataDeFieldType: "",
			sysDataDeFieldMemo: ""
		});
		
		lastIndex = $("#addEditDataGrid").datagrid("getRows").length- 1;
		$("#addEditDataGrid").datagrid("selectRow", lastIndex);
		$("#addEditDataGrid").datagrid("beginEdit", lastIndex);
		
	}
	

	//删除行
	function removeRow(){
		var rows = $("#addEditDataGrid").datagrid("getSelections");
		if (rows.length>0) {
			$.messager.confirm("提示","确认删除选中的"+rows.length+"条记录吗？",function(r){
				if(r){
					
					$("#addEditDataGrid").datagrid("endEditAll");
					for (var i = 0; i < rows.length; i++) {
						var index = $("#addEditDataGrid").datagrid("getRowIndex", rows[i]);
						$("#addEditDataGrid").datagrid("deleteRow", index);
					}
				}
			});
		}else{
			$.messager.alert("系统提示","请选择要删除的数据!'");
		}
	}
	
	//保存
	function save(){
		top.showMsgProgress(); //显示进度条
		$("#dataForm").form("submit",{
			onSubmit: function(){
				var isValid = $(this).form("validate");
				if (!isValid){
					top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
				}
				
				if(isValid){
					var rows = $("#addEditDataGrid").datagrid("getRows");
					if(rows.length<1){
						top.closeMsgProgress(); //如果表单是无效的的则关闭进度条
						$.messager.alert("提示","表体不能为空！","error");
						isValid=false;
					}
					
				}
				
				if(isValid){
					$("#addEditDataGrid").datagrid("endEditAll");
					
					var inserted=$("#addEditDataGrid").datagrid("getChanges","inserted");
					var deleted=$("#addEditDataGrid").datagrid("getChanges","deleted");
					var updated=$("#addEditDataGrid").datagrid("getChanges","updated");
					
					inserted=JSON.stringify(inserted);
					deleted=JSON.stringify(deleted);
					updated=JSON.stringify(updated);
					
					dataForm.insertedStr.value=inserted;
					dataForm.updatedStr.value=updated;
					dataForm.deletedStr.value=deleted;
					
				}
				return isValid;//返回false终止表单提交
			},
			success:function(data){
				top.closeMsgProgress();//关闭进度条
				var data = eval("("+data+")"); 
				if("success"==data.result){
					$("#saveEditCenter").hide();
					top.Dialog.close();
				}else if("codeRepeat"==data.result){
					$.messager.alert("系统提示","索引重复，请重新输入！","error");
				}
				else{
					$.messager.alert("系统提示","保存出错:"+data.errmsg,"error");
				}
			}
		});
		
	}
</script>

</html>
