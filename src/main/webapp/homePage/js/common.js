var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
 //服务器host
var curWwwPath = window.document.location.href;
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
var localhostPaht = curWwwPath.substring(0, pos);
var httpurl = localhostPaht+'/common/'; 

var minbodyHeight=window.screen.height-200;
function getMinBodyHeight(){
	var screenHeight=window.screen.height;
	var minbodyHeight=screenHeight-200;
	return minbodyHeight;
}
function root() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);

    return localhostPaht;
}

//获取设备下拉组件数据
function getMacOptions() {
    var resultData = [];
    $.ajax({
        url: root() + "/common/selects/getMacSelect",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.data;
        }
    });
    return resultData;
}

//获取部门下拉组件数据
function getDeptOptions() {
    var resultData = [];
    $.ajax({
        url: root() + "/common/selects/getDeptsForFilter",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.depts;
        }
    });
    return resultData;
}

//获取人员下拉组件数据
function getUserOptions() {
    var resultData = [];
    $.ajax({
        url: root() + "/common/selects/getUsersForSelect",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.data;
        }
    });
    return resultData;
}


//获取部门的treedata
function getDeptTree() {
    var resultData = [];
    $.ajax({
        url: root() + "/common/trees/getDeptTree",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.data;
        }
    });
    return resultData;
}

//获取班组下拉组件数据
function getTeamOptions() {
    var resultData = [];
    $.ajax({
        url: root() + "/common/system/agency/team/getAllTeams",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.teams;
        }
    });
    return resultData;
}

//获得设备类型的treedata
function getMacTypeTree() {
    var resultData = [];
    $.ajax({
        url:root()+"/common/trees/getMacTypeTree",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.data;
        }
    });
    return resultData;
}

//获取产品类型下拉组件数据
function getGoodsTypeNameAndCode() {
    var resultData;
    $.ajax({
        url: root() + "/wit/goodsInfo/goodsType/getNameAndCode",
        type: 'POST',
        dataType: "json",
        async:false,
        success: function (data) {
            if (data.result == "success") {
                resultData=data.data;
            }
        }
    })
    return resultData;
}


//获取工序下拉组件数据
function getProcessInfoNameAndCode() {
    var resultData;
    $.ajax({
        url: root() + "/wit/goodsInfo/processInfo/getNameAndCode",
        type: 'POST',
        dataType: "json",
        async:false,
        success: function (data) {
            if (data.result == "success") {
                resultData=data.data;
            }
        }
    })
    return resultData;
}


//获取工位下拉组件数据
function getWitMacInfoNameAndCode() {
    var resultData;
    $.ajax({
        url: root() + "/common/system/agency/macInfo/getNameAndCode",
        type: 'POST',
        dataType: "json",
        async:false,
        success: function (data) {
            if (data.result == "success") {
                resultData=data.data;
            }
        }
    })
    return resultData;
}


//获取库位下拉
function getBankPosesOptions(){
              
      var resultData = [];
      $.ajax({
         url:root()+"/wms/bank/bankPos/getDataForExport",
         type:"post",
         dataType:"json",
          async: false,
          cache: false,
         success:function (data) {
            if(data.result==="success"){
                resultData=data.data;
            }
            if(data.result==="error"){
                _this.$message({
                    type:"error",
                    duration:1000,
                    message:"服务器出错,获取数据失败!"
                });
            }
         },
      });
      return resultData;
}
//获取仓库下拉
function getBankRoomsOptions(){
      var resultData = [];
      $.ajax({
         url:root()+"/wms/bank/bankPos/getRooms",
         type:"post",
         dataType:"json",
         async: false,
          cache: false,
         success:function (data) {
            if(data.result==="success"){
                resultData=data.rooms;
            }
            if(data.result==="error"){
                _this.$message({
                    type:"error",
                    duration:1000,
                    message:"服务器出错,获取数据失败!"
                });
            }
         },
      });
      return resultData;
 }


/*
 * 选择产品信息
 */
