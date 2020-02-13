
//为所有的datagrid添加表头右键菜单
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderMenu;


//datagrid在加载前取消选中
$.fn.datagrid.defaults.onBeforeLoad = function(){$(this).datagrid("unselectAll")};


var webroot = window.location.pathname.substring(window.location.pathname.indexOf("\/")+1).substring(0, window.location.pathname.substring(window.location.pathname.indexOf("\/")+1).indexOf("\/"));
var MES_BASE = window.location.protocol+"\/\/" + window.location.host + "\/" + webroot;


var winH=window.screen.height;
var winW= window.screen.width;


/**
 * 创建datagrid表头的右键菜单，实现列的可隐藏
 */
function createGridHeaderMenu(e, field) {
	e.preventDefault();//阻止浏览器捕获右键事件
	var grid = $(this);
	if (!$("#tMenu").length) {
		var tmenu = $('<div id="tMenu" style="width:100px;"></div>').appendTo('body');  
		var fields = grid.datagrid('getColumnFields',false);
		for ( var i = 0; i < fields.length; i++) {
			var field_option = grid.datagrid("getColumnOption", fields[i]);
			var title = field_option.title;
			$('<div iconCls="icon-ok" field="'+fields[i]+'" />').html(title).appendTo(tmenu); 
		}
		tmenu.menu({
			onClick : function(item) {
				var l_field = $(item.target).attr("field");
				if (item.iconCls == "icon-ok") {
					grid.datagrid("hideColumn", l_field);
					tmenu.menu('setIcon', {  
                        target: item.target,  
                        iconCls: 'icon-empty'  
                    });  
				} else {
					grid.datagrid("showColumn", l_field);
					tmenu.menu("setIcon", {
						target : item.target,
						iconCls : "icon-ok"
					});
				}
			}
		});
	}
	$("#tMenu").menu("show", {
		 left:e.pageX,  
         top:e.pageY  
	});
};

//动态设定datagrid的高度
function setDataGridHeight(){
	var h=$(window).height();
	var w=$(window).width();
	var marginTop=parseInt($(".main_tableDiv").css("margin-top"));
	var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
	$("#dataGridDiv").css("height",(h-marginTop*2)+"px");
	$("#dataGridDiv").css("width",(w-marginLeft*2)+"px");
}

//动态设定带图表的datagrid的高度
function setChartDataGridHeight(){
	var w=$(window).width();
	var marginLeft=parseInt($(".main_tableDiv").css("margin-left"));
	$("#dataGridDiv").css("width",(w-marginLeft*2)+"px");
}


function settableDivEditHeight(){
	var h=$(window).height();
	var w=$(window).width();
	var marginTop=parseInt($(".main_tableDivEdit").css("margin-top"));
	var marginBottom=parseInt($(".main_tableDivEdit").css("margin-bottom"));
	var marginleft=parseInt($(".main_tableDivEdit").css("margin-left"));
	var marginright=parseInt($(".main_tableDivEdit").css("margin-right"));
	var addEditButtonDivHeight=parseInt($(".addEditButtonDiv").height())+11;
	$("#saveEditCenter").css("height",(h-marginTop-marginBottom-addEditButtonDivHeight)+"px");
}

//dataGrid title
function showTip(){
	 $(".cell_content").tooltip({
        position:"top",
        onShow: function(){
            $(this).tooltip("tip").css({
                backgroundColor: "#FFFBCD",
                borderColor: "#AB9F6B",
                boxShadow: "1px 1px 3px #292929"
            });
        },
        onPosition: function(){
            $(this).tooltip("tip").css("left", $(this).offset().left);
            $(this).tooltip("arrow").css("left", 20);
        }
    });
}

//格式化日期方法  
function formatDate(value,type) {  
	if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {
    	//ie下 date有时不兼容
    	if(value.indexOf("-") != -1 ){
    		value=value.replace(/-/g,"/");
    		if(value.indexOf(".") != -1 ){
    			value.lastInde
    			value=value.substring(0,value.lastIndexOf("."));
    		}
    	}
        dt = new Date(value);
    }  
  
    if(type=="1"){
    	return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)  
    }else if(type=="2"){
    	return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)  
    }else if(type=="3"){
    	return dt.format("yyyy-MM-dd hh:mm"); //扩展的Date的format方法(上述插件实现)  
    }
    return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)  
}  


