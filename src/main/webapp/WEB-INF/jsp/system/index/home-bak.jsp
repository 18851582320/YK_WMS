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
	
	<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/metro-seablue/base.css">
	<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/metro-seablue/easyui.css">
	<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="static/iconfont/iconfont.css">
	<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/metro-seablue/platform.css">
	<link rel="stylesheet" type="text/css" href="plugins/ace/css/aceMy.css" class="ace-main-stylesheet"/>
	 
	<script src="static/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>

	<script type="text/javascript" src="plugins/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="plugins/easyui/jquery.edatagrid.js"></script>
	<script type="text/javascript" src="plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="static/myJs/common.js"></script>
	
	<script type="text/javascript" src="plugins/attention/drag/drag.js"></script>
	<script type="text/javascript" src="plugins/attention/drag/dialog.js"></script>
	<link type="text/css" rel="stylesheet" href="plugins/attention/drag/style_metro-seablue.css"/>
	
	<script type="text/javascript" src="plugins/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="plugins/echarts/theme/macarons.js"></script>
	
	<style type="text/css">
		
	</style>
	
	
</head>
<body>
	<div class="main_tableDiv">
		<div id="divTop" style="height:280px; ">
			<table style="width: 100%;height: 100%;">
				<tr>
					<td style="width: 50%;height: 100%;" id="monthJieXiaoChartDiv"></td>
					<td style="width: 25%;height: 100%;" id="completeChartDiv"></td>
					<td style="width: 25%;height: 100%;" id="fenBuChartDiv"></td>
				</tr>
			</table>
		</div>
		<div style="height: 5px;" id="middleDiv"></div>
		<div id="divBottom" style="">
			<table class="easyui-treegrid" id="dataInfoTable" data-options="fit:true" toolbar="#dataInfo_bar" title="我的所有未接收和已接收未完成的任务">
				
			</table>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div  id="dataInfo_bar" style="height: 35px;padding-top: 6px;">
		&nbsp;&nbsp;<a class="btn btn-mini btn-lightBlue"  id="dataInfo-reload"><i class="iconfont icon-shuaxin6"></i>&nbsp;刷新</a>
	</div>
	
</body>