function chooseGoodsInfo() {
	top.layer.open({
        type: 2,//iframe方式
        title: '选择产品',
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area: ['1150px', '600px'],
        resize: true,//是否允许拉伸
        content: root() + "/html/wit/goodsInfo/goodsInfo/goodsInfo_choose.html",
        end: function () {
            if (JSON.stringify(top.layerFrameConfig)!=='{}') {
                vm.chooseGoodsInfoCallback(top.layerFrameConfig.row);
            }
            top.layerFrameConfig={};
        }
    });
}
/*
 * 选择人员信息
 */
function chooseUserInfo() {
	top.layer.open({
        type: 2,//iframe方式
        title: '选择产品',
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area: ['1150px', '600px'],
        resize: true,//是否允许拉伸
        content: root() + "/html/common/system/agency/userInfo/chooseUser.html",
        end: function () {
            if (JSON.stringify(top.layerFrameConfig)!=='{}') {
                vm.chooseUserInfoCallback(top.layerFrameConfig.row);
            }
            top.layerFrameConfig={};
        }
    });
}

// 删除json中值为空的元素
function deleteEmptyProperty(object) {
    for (var i in object) {
        var value = object[i];
        if (typeof value === 'object') {
            if (Array.isArray(value)) {
                if (value.length == 0) {
                    delete object[i];
                    console.log('delete Array', i);
                    continue;
                }
            }
            deleteEmptyProperty(value);
            if (isEmpty(value)) {
                console.log('isEmpty true', i, value);
                delete object[i];
                console.log('delete a empty object');
            }
        } else {
            if (value === '' || value === null || value === undefined || value === " ") {
                delete object[i];
                console.log('delete ', i);
            } else {
                console.log('check ', i, value);
            }
        }
    }
}

function isEmpty(object) {
    for (var name in object) {
        return false;
    }
    return true;
}

function getTopWinow() {
    var p = window;
    while (p != p.parent) {
        p = p.parent;
    }
    return p;
}

$.ajaxSetup({
    type: 'POST',
    error:function (xhr,textStatus) {
        var sessionStatus = xhr.getResponseHeader('sessionstatus');
        if(sessionStatus==='timeout'){
            var top = getTopWinow();
            top.location.href='/html/common/login.html';
        }
    }
});

//json数据转excel
function JSONToExcelConvertor(JSONData, FileName, showLabel) {
    //先转化json
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
    var excel = '<table>';
    var row = "<tr>";
    //设置表头
    //var keys = Object.keys(JSONData[0]);
    showLabel.forEach(function (val, index) {
        row += "<td style='text-align: center'><b>" + val + '</b></td>';
    });
    //换行
    excel += row + "</tr>";

    //设置数据
    //设置数据
    for (var i = 0; i < arrData.length; i++) {
        var row = "<tr>";
        for (var j = 0; j < arrData[i].length; j++) {
            var value = arrData[i][j].value === "." ? "" : arrData[i][j].value;
            if (value === undefined) {
                value = "";
            }
            row += '<td style=\'text-align: center\'>' + value + '</td>';
        }
        excel += row + "</tr>";
    }

    excel += "</table>";

    var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
    excelFile += '<meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">';
    // excelFile += '<meta http-equiv="content-type" content="application/vnd.ms-excel';
    // excelFile += '; charset=UTF-8">';
    excelFile += "<head>";
    excelFile += "<!--[if gte mso 9]>";
    excelFile += "<xml>";
    excelFile += "<x:ExcelWorkbook>";
    excelFile += "<x:ExcelWorksheets>";
    excelFile += "<x:ExcelWorksheet>";
    excelFile += "<x:Name>";
    excelFile += "{worksheet}";
    excelFile += "</x:Name>";
    excelFile += "<x:WorksheetOptions>";
    excelFile += "<x:DisplayGridlines/>";
    excelFile += "</x:WorksheetOptions>";
    excelFile += "</x:ExcelWorksheet>";
    excelFile += "</x:ExcelWorksheets>";
    excelFile += "</x:ExcelWorkbook>";
    excelFile += "</xml>";
    excelFile += "<![endif]-->";
    excelFile += "</head>";
    excelFile += "<body>";
    excelFile += excel;
    excelFile += "</body>";
    excelFile += "</html>";

    var uri = 'data:application/vnd.ms-excel;charset=utf-8,' + encodeURIComponent(excelFile);

    var link = document.createElement("a");
    link.href = uri;

    link.style = "visibility:hidden";
    link.download = FileName + ".xls";

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

//时间格式化 到天
function formatDateDay(date) {
    var seperator1 = ".";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}



//根据file获取对象URL
function createObjectURL(blob) {
    if (window.URL) {
        return window.URL.createObjectURL(blob);
    } else if (window.webkitURL) {
        return window.webkitURL.createObjectURL(blob);
    } else {
        return null;
    }
}

//数组原型添加in_array()，判断数组是否包含element
Array.prototype.in_array = function (element) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == element) {
            return true;
        }
    }
    return false;
}