function formatProgress(value){
	if (!isEmpty(value)){
    	var rtnStr="";
    	if(parseFloat(value)>=30){
    		if(parseFloat(value)<=70){
    			rtnStr = '<div style="width:100%;">' +
    			'<div style="width:' + value + '%;background:#1da02b;color:#fff">' + value + '%' + '</div>'
    			'</div>';
    		}else{
    			rtnStr = '<div style="width:100%;">' +
    			'<div style="width:' + value + '%;background:#1da02b;color:#fff">' + value + '%' + '</div>'
    			'</div>';
    		}
    	}else{
    		rtnStr = '<div style="width:100%;">' +
			'<div style="width:' + value + '%;background:#1da02b;color:#fff" title="'+value+'%">&nbsp;</div>'+
			'</div>';
    	}
    	return rtnStr;
	} else {
    	return '';
	}
}


//获取当前时间
function getCurentTime()  
{   
    var now = new Date();  
      
    var year = now.getFullYear();       //年  
    var month = now.getMonth() + 1;     //月  
    var day = now.getDate();            //日  
      
    var hh = now.getHours();            //时  
    var mm = now.getMinutes();          //分  
    var ss = now.getSeconds();           //秒  
      
    var clock = year + "-";  
      
    if(month < 10)  
        clock += "0";  
      
    clock += month + "-";  
      
    if(day < 10)  
        clock += "0";  
          
    clock += day + " ";  
      
    if(hh < 10)  
        clock += "0";  
          
    clock += hh + ":";  
    if (mm < 10) clock += '0';   
    clock += mm + ":";   
       
    if (ss < 10) clock += '0';   
    clock += ss;   
    return(clock);   
}  



//动态设定添加修改页面中间区域的高度
function setAddEditDivHeight(){
	var h=$(window).height()-4;
	var marginTop=parseInt($(".addEditDiv").css("margin-top"));
	var marginBottom=parseInt($(".addEditDiv").css("margin-bottom"));
	var addEditButtonDivHeight=parseInt($(".addEditButtonDiv").height())+6;
	$("#saveEditCenter").css("height",(h-marginTop-marginBottom-addEditButtonDivHeight)+"px");
}

//动态设定添加修改页面中间区域的高度（没有按钮）
function setDivHeightNoBtn(){
	var h=$(window).height();
	var marginTop=parseInt($(".addEditDivNoBtn").css("margin-top"));
	var marginBottom=parseInt($(".addEditDivNoBtn").css("margin-bottom"));
	$("#saveEditCenter").css("height",(h-marginTop-marginBottom)+"px");
}


//动态设定详细页面的高度
function setDetailDivHeight(){
	var h=$(window).height()-4;
	var marginTop=parseInt($(".detailDiv").css("margin-top"));
	var marginBottom=parseInt($(".detailDiv").css("margin-bottom"));
	var detailMarginTop=parseInt($(".detailDataGridDiv").css("margin-top"));
	var detailMarginBottom=parseInt($(".detailDataGridDiv").css("margin-bottom"));
	var addEditButtonDivHeight=parseInt($(".detailButtonDiv").height())+6;
	$("#detailDataGridDiv").css("height",(h-marginTop-marginBottom-addEditButtonDivHeight-detailMarginTop-detailMarginBottom)+"px");
}


/**
 * 通过设置的编码规则获取表单字段的流水号
 * @param {} tableName 表名
 * @param {} fieldName 字段名称
 * @param {} formFieldId jsp页面form中字段的id
 * @param {} formCodeRuleTypeId jsp页面form中存储编码规则类型的流水号
 */
function getFormCodeByRule(tableName,fieldName,formFieldId,formCodeRuleTypeId){
	$.ajax({
		url:"codeRule/formCode.do",
		type:"post",
		async:false,
		data:{tableName:tableName,fieldName:fieldName},
		dataType:"json",
		success:function(data){
			if(data.result=="success"){
				if(data.codeRuleType=="1"){//自己输入
					$("#"+formCodeRuleTypeId).val(data.codeRuleType);
				}else{
					$("#"+formFieldId).textbox("setValue", data.code);
					$("#"+formCodeRuleTypeId).textbox("setValue", data.codeRuleType);
					
					/*
					$("#"+formFieldId).textbox({
						editable: false
					});
					*/
				}
			}
		}
	});
}

