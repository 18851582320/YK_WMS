package com.iwfm.controller.system.file;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iwfm.util.Const;
import com.iwfm.util.FileDownload;
import com.iwfm.util.PathUtil;
import com.iwfm.controller.base.BaseController;

/**
 * ClassName: FileController 
 * @Description: 文件类
 * @author yk
 * @date 2018年1月25日
 */
@Controller
@RequestMapping(value="file")
public class FileController extends BaseController{
	
	/**
	 * @Title:toImportExcel
	 * @Description: excel导入页面
	 * @author yk
	 * @date 2018年1月25日
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 */
	@RequestMapping(value="/toImportFile")
	public ModelAndView toImportExcel(@RequestParam String controllerName,@RequestParam String suffix)throws Exception{
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("system/file/importFile");
		mv.addObject("controllerName", controllerName);//controller的路径
		mv.addObject("suffix", suffix);//上传文件的后缀名，用来验证
		return mv;
	}
	
	/**
	 * @Title:downExcel
	 * @Description: 下载模板
	 * @author yk
	 * @date 2018年1月26日
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response,@RequestParam String controllerName)throws Exception{
		
		String fileName="";
		if(controllerName.equals("user")){
			 fileName="Users.xls";
		}else if(controllerName.equals("goods")){
			fileName="Goods.xls";
		}else if(controllerName.equals("processStep")){
			fileName="ProcessStep.xls";
		}else if(controllerName.equals("units")){ //单位
			fileName="Units.xls";
		}else if(controllerName.equals("wlType")){  //物料类型
			fileName="WlType.xls";
		}else if(controllerName.equals("wlAttribute")){   //物料属性
			fileName="WlAttribute.xls";
		}else if(controllerName.equals("shiftType")){   //班制类型
			fileName="ShiftType.xls";
		}else if(controllerName.equals("detailShiftType")){   //班次类型
			fileName="DetailShiftType.xls";
		}else if(controllerName.equals("fileType")){ //文件类型
			fileName="fileType.xls";
		}else if(controllerName.equals("bankPos")){ //库位
			fileName="bankPos.xls";
		}else if(controllerName.equals("bankRoom")){ //库房
			fileName="bankRoom.xls";
		}else if(controllerName.equals("fileManage")){ //文件管理
			fileName="fileManage.xls";
		}else if(controllerName.equals("customerType")){
			fileName="customerType.xls";
		}else if(controllerName.equals("supplierType")){
			fileName="supplierType.xls";
		}else if(controllerName.equals("resource")){//资源
			fileName="Resource.xls";
		}else if(controllerName.equals("department")){
			fileName="department.xls";
		}else if(controllerName.equals("processControlCode")){  //工序控制码
			fileName="processControlCode.xls";
		}else if(controllerName.equals("goodsProcessVersion")){  //工艺路线
			fileName="GoodProcess.xls";
		}else if(controllerName.equals("staff")){  //工艺路线
			fileName="staff.xls";
		}else if(controllerName.equals("team")){  //班组
			fileName="team.xls";
		}else if(controllerName.equals("duty")){  //职务
			fileName="duty.xls";
		}else if(controllerName.equals("faultReason")){  //故障原因
			fileName="故障原因导入模板.xls";
		}else if(controllerName.equals("macType")){  //设备类型
			fileName="设备类型导入模板.xls";
		}else if(controllerName.equals("faultType")){  //故障类型
			fileName="故障类型导入模板.xls";
		}else if(controllerName.equals("macInfo")){  //设备台账
			fileName="设备台账导入模板.xls";
		}else if(controllerName.equals("customers")){  //客户
			fileName="XS_customers.xls";
		}else if(controllerName.equals("suppliers")){  //供应商
			fileName="CG_suppliers.xls";
		}else if(controllerName.equals("mouldType")){  //模具类型
			fileName="MJ_mouldType.xls";
		}else if(controllerName.equals("mouldFaultType")){  //模具故障类型
			fileName="MJ_mouldFaultType.xls";
		}else if(controllerName.equals("mouldFaultReason")){  //模具故障原因
			fileName="MJ_mouldFaultReason.xls";
		}else if(controllerName.equals("repairKnowledge")){  //维修知识库
			fileName="维修知识库导入模板.xls";
		}else if(controllerName.equals("defectType")){  //缺陷类型
			fileName="ZL_defectType.xls";
		}else if(controllerName.equals("checkType")){  //检验类型
			fileName="ZL_checkType.xls";
		}else if(controllerName.equals("workCenter")){  //工作中心
			fileName="工作中心模板.xls";
		}else if(controllerName.equals("pauseType")){  //暂停类型
			fileName="暂停类型.xls";
		}else if(controllerName.equals("mouldRepairKnowledge")){  //模具维修知识库
			fileName="模具维修知识库导入模板.xls";
		}else if(controllerName.equals("badDealMode")){  //不良处理类型
			fileName="不良处理方式.xls";
		}else if(controllerName.equals("bankWarn")){  //安全库存预警
			fileName="WMS_bankWarn.xls";
		}else if(controllerName.equals("bomVersion")){  //bom版本
			fileName="BOM版本导入模板.xls";
		}else if(controllerName.equals("producePlan")){  //生产计划创建
			fileName="生产计划导入模板.xls";
		}else if(controllerName.equals("checkTemplate")){  //来料检验模板
			fileName="来料检验模板.xls";
		}else if(controllerName.equals("processCheckTemplate")){  //过程检验模板
			fileName="过程检验模板.xls";
		}else if(controllerName.equals("productCheckTemplate")){  //成品检验模板
			fileName="成品检验模板.xls";
		}else if(controllerName.equals("cachingArea")){  //缓存区
			fileName="缓存区.xls";
		}else if(controllerName.equals("mouldMaintainProgram")){//模具保养项目模板
			fileName="模具保养项目模板.xls";
		}else if(controllerName.equals("mouldInfo")){//模具台账模板
			fileName="模具台账模板.xls";
		}else if(controllerName.equals("processCheckItem")){//检验项目模板
			fileName="检验项目模板.xls";
		}else if(controllerName.equals("maintainProgram")){//保养项目模板
			fileName="保养项目模板.xls";
		}else if(controllerName.equals("macSpotCheckItem")){//设备点检项目模板
			fileName="macSpotCheckItem.xls";
		}else if(controllerName.equals("mouldSpotCheckItem")){//模具点检项目模板
			fileName="mouldSpotCheckItem.xls";
		}else if(controllerName.equals("reservePartType")){//备件类型模板
			fileName="reservePartType.xls";
		}else if(controllerName.equals("endProductDefectCode")){//备件类型模板
			fileName="成品缺陷代码.xls";
		}else if(controllerName.equals("badDealMode")){  //不良处理类型
			fileName="不良处理方式.xls";
		}else if(controllerName.equals("endProductCheckItem")){//检验项目模板
			fileName="成品检验项目模板.xls";
		}else if(controllerName.equals("bugCreate")) {//bug创建
			fileName="bug创建.xls";
		}else if(controllerName.equals("tkTask")) {//bug创建
			fileName="任务.xls";
		}else if(controllerName.equals("targetCustomerInfo")) {//目标客户信息
			fileName="targetCustomerInfo.xls";
		}
		
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.TemplateFILEPATHFILE + fileName, fileName);
	}
}