//根据数组元素中对象的属性给对象数组排序
/*==============================================================================================================*/
function createAscFunction(propertyName) {  //升序
    return function (obj1, obj2) {
        var v1 = obj1[propertyName];
        var v2 = obj2[propertyName];
        if (v1 < v2) {
            return -1;
        } else if (v1 > v2) {
            return 1;
        } else {
            return 0;
        }
    }
}

function createDescFunction(propertyName) { //降序
    return function (obj1, obj2) {
        var v1 = obj1[propertyName];
        var v2 = obj2[propertyName];
        if (v1 < v2) {
            return 1;
        } else if (v1 > v2) {
            return -1;
        } else {
            return 0;
        }
    }
}

/*==============================================================================================================*/


/*===============================消息提示整合=====================================*/
function infoMessage(message) {
    vm.$message({
        type: 'info',
        showClose: true,
        duration: 1500,
        message: message
    });
}

function successMessage(message) {
    vm.$message({
        type: 'success',
        showClose: true,
        duration: 1500,
        message: message
    });
}

function warnMessage(message) {
    vm.$message({
        type: 'warning',
        showClose: true,
        duration: 1500,
        message: message
    });
}

function errorMessage(message) {
    vm.$message({
        type: 'error',
        showClose: true,
        duration: 1500,
        message: message
    });
}

/*=================================================================================*/


/*======================================    table操作，共用方法 (排序，选择框，分页)   ===========================================*/

//初始化
function initFunction(that) {
    that.getTableData();
    that.windowHeight = window.innerHeight - 101;
    window.addEventListener('resize', function () {
        that.windowHeight = window.innerHeight - 101;
    })
}

//清空搜索框，刷新表格
function clearSearchFunction() {
    vm.pageData.codeSearch = '';
    vm.pageData.nameSearch = '';
    vm.getTableData();
}

//排序规则改变触发
function handleSortChangeFunction(column) {
    if (column.order != null) {
        column.order = column.order === 'ascending' ? 'asc' : 'desc';
    }
    vm.pageData.prop = column.prop;
    vm.pageData.order = column.order;
    vm.getTableData();
}

//pageSize改变时触发的函数
function handleSizeChangeFunction(val) {
    vm.pageData.pageSize = val;
    vm.getTableData();
}

//当前页改变时触发的函数
function handleCurrentChangeFunction(val) {
    vm.pageData.pageCode = val;
    changePageCoreRecordDataFunction();
    vm.getTableData();
}

//选择框改变时触发
function handleSelectionChangeFunction(val) {
    vm.multipleSelection = val;
    //实时记录选中的数据
    setTimeout(function () {
        changePageCoreRecordDataFunction();
    }, 50)
}