/**选择设备厂商
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectMf(obj,obj_id,callback,selType,queryCon,winType){
	if(!queryCon)queryCon="";
	
	 var diag;
	 if(winType=="top"){
		 diag=new top.Dialog();
	 }else if(winType=="parent"){
		 diag=new Dialog();
	 }else{
		 diag=new top.Dialog();
	 }
	 
	 
	 diag.Drag=true;
	 diag.Title ="设备厂商选择";
	 diag.URL = $("base[href]").prop("href")+'manufacturer/selectMf?selType='+selType+queryCon+'&winType='+winType;
	 diag.Width = 1000;
	 diag.Height = 500;
	 diag.CancelEvent=function(){
		 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 var select = diag.innerFrame.contentWindow.getSelect();
			 if(select.length > 0){
				 if(callback){
					callback(select);
				 }
			 }
		 }
		 diag.close();
	 };		
	 diag.show();
}

/**
 * 设备选择
 * @param obj
 * @param obj_id
 * @param callback
 * @param selType
 * @param queryCon
 * @param winType
 * @returns
 */
function selectMacName(obj,obj_id,callback,selType,queryCon,winType){
	if(!queryCon)queryCon="";
	 var diag;
	 if(winType=="top"){
		 diag=new top.Dialog();
	 }else if(winType=="parent"){
		 diag=new Dialog();
	 }else{
		 diag=new top.Dialog();
	 }
	 diag.Drag=true;
	 diag.Title ="设备选择";
	 diag.URL = $("base[href]").prop("href")+'macInfo/selectMacName?selType='+selType+queryCon+'&winType='+winType;
	 diag.Width = 1000;
	 diag.Height = 500;
	 diag.CancelEvent=function(){
		 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 var select = diag.innerFrame.contentWindow.getSelect();
			 if(select.length > 0){
				 if(callback){
					callback(select);
				 }
			 }
		 }
		 diag.close();
	 };		
	 diag.show();
}

/**
 * 车间选择
 * @param obj
 * @param obj_id
 * @param callback
 * @param selType
 * @param queryCon
 * @param winType
 * @returns
 */
function selectWsName(obj,obj_id,callback,selType,queryCon,winType){
	if(!queryCon)queryCon="";
	 var diag;
	 if(winType=="top"){
		 diag=new top.Dialog();
	 }else if(winType=="parent"){
		 diag=new Dialog();
	 }else{
		 diag=new top.Dialog();
	 }
	 diag.Drag=true;
	 diag.Title ="车间选择";
	 diag.URL = $("base[href]").prop("href")+'workshop/selectWsName?selType='+selType+queryCon+'&winType='+winType;
	 diag.Width = 1000;
	 diag.Height = 500;
	 diag.CancelEvent=function(){
		 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 var select = diag.innerFrame.contentWindow.getSelect();
			 if(select.length > 0){
				 if(callback){
					callback(select);
				 }
			 }
		 }
		 diag.close();
	 };		
	 diag.show();
}

/**
 * 产线选择
 * @param obj
 * @param obj_id
 * @param callback
 * @param selType
 * @param queryCon
 * @param winType
 * @returns
 */
function selectPlName(obj,obj_id,callback,selType,queryCon,winType){
	if(!queryCon)queryCon="";
	 var diag;
	 if(winType=="top"){
		 diag=new top.Dialog();
	 }else if(winType=="parent"){
		 diag=new Dialog();
	 }else{
		 diag=new top.Dialog();
	 }
	 diag.Drag=true;
	 diag.Title ="产线选择";
	 diag.URL = $("base[href]").prop("href")+'produceLine/selectPlName?selType='+selType+queryCon+'&winType='+winType;
	 diag.Width = 1000;
	 diag.Height = 500;
	 diag.CancelEvent=function(){
		 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 var select = diag.innerFrame.contentWindow.getSelect();
			 if(select.length > 0){
				 if(callback){
					callback(select);
				 }
			 }
		 }
		 diag.close();
	 };		
	 diag.show();
}