<script type="text/javascript">
	
	var performance="${performance}";
	
	function setHomeHeight(){
		var h=$(window).height();
		var w=$(window).width();
		var ht=$("#divTop").height();
		var mbt=$("#middleDiv").height();
		var marginTop=parseInt($(".main_tableDiv").css("margin-top"));
		var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
		
		
		var heightValue=Math.floor(h-ht-marginTop*2-mbt);
		
		$("#divBottom").css("height",(heightValue)+"px");
		$("#divBottom").css("width",(w-marginLeft*2)+"px");
		$("#divTop").css("width",(w-marginLeft*2)+"px");
		
		/* $("#smsInfoTableDiv").css("height",(heightValue)+"px");*/
		
	}
	
	
	//刷新任务
	function refreshData(){
		setChart();
		$("#dataInfoTable").treegrid("unselectAll");
		$("#dataInfoTable").treegrid("reload"); 
	}

	
	var STAFF_ID="${sessionScope.STAFF_ID}";
	

	$(function(){
		setHomeHeight();
		window.onresize=function(){  
			setHomeHeight();
		};
		
		
		var titleName="我的每月绩效分析";
		var legendDataName="绩效";
		if(performance=="noStartUse"){
			titleName="我的每月验收任务分析"
			legendDataName="任务数";
		}
		
		//图表
		var option = {
			    title : {
			        text: titleName,
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'axis'			      
			    },
			    legend: {
			        data:[legendDataName],
			        show:true,
			        left:20,
			        top:0,
			        align:"left",
			        orient:'horizontal'
			    },
			    grid:{
			        right:40,
			        left:40,
			        top:40,
			        bottom:50
			    },
			    calculable : true,
			    xAxis : 
			        {
			            type : 'category',
			            axisTick:{
			            	show:true,
			            	length:5,
			            },
			            data : [],
			            axisLabel:{
			            	interval:0,
			            	rotate:0
			            }
			        }
			    ,
			    yAxis : [
			        {
			            type : 'value',
			            name:"",
			            axisLabel:{
			                formatter:'{value}',
			                textStyle:{
			                    fontWeight:'bolder',			                    
			                    fontSize:13
			                }
			            }
			        }
			    ],
			    series : [
			        {
			            name:'绩效',
			            type:'bar',
			            
			            data:[],
			            barWidth:20,
			            label:{
			                normal:{
			                    show:true,
			                    position:'top',
			                    formatter:'{c}',
			                    textStyle:{
			                        
			                    }
			                }
			            }
			        }
			    ]
			};			
			
		 myChart = echarts.init(document.getElementById("monthJieXiaoChartDiv"),"macarons");
		 myChart.setOption(option);
		 
		 
		 
		 var option1 = {
			  	title : {
			        text: '我的当月任务完成率',
			        subtext: '',
			        x:'right'
			    },
				tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        x: 'left',
			        data:['已完成','未完成']
			    },
			    series: [
			        {
			            name:'任务完成率',
			            type:'pie',
			            radius: ['50%', '70%'],
			            avoidLabelOverlap: false,
			            label: {
			                normal: {
			                    show: false,
			                    position: 'center'
			                },
			                emphasis: {
			                    show: true,
			                    textStyle: {
			                        fontSize: '30',
			                        fontWeight: 'bold'
			                    }
			                }
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			               
			            ]
			        }
			    ]
			};
			
			myChart1 = echarts.init(document.getElementById('completeChartDiv'),"macarons");
			myChart1.setOption(option1);
			
			
			
			//图表
			var option2 = {
				    title : {
				        text: '我的当月任务状态分布',
				        subtext: '',
				        x:'right'
				    },
				    tooltip : {
				        trigger: 'axis'			      
				    },
				    legend: {
				        data:['任务数'],
				        show:true,
				        left:20,
				        top:0,
				        align:"left",
				        orient:'horizontal'
				    },
				    grid:{
				        right:40,
				        left:40,
				        top:40,
				        bottom:50
				    },
				    calculable : true,
				    xAxis : 
				        {
				            type : 'category',
				            axisTick:{
				            	show:true,
				            	length:5,
				            },
				            data : [],
				            axisLabel:{
				            	interval:0,
				            	rotate:-40
				            }
				        }
				    ,
				    yAxis : [
				        {
				            type : 'value',
				            name:"",
				            axisLabel:{
				                formatter:'{value}',
				                textStyle:{
				                    fontWeight:'bolder',			                    
				                    fontSize:13
				                }
				            }
				        }
				    ],
				    series : [
				        {
				            name:'任务数',
				            type:'bar',
				            
				            data:[],
				            barWidth:20,
				            label:{
				                normal:{
				                    show:true,
				                    position:'top',
				                    formatter:'{c}',
				                    textStyle:{
				                        
				                    }
				                }
				            }
				        }
				    ]
				};			
				
		 myChart2 = echarts.init(document.getElementById("fenBuChartDiv"),"macarons");
		 myChart2.setOption(option2);
		 
		 
		
		 setChart();
		 
		//加载数据
		$("#dataInfoTable").treegrid({  
	        autoRowHeight:false,  
	        loadMsg:"数据加载中，请稍后......",
	        lines:false,
	        pagination: true,
	        pageSize: 100,
	        rownumbers: true,
	        singleSelect:false,
	        idField:"taskId",
	        treeField:"taskName",
	        fitColumns:false,
	        pageList:[10,20,30,50,100,300],
	        url:"<%=basePath%>tkTask/myHomeQuerylistPage.do",
	        queryParams:{
	        	"taskReveiveStaffId":STAFF_ID,
	        },
	        frozenColumns:[[  
	            {field:"taskId",checkbox:true},
	        ]],    
	        columns:[[ 
	        	/* {title:"操作",field:"Operation",align:"center",width:100,
	        		formatter:function(value,row){
        				var editHtml="";
	        			if(row.taskReveiveState==1 && row.taskCompleteState==0 && row.taskCheckState==0){
	        				editHtml+="&nbsp;<a  href=\"javascript:proSub('"+row.taskId+"');\" class=\"btn btn-mini btn-info\" title=\"进度提交\"><i class=\"iconfont icon-tijiao3\"></i>&nbsp;进度提交</a>";	
	        			}
        				return editHtml;
	        		}	
	        	}, */
	        	{title:"任务名称",field:"taskName",align:"left",width:350,sortable:false},
	        	{title:"任务编码",field:"taskCode",align:"left",width:120,sortable:false},
	        	{title:"任务状态",field:"taskState",align:"center",width:100,sortable:false,
	        		formatter:function(value,row){
	        			if(row.taskSendState==0){
	        				return "未下达";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==0){
	        				return "已下达未接收";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==0){
	        				return "已接收未完成";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==1 && row.taskCheckState==0){
	        				return "已完成未验收";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==1 && row.taskCheckState==1 && row.taskConfirmState==0){
	        				return "已验收未审核";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==1 && row.taskCheckState==1 && row.taskConfirmState==1){
	        				return "已审核";	
	        			}
	        		},
	        		styler: function(value,row,index){
	        			if(row.taskSendState==0){
	        				return "background-color:#a0a0a0;color:#ffffff";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==0){
	        				return "background-color:#6fb3e0;color:#ffffff";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==0){
	        				return "background-color:#f45438;color:#ffffff";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==1 && row.taskCheckState==0){
	        				return "background-color:#f29503;color:#ffffff";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==1 && row.taskCheckState==1 && row.taskConfirmState==0){
	        				return "background-color:#66CD00;color:#ffffff";	
	        			}
	        			if(row.taskSendState==1 && row.taskReveiveState==1 && row.taskCompleteState==1 && row.taskCheckState==1 && row.taskConfirmState==1){
	        				return "background-color:#1da02b;color:#ffffff";	
	        			}
   	        		}
	        	},
	        	{title:"计划开始时间",field:"planStartTime",align:"center",width:100,sortable:false,
	        		formatter:function(value,row){
	        			return formatDate(value,"2");
	        		}
	        	}, 
	        	{title:"计划完成时间",field:"planEndTime",align:"center",width:100,sortable:false,
	        		formatter:function(value,row){
	        			return formatDate(value,"2");
	        		}
	        	},
	        	{title:"任务描述",field:"taskDescribe",align:"center",width:100,sortable:false,
	        		formatter:function(value,row){
	        			return "<span  title='"+value+"'  >"+value+"</span>";
	        		}
	        	},
	        	{title:"预估工时(天)",field:"predictTimes",align:"center",width:80,sortable:false,
	        		formatter:function(value,row){
	        			if(!isEmpty(value))
	        	            return parseFloat(value).toFixed(1);
	        		}
	        	}, 
	        	{title:"创建人",field:"createUserName",align:"center",width:100,sortable:false}, 
	        ]],
	        onClickRow:function(rowData){
	         	$("#dataInfoTable").treegrid("unselectAll");
	         	$("#dataInfoTable").treegrid("select",rowData.taskId);
	        },
	        onLoadSuccess:function(data){
	       		//不启用绩效管理，不显示工时
	       		<c:if test="${performance eq 'noStartUse'}">
	       			$("#dataInfoTable").datagrid("hideColumn","predictTimes");
	       		</c:if>
		    }
	    });
		
		
		//刷新
		$("#dataInfo-reload").click(function(){
			$("#dataInfoTable").treegrid("unselectAll");
			$("#dataInfoTable").treegrid("reload");
		});
		
		
	});
	
	//设置图表
	function setChart(){
		
		var titleName="绩效";
		if(performance=="noStartUse"){
			titleName="任务数"
		}
		
		$.ajax({
			url:"<%=basePath%>tkTask/monthJieXiao.do",
			type:"post",
			async:false,
			data:{},
			dataType:"json",
			success:function(data){
				if(data){
					
					myChart.setOption({
				        xAxis: {
				            data: data.name
				        },
				        series: [
				            {
					            name:titleName,
					            data:data.jiXiaoData
					        }
				        ]
				    });
					
					
					myChart1.setOption({
						series: [
					        {
					            data:[
					                {value:data.hasComplete, name:'已完成'},
					                {value:data.noComplete, name:'未完成'},
					            ]
					        }
					    ]
				    });
					
					
					myChart2.setOption({
				        xAxis: {
				        	data : data.fenBuName
				        },
				        series: [
				            {
				            	name:'任务数',
					            data:data.fenBuNumData,
					        }
				        ]
				    });
					
					
				}
			}
		});
	}
	

	
</script>

  
</html>