//设置行选中
function selectRowFunction() {
    if (!vm.multipleSelectionAll || vm.multipleSelectionAll.length <= 0) {
        return;
    }
    // 标识当前行的唯一键的名称
    var idKey = vm.idKey;
    var selectAllIds = [];
    vm.multipleSelectionAll.forEach(function (row) {
        selectAllIds.push(row[idKey]);
    });
    vm.$refs.table.clearSelection();
    for (var i = 0; i < vm.tableData.length; i++) {
        if (selectAllIds.indexOf(vm.tableData[i][idKey]) >= 0) {
            // 设置选中，table组件需要使用ref="table"
            vm.$refs.table.toggleRowSelection(vm.tableData[i], true);
        }
    }
}

// 记忆选择核心方法
function changePageCoreRecordDataFunction() {
    // 标识当前行的唯一键的名称
    var idKey = vm.idKey;
    // var vm = vm;
    // 如果总记忆中还没有选择的数据，那么就直接取当前页选中的数据，不需要后面一系列计算
    if (vm.multipleSelectionAll.length <= 0) {
        vm.multipleSelectionAll = vm.multipleSelection;
        return;
    }
    // 总选择里面的key集合
    var selectAllIds = [];
    vm.multipleSelectionAll.forEach(function (row) {
        selectAllIds.push(row[idKey]);
    });
    var selectIds = [];
    // 获取当前页选中的id
    vm.multipleSelection.forEach(function (row) {
        selectIds.push(row[idKey]);
        // 如果总选择里面不包含当前页选中的数据，那么就加入到总选择集合里
        if (selectAllIds.indexOf(row[idKey]) < 0) {
            vm.multipleSelectionAll.push(row);
        }
    });
    var noSelectIds = [];
    // 得到当前页没有选中的id
    vm.tableData.forEach(function (row) {
        if (selectIds.indexOf(row[idKey]) < 0) {
            noSelectIds.push(row[idKey]);
        }
    });
    noSelectIds.forEach(function (id) {
        if (selectAllIds.indexOf(id) >= 0) {
            for (var i = 0; i < vm.multipleSelectionAll.length; i++) {
                if (vm.multipleSelectionAll[i][idKey] == id) {
                    // 如果总选择中有未被选中的，那么就删除这条
                    vm.multipleSelectionAll.splice(i, 1);
                    break;
                }
            }
        }
    })
}

//删除单个
function deleteFunction(row) {
    vm.$confirm('是否确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(function () {
        vm.deleteMethod(row);//执行单个删除的方法
    }).catch(function (reason) {
    });
}

//批量删除
function batchDeleteFunction() {
    var idKey = vm.idKey;
    if (vm.multipleSelectionAll.length === 0) {
        warnMessage('请选择要删除的数据');
        return;
    }
    vm.$confirm('是否确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(function () {
        vm.deleteIds = [];
        vm.multipleSelectionAll.forEach(function (item) {
            vm.deleteIds.push(item[idKey])
        });
        vm.batchDeleteMethod();//执行批量删除的方法
    }).catch(function (reason) {
    });
}

/*===========================================================================================================*/
/**
 * 打开附件页面
 * @param {Object} tableName
 * @param {Object} tableKeyValue
 * @param {Object} upFileIds
 */
function openFileManage(tableName, tableKeyValue, upFileIds) {
    top.layerFrameConfig = {
        id: self.frameElement.getAttribute('id'),
        tableName: tableName,
        tableKeyValue: tableKeyValue,
        upFileIds: upFileIds,
    };
    top.layer.open({
        type: 2,//iframe方式
        title: '附件管理',
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area: ['1000px', '600px'],
        resize: true,//是否允许拉伸
        content: this.root() + "/html/common/file/fileManage.html",
        end: function () {
            vm.getTableData();
        }
    });
}


/**
 * 删除上传文件时的临时文件
 */
function deleteCacheFiles(upFileIds) {
    if ((upFileIds != null) && (upFileIds != '')) {
        $.ajax({
            url: root() + "/common/file/deleteCacheFiles",
            type: 'POST',
            dataType: "json",
            data: {upFileIds: upFileIds},
            async: false,
            cache: false,
            success: function (data) {

            }
        });
	}
}