/**
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectDepartment(obj,obj_id,callback,selType,queryCon,winType){
	 
	if(!queryCon)queryCon="";
	
	 var diag;
	 if(winType=="top"){
		 diag=new top.Dialog();
	 }else if(winType=="parent"){
		 diag=new Dialog();
	 }else{
		 diag=new top.Dialog();
	 } 
	
	 diag.Drag=true;
	 diag.Title ="部门选择";
	 diag.URL = $("base[href]").prop("href")+'department/selectDepartment.do?selType='+selType+queryCon+'&winType='+winType;
	 diag.Width = 1000;
	 diag.Height =500;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 var select = diag.innerFrame.contentWindow.getSelect();	
			 if(select.length > 0){
				 if(callback){
					callback(select);
				 }
			 }
		 }
		 diag.close();
	 };
	 diag.show();
}



/**选择里程碑模板
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectStageTemplate(obj,obj_id,callback,selType,queryCon,winType){
	
	if(!queryCon)queryCon="";
	
	var diag;
	if(winType=="top"){
		diag=new top.Dialog();
	}else if(winType=="parent"){
		diag=new Dialog();
	}else{
		diag=new top.Dialog();
	}
	
	
	diag.Drag=true;
	diag.Title ="里程碑模板选择";
	diag.URL = $("base[href]").prop("href")+'stageTemplate/selectStageTemplate.do?selType='+selType+queryCon+'&winType='+winType;
	diag.Width = 1000;
	diag.Height = 400;
	diag.CancelEvent=function(){
		if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		{
			var select = diag.innerFrame.contentWindow.getSelect();
			if(select.length > 0){
				if(callback){
					callback(select);
				}
			}
		}
		diag.close();
	};		
	diag.show();
}


/**选择合同信息
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectContract(obj,obj_id,callback,selType,queryCon,winType){
	
	if(!queryCon)queryCon="";
	
	var diag;
	if(winType=="top"){
		diag=new top.Dialog();
	}else if(winType=="parent"){
		diag=new Dialog();
	}else{
		diag=new top.Dialog();
	}
	
	
	diag.Drag=true;
	diag.Title ="合同信息选择";
	diag.URL = $("base[href]").prop("href")+'contract/selectContract.do?selType='+selType+queryCon+'&winType='+winType;
	diag.Width = 1000;
	diag.Height = 400;
	diag.CancelEvent=function(){
		if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		{
			var select = diag.innerFrame.contentWindow.getSelect();
			if(select.length > 0){
				if(callback){
					callback(select);
				}
			}
		}
		diag.close();
	};		
	diag.show();
}





/**选择任务
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectTask(obj,obj_id,callback,selType,queryCon,winType){
	
	if(!queryCon)queryCon="";
	
	var diag;
	if(winType=="top"){
		diag=new top.Dialog();
	}else if(winType=="parent"){
		diag=new Dialog();
	}else{
		diag=new top.Dialog();
	}
	
	
	diag.Drag=true;
	diag.Title ="上级任务";
	diag.URL = $("base[href]").prop("href")+'tkTask/selectTask.do?selType='+selType+queryCon+'&winType='+winType;
	diag.Width = 1000;
	diag.Height = 400;
	diag.CancelEvent=function(){
		if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		{
			var select = diag.innerFrame.contentWindow.getSelect();
			if(select.length > 0){
				if(callback){
					callback(select);
				}
			}
		}
		diag.close();
	};		
	diag.show();
}



/**选择任务
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectStage(obj,obj_id,callback,selType,queryCon,winType){
	
	if(!queryCon)queryCon="";
	
	var diag;
	if(winType=="top"){
		diag=new top.Dialog();
	}else if(winType=="parent"){
		diag=new Dialog();
	}else{
		diag=new top.Dialog();
	}
	
	
	diag.Drag=true;
	diag.Title ="所属阶段";
	diag.URL = $("base[href]").prop("href")+'stage/selectStage.do?selType='+selType+queryCon+'&winType='+winType;
	diag.Width = 1000;
	diag.Height = 400;
	diag.CancelEvent=function(){
		if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		{
			var select = diag.innerFrame.contentWindow.getSelect();
			if(select.length > 0){
				if(callback){
					callback(select);
				}
			}
		}
		diag.close();
	};		
	diag.show();
}



/**选择项目
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectProject(obj,obj_id,callback,selType,queryCon,winType){
	
	if(!queryCon)queryCon="";
	
	var diag;
	if(winType=="top"){
		diag=new top.Dialog();
	}else if(winType=="parent"){
		diag=new Dialog();
	}else{
		diag=new top.Dialog();
	}
	
	
	diag.Drag=true;
	diag.Title ="项目选择";
	diag.URL = $("base[href]").prop("href")+'project/selectProject.do?selType='+selType+queryCon+'&winType='+winType;
	diag.Width = 1000;
	diag.Height = 500;
	diag.CancelEvent=function(){
		if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		{
			var select = diag.innerFrame.contentWindow.getSelect();
			if(select.length > 0){
				if(callback){
					callback(select);
				}
			}
		}
		diag.close();
	};		
	diag.show();
}






/**选择人员
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectStaff(obj,obj_id,callback,selType,queryCon,winType){
	 
	 if(!queryCon)queryCon="";
	
	 var diag;
	 if(winType=="top"){
		 diag=new top.Dialog();
	 }else if(winType=="parent"){
		 diag=new Dialog();
	 }else{
		 diag=new top.Dialog();
	 }
	 
	 
	 diag.Drag=true;
	 diag.Title ="人员选择";
	 diag.URL = $("base[href]").prop("href")+'staff/selectStaff?selType='+selType+queryCon+'&winType='+winType;
	 diag.Width = 1000;
	 diag.Height = 400;
	 diag.CancelEvent=function(){
		 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 var select = diag.innerFrame.contentWindow.getSelect();
			 if(select.length > 0){
				 if(callback){
					callback(select);
				 }
			 }
		 }
		 diag.close();
	 };		
	 diag.show();
}





/**班组选择
 * @param obj ：对象
 * @param obj_id ：对象id
 * @param callback ：回调方法
 * @param selType ：mul:多选，sin：单选
 * @param queryCon ：查询条件，格式：&参数名=参数值&参数名=参数值
 * @param winType ：窗口类型：top，parent
 */
function selectTeam(obj,obj_id,callback,selType,queryCon,winType){
	 
	if(!queryCon)queryCon="";
	
	 var diag;
	 if(winType=="top"){
		 diag=new top.Dialog();
	 }else if(winType=="parent"){
		 diag=new Dialog();
	 }else{
		 diag=new top.Dialog();
	 }
	
	 diag.Drag=true;
	 diag.Title ="班组选择";
	 diag.URL = $("base[href]").prop("href")+'team/selectTeam.do?selType='+selType+queryCon+'&winType='+winType;
	 diag.Width = 800;
	 diag.Height =500;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('saveEditCenter').style.display == 'none')
		 {
			 var select = diag.innerFrame.contentWindow.getSelect();	
			 if(select.length > 0){
				 if(callback){
					callback(select);
				 }
			 }
		 }
		 diag.close();
	 };
	 diag.show();
}


/**
 * 显示屏幕右下角的消息框
 * @param {} title 标题
 * @param {} msg 消息内容
 */
function showTips(title,msg){
	$.messager.show({
    title:title,  
	msg:msg,  
	timeout:5000,  
	showType:'slide'  
   });		
}


/**
 * 获取给定时间的前一天日期或后一天日期
 * @param {} date 给定的时间，Date类型 ,为空，则为this，再则默认为当前日期
 * @param {} yesterday_or_tomorrow 参数DATE.YESTERDAY(-1) 或是DATE.TOMORROW(1)
 * @return {}
 */
function getTheDay(yesterday_or_tomorrow,p_date){
	var date = new Date();
	if(p_date){
		date = p_date;
	}else if(typeof this.getDate == "function"){
		date = this;
	}
	//一天的毫秒数 1000*60*60*24
	var one_day_milliseconds = 86400000;
	var the_day_milliseconds = date.getTime();
	if(yesterday_or_tomorrow==-1){
		the_day_milliseconds=the_day_milliseconds-one_day_milliseconds;    
	}
	if(yesterday_or_tomorrow==1){
		the_day_milliseconds=the_day_milliseconds+one_day_milliseconds;    
	}
	var yesterday = new Date();     
	yesterday.setTime(the_day_milliseconds);     
	   
	
	return yesterday;
}


/**
 * 格式化日期 yyy-mm-dd
 * @param {} p_date 指定要格式化的日期，为空则取this，再则则取当前日期
 * @param {} withTime 是否返回时间 boolean类型 为空，则默认为false
 * @return {}
 */
function dateFormatter(p_isWithTime,p_date){
	var date = new Date();
	var isWithTime = false;
	if(p_date){
		date = p_date;
	}else if(typeof this.getDate == "function"){
		date = this;
	}
	if(p_isWithTime==true ||p_isWithTime==false){
		isWithTime = p_isWithTime;
	}
	var myyear = date.getFullYear();  
    var mymonth = date.getMonth()+1;  
    var myweekday = date.getDate();   
    var myHour=date.getHours()>9?date.getHours():'0'+date.getHours();
    var myMinute=date.getMinutes()>9?date.getMinutes():'0'+date.getMinutes();
    var mySecond=date.getSeconds()>9?date.getSeconds():'0'+date.getSeconds();
    
    if(mymonth < 10){  
        mymonth = "0" + mymonth;  
    }   
    if(myweekday < 10){  
        myweekday = "0" + myweekday;  
    }
    var strdate;
 	if(isWithTime){
    	strdate = myyear+"-"+mymonth + "-" + myweekday + " "+myHour+":"+myMinute+":"+mySecond;
 	}else{
    	strdate = myyear+"-"+mymonth + "-" + myweekday;   
    }
    return strdate;
}