/**
 * 更新上传临时文件的tableKeyValue
 */
function updateCacheFiles(upFileIds, tableKeyValue) {
    if ((upFileIds != null) && (upFileIds != '') && (tableKeyValue != null) && (tableKeyValue != '')) {
        $.ajax({
            url: root() + "/common/file/updateCacheFiles",
            type: 'POST',
            dataType: "json",
            data: {
            	upFileIds: upFileIds,
            	tableKeyValue: tableKeyValue
            },
            async: false,
            cache: false,
            success: function (data) {

            }
        });
	}
}

/**
 * 通过tableName 和 tableKeyValue 查找出已存在的文件Id （SYS_uploadFile表的upFileId）
 * @param tableName
 * @param tableKeyValue
 * @returns {Array}
 */
function getUpFileIds(tableName,tableKeyValue) {
    var upFileIds=[];
    $.ajax({
        type: "post",
        url: root() + "/common/file/getUpFileIds",
        dataType: "json",
        async: false,
        cache: false,
        data: {
            tableName: tableName,
            tableKeyValue: tableKeyValue
        },
        success: function (res) {
            if (res.result === 'success') {
                if (res.data.length > 0) {
                    res.data.forEach(function (item) {
                        upFileIds.push(item.upFileId)
                    });
                }
            } else {
                errorMessage('数据加载错误');
            }
        },
        error: function () {
            errorMessage('数据加载错误');
        }
    });
    return upFileIds;

}

//获取设备类型下拉组件数据
function getMacTypeOptions() {
    var resultData = [];
    $.ajax({
        url: root() + "/MAC/selects/getMacTypeForSelect",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            if (data.result === "success") {
                resultData = data.data;
            }
        }
    });
    return resultData;
}
//获取不良类型下拉组件数据
function getBadTypeOptions() {
    var resultData = [];
    $.ajax({
        url: root() + "/wit/produceCheck/checkBadType/getNameAndCode",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            if (data.result === "success") {
                resultData = data.data;
            }
        }
    });
    return resultData;
}


/*
 * 日期格式化
 */
function dateFormat(row, column, cellValue, index){
	if (cellValue != null && cellValue != "") {
		var t = new Date(cellValue);
		var year = t.getFullYear();
		var month = t.getMonth() + 1;
		var day = t.getDate();

		var newTime = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
  		
  		return newTime;
	}
}

/*
 * 时间格式化
 */
function dateTimeFormat(row, column, cellValue, index){
	if (cellValue != null && cellValue != "") {
		var t = new Date(cellValue);
		var year = t.getFullYear();
		var month = t.getMonth() + 1;
		var day = t.getDate();
		var hour = t.getHours();
		var min = t.getMinutes();
		var sec = t.getSeconds();

		var newTime = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day) + ' ' +
			(hour < 10 ? '0' + hour : hour) + ':' + (min < 10 ? '0' + min : min) + ':' + (sec < 10 ? '0' + sec : sec);
  		
  		return newTime;
	}
}

//获取保养项目下拉组件数据
function getMaintainItemOptions() {
    var resultData = [];
    $.ajax({
        url:root()+"/MAC/selects/getMaintainItemsForSelect",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.data;
        }
    });
    return resultData;
}

//获取点检项目下拉组件数据
function getCheckItemOptions() {
    var resultData = [];
    $.ajax({
        url:root()+"/MAC/selects/getCheckItemsForSelect",
        type: 'POST',
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            resultData = data.data;
        }
    });
    return resultData;
}
//获取设备类型下拉组件数据
function getButtonsPer(itemCode) {
    var isExist=false;
	var obj=sessionStorage.getItem("btnPermissions");
	if(obj==undefined || obj==null){
        return false;
    }
	if(obj.indexOf(itemCode)>-1){
		isExist=true;
	}
    return isExist;
}