/**
 * 取得给出日期的前后number_of_days天的日期对象{id:date,text:date}数组
 * @param {} number_of_days 前后多少时间
 * @param {} p_date 给出的基准日期，为空则取this，this为空,则取当前日期
 * @return {}
 */
function getDateTreeData(number_of_days,p_date){
	var date;
	var num;
	if(p_date){
		date = p_date;
	}else if(typeof this.getDate == "function"){
		date = this;
	}else{
		date = new Date();
	}
	if(number_of_days){
		num = number_of_days;
	}else{
		num = 10;
	}
	
	var dateArray = new Array();
	// 计算的当前日期
	var c_date = date;
	var value = date.dateFormatter();
	var o_day = {
		id:value,
		text:value,
		iconCls:'icon-dateicon'
	}
	dateArray.push(o_day);
	//前十天日期
	for(var i=0;i<num;i++){
		c_date = c_date.getTheDay(date.YESTERDAY);
		value = c_date.dateFormatter();
		o_day ={
			id:value,
			text:value,
			iconCls:'icon-dateicon'
		}
		dateArray.push(o_day);
	}
	//后十天日期
	var c_date = date;
	for(var i=0;i<num;i++){
		c_date = c_date.getTheDay(date.TOMORROW);
		value = c_date.dateFormatter();
		o_day ={
			id:value,
			text:value,
			iconCls:'icon-dateicon'
		}
		dateArray.push(o_day);
	}
	//排序
	var i=0;
	var j=num;
	while(i<j){
		var temp = dateArray[i];
		dateArray[i] = dateArray[j];
		dateArray[j] = temp;
		i++;
		j--;
	}
	return dateArray;
}


/**
 * 去除字符串首尾空格
 * @param {} str 字符串
 * @return {}
 */
function trim(p_str){
	var start=0,end=0,str="";
	if(p_str||p_str == ""){
		str = p_str;
	}else{
		str = this;
	}
	for(var i=0;i<str.length;i++){
		if(str.charAt(i)!=" "){
			start = i;
			break;
		}
	}
	for(var i=str.length-1;i>=0;i--){
		if(str.charAt(i)!=" "){
			end = i+1;
			break;
		}
	}
	return str.substring(start,end);
}


//判断是否为空
function isEmpty(data){
	if(data==null){
		return true;
	}else if(data==undefined){
		return true;
	}else if(data==""){
		if(data.length==0){
			return true;
		}else{
			return false;
		}
	}else if(data=="null"){
		return true;
	}else if(data.length==0	){
		return true;
	}else{
		return false;
	}
}


/****************************************************validate  start***********************************************/
/*************************************************validate end **************************************************/

/************************************************datagrid 扩展 start**************************************************/
/**
 * 扩展验证的类型
 */
$.extend($.fn.validatebox.defaults.rules, { 
	tel:{ 
 		validator: function(value, param){ 
 				var reg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/; 
 				return reg.test(value); 
			}, 
 		message: '请输入有效的电话号码'
 	},
 	phone:{ 
 		validator: function(value, param){ 
 				var reg = /^0?(13[0-9]|15[0-9]|16[0-9]|17[0-9]|19[0-9]|18[0-9]|14[0-9])[0-9]{8}$/; 
 				return reg.test(value); 
			}, 
 		message: '请输入有效的手机号码'
 	},
 	IDCard:{
 		validator: function(value, param){
 			var reg = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/; 
 			return reg.test(value);
 		},
 		message:'请输入有效的身份证号码'
 	},
 	validDateType:{
 		validator: function(value, param){
			var reg = new RegExp("^(?:(?:([0-9]{4}(-|\/)(?:(?:0?[1,3-9]|1[0-2])(-|\/)(?:29|30)|((?:0?[13578]|1[02])(-|\/)31)))|([0-9]{4}(-|\/)(?:0?[1-9]|1[0-2])(-|\/)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-|\/)0?2(-|\/)29))))$");
// 			var reg = new RegExp("^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");
 			return reg.test(value);
 		},
 		message:'请输入正确的日期格式'
 	},
 	validDateTimeType:{
 		validator: function(value, param)
 		{
// 			var reg = new RegExp("^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d$");
 			var reg2 = new RegExp("^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$");
 			return reg2.test(value);
 		},
 		message:'请输入正确的日期时间格式'
 	},
 	beforeCurrDate:{
 		validator: function(value, param){
 			var flag = true;
 			var currDate = new Date(); 
 			var currDateStr = currDate.format('yyyy-MM-dd');
 			if ((new Date(currDateStr)) < (new Date(value)))
 			{
 				flag = false;
 			}
 			
 			return flag;
 		},
 		message:'必须小于等于当前日期'
 	},
 	afterCurrDate:{
 		validator: function(value, param){
 			var flag = true;
 			var currDate = new Date(); 
 			var currDateStr = currDate.format('yyyy-MM-dd');
 			if ((new Date(currDateStr)) > (new Date(value)))
 			{
 				flag = false;
 			}
 			
 			return flag;
 		},
 		message:'必须大于等于当前日期'
 	},
 	compareDateAfter:
 	{
 		validator:function(value, param){
 			var reg = new RegExp("^(?:(?:([0-9]{4}(-|\/)(?:(?:0?[1,3-9]|1[0-2])(-|\/)(?:29|30)|((?:0?[13578]|1[02])(-|\/)31)))|([0-9]{4}(-|\/)(?:0?[1-9]|1[0-2])(-|\/)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-|\/)0?2(-|\/)29))))$");
 			var comDate = $(param[0]).datebox('getValue');
 			var flag = true;
 			if (reg.test(value) &&  reg.test(comDate))
 			{
 				if ((new Date(value)) < (new Date(comDate)))
 				{
 					flag = false;
 				}
 			}
 			return flag;
 			
 		},
 		message: '{1}必须大于等于{2}'
 	},
 	safepass: {  
        validator: function (value, param) {  
            return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));   
        },  
        message: '密码由字母和数字组成，至少6位'  
    },
    equalTo: {  
        validator: function (value, param) {  
            return value == $(param[0]).val();  
        },  
        message: '两次输入的字符不一至'  
    },
    number: {  
        validator: function (value, param) {  
            return /^\d*$/.test(value);  
        },  
        message: '请输入数字'
    },
    chinese : {// 验证中文 
        validator : function(value) { 
            return /^[\Α-\￥]+$/i.test(value); 
        }, 
        message : '请输入中文' 
    },
    notChinese : {// 验证非中文 
        validator : function(value) { 
            return (/^[^\Α-\￥]*$/i.test(value)); 
        }, 
        message : '请输入非中文字符' 
    },
    passwordOk : {// 验证前后密码一致
        validator : function(value) { 
            return value==$("#PASSWORD").val();
        }, 
        message : '两次输入的密码不一致' 
    },
    alphanumeric : {
    	validator: function(value,param){
     	   return (/^\w+$/i.test(value));
         },
        message: '只能输入字母、数字、下划线!'
    }
    
});


/**
 * datagrid 扩展方法
 */
$.extend($.fn.datagrid.methods, {
		/**
		 * 结束所有行的编辑 如果验证，返回true为通过验证
		 * @param {} jq
		 * @param {} items 数组 ，第一个参数为boolean类，是否验证，第二个参数为验证失败时，alert的提示信息
		 * @return {Boolean}
		 */
		endEditAll:function(jq,items){
			var grid = jq;
			var isValidate = false; 
			var msg;
			if(items){
				if(typeof items =='boolean'){
					isValidate = items;
				}else{
					isValidate = items[0]
					if(items.length>=2){
						msg= items[1];
					}
				}
				
			}
			var rows = grid.datagrid('getRows');
        	for ( var i = 0; i < rows.length; i++) {
	        	grid.datagrid('endEdit', i);
	        	if(isValidate){
	        		grid.datagrid('beginEdit',i)
		        	if(grid.datagrid('validateRow',i)){
		        		grid.datagrid('endEdit',i)
		        		continue;
		        	}else{
		        		 if(msg){
		        		 	$.messager.alert('系统提示',msg);
		        		 }
		        		 return false;
		        	}
	        	}
        	}
        	return true;		
		},
		/**
		 * 保存最后一次以来的所有更改，数据以json格式传送，分为inserted（插入的数据数组），deleted（删除的数据数组），updated（跟新的数据数组）
		 * @param {} jq
		 * @param {objec} items :url,isShowMsg，msg，inserted，deleted,updated,isReload
		 * url：即保存数据提交的地址
		 * isShowMsg:ture|false，可选，默认为true，即成功提交显示提示信息 
		 * msg：isShowMsg 为true是有效，即成功的提示信息
		 * inserted，deleted,updated:即提交的添加、更新、删除的参数名称，默认为inserted、updated、deleted
		 * isReload:boolean 类型，true表示保存后刷新，false表示保存后不刷新
		 */
		saveChanges:function(jq,items){
			var grid = jq;
			var url;
			var flag;
			var isShowMsg = true;
			if(items.isShowMsg){
				isShowMsg = items.isShowMsg;
			}
			var msg = "保存更改数据成功!";
			url = items.url;
			if(typeof url =="undefined"){
				$.messager.alert('系统提示',"url不可为空！");
				return ;
			}
			var insertedName = items.inserted;
			var deletedName = items.deleted;
			var updateName = items.updated;
			
			if(!grid.datagrid('endEditAll',true)){
				return;
			}
			if(grid.datagrid('getChanges').length){
				 var inserted = grid.datagrid('getChanges', "inserted");
				 var deleted = grid.datagrid('getChanges', "deleted");
				 var updated = grid.datagrid('getChanges', "updated");
				 var effectRow = new Object();
			}
			if(inserted.length){
				if(insertedName){
					effectRow[insertedName] = JSON.stringify(inserted);
				}else{
					effectRow["inserted"] = JSON.stringify(inserted);
				}
			}
			if(deleted.length){
				if(deletedName){
					effectRow[deletedName] = JSON.stringify(deleted);
				}else{
					effectRow["deleted"] = JSON.stringify(deleted);
				}
			}
			if(updated.length){
				if(updateName){
					effectRow[updateName] = JSON.stringify(updated);
				}else{
					effectRow["updated"] = JSON.stringify(updated);
				}
			}
			var isReload = items.isReload;
			if(typeof isReload != "boolean"){
				isReload = false;
			}
			 $.post(url, 
			 	effectRow,
			    function(data){
			    	if(isShowMsg){
				    	$.messager.show({
							title:"提示",  
							msg:"保存更改数据成功",  
							timeout:5000,  
							showType:'slide'  
						});	
			    	}
					grid.datagrid('acceptChanges');	
					if(isReload){
						grid.datagrid('reload');
					}
					flag = true;
				}, 
				"JSON"
			).error(function() {
				$.messager.alert("提示", "保存更改数据失败！");
				flag = false;
			});
			return flag;
			
		}
});
/************************************************datagrid 扩展 start**************************************************/

/***/
Date.prototype.format = function(format)
{
    var o =
    {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
Date.prototype.YESTERDAY = -1;
Date.prototype.TOMORROW = 1;
Date.prototype.dateFormatter = dateFormatter;
Date.prototype.getTheDay = getTheDay;
Date.prototype.getDateTreeData = getDateTreeData;
String.prototype.trim = trim;

/**
 * 操作成功提示信息
 */
function alertSuccess(){
	$.messager.alert('提示','操作成功','info');
}

/**
 * 检查两个datebox的时间是否合法（第一个<=第二个时间）
 * @param {} id1
 * @param {} id2
 */
function checkDate(id1,id2){
	var dateStr1 = $('#'+id1).datebox('getValue').split('-');
	var date1 = new Date(dateStr1[0],dateStr1[1],dateStr1[2]);
	var dateStr2 = $('#'+id2).datebox('getValue').split('-');
	var date2 = new Date(dateStr2[0],dateStr2[1],dateStr2[2]);
	if(date1>date2){
		return false;
	}
	return true;
}

//屏蔽Enter和BackSpace键，避免按这两个键导致系统退出
$(document).keydown(function(e){
	var doPrevent;
	if (e.keyCode == 8 || e.keyCode == 13)//8:Backspace,13:Enter
	{
		var d = e.srcElement || e.target;
		if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA')
		{
			doPrevent = d.readOnly || d.disabled;
		}
		else
		{
			doPrevent = true;
		}
	}
	else
	{
		doPrevent = false;
	}
	if (doPrevent)
	{
		e.preventDefault();
	}
});
