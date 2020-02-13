/*
 * package com.iwfm.controller.app;
 * 
 * import java.text.SimpleDateFormat; import java.util.ArrayList; import
 * java.util.Calendar; import java.util.Date; import java.util.HashMap; import
 * java.util.List; import java.util.Map;
 * 
 * import javax.annotation.Resource; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.apache.commons.lang.StringUtils; import
 * org.apache.shiro.crypto.hash.SimpleHash; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.ResponseBody; import
 * org.springframework.web.multipart.MultipartHttpServletRequest; import
 * org.springframework.web.servlet.ModelAndView;
 * 
 * import com.iwfm.controller.base.BaseController; import com.iwfm.entity.Page;
 * import com.iwfm.entity.system.Menu; import
 * com.iwfm.service.afterSale.contract.ContractManager; import
 * com.iwfm.service.afterSale.custHandle.CustHandleManager; import
 * com.iwfm.service.afterSale.customers.CustomersManager; import
 * com.iwfm.service.afterSale.request.RequestManager; import
 * com.iwfm.service.afterSale.service.ServiceManager; import
 * com.iwfm.service.oa.department.DepartmentManager; import
 * com.iwfm.service.oa.staff.StaffManager; import
 * com.iwfm.service.perSaleManagement.followRecord.FollowRecordManager; import
 * com.iwfm.service.perSaleManagement.saleLeads.SaleLeadsManager; import
 * com.iwfm.service.perSaleManagement.targetCustomerInfo.
 * TargetCustomerInfoManager; import
 * com.iwfm.service.perSaleManagement.workReport.WorkReportManager; import
 * com.iwfm.service.project.progressSubmit.ProgressSubmitManager; import
 * com.iwfm.service.project.project.ProjectManager; import
 * com.iwfm.service.project.stage.StageManager; import
 * com.iwfm.service.system.menu.MenuManager; import
 * com.iwfm.service.system.register.RegisterManager; import
 * com.iwfm.service.system.role.RoleManager; import
 * com.iwfm.service.system.sms.impl.SmsService; import
 * com.iwfm.service.system.sysDataSet.SysDataSetManager; import
 * com.iwfm.service.system.sysSet.SysSetManager; import
 * com.iwfm.service.system.uploadFile.UploadFileManager; import
 * com.iwfm.service.system.user.UserManager; import
 * com.iwfm.service.system.userRole.UserRoleManager; import
 * com.iwfm.service.task.taskConfirm.TaskConfirmManager; import
 * com.iwfm.service.task.taskNoCheck.TaskNoCheckManager; import
 * com.iwfm.service.task.taskNoReceive.TaskNoReceiveManager; import
 * com.iwfm.service.task.taskReceive.TaskReceiveManager; import
 * com.iwfm.service.task.taskSend.TaskSendManager; import
 * com.iwfm.service.task.tkCheckSubmit.TkCheckSubmitManager; import
 * com.iwfm.service.task.tkProgressSubmit.TkProgressSubmitManager; import
 * com.iwfm.service.task.tkTask.TkTaskManager; import com.iwfm.util.AppUtil;
 * import com.iwfm.util.Const; import com.iwfm.util.DateUtil; import
 * com.iwfm.util.FileDownload; import com.iwfm.util.JsonUtil; import
 * com.iwfm.util.PageData; import com.iwfm.util.PathUtil; import
 * com.iwfm.util.PushUtils; import com.iwfm.util.RightsHelper; import
 * com.iwfm.util.SmsUtils; import com.iwfm.util.Tools; import
 * com.iwfm.util.UuidUtil;
 * 
 * import net.sf.json.JSONArray; import net.sf.json.JSONObject;
 * 
 *//**
	 * 
	 * ClassName: AppController
	 * 
	 * @Description: TODO
	 * @author yk
	 * @date 2018年7月18日
	 */
/*
 * @Controller
 * 
 * @RequestMapping(value="/app") public class AppController extends
 * BaseController {
 * 
 * @Resource(name="userService") private UserManager userService;
 * 
 * @Resource(name="sysSetService") private SysSetManager sysSetService;
 * 
 * @Resource(name="projectService") private ProjectManager projectService;
 * 
 * @Resource(name="staffService") private StaffManager staffService;
 * 
 * @Resource(name="tkTaskService") private TkTaskManager tkTaskService;
 * 
 * @Resource(name="taskReceiveService") private TaskReceiveManager
 * taskReceiveService;
 * 
 * @Resource(name="taskNoReceiveService") private TaskNoReceiveManager
 * taskNoReceiveService;
 * 
 * @Resource(name="tkProgressSubmitService") private TkProgressSubmitManager
 * tkProgressSubmitService;
 * 
 * @Resource(name="taskSendService") private TaskSendManager taskSendService;
 * 
 * @Resource(name="taskNoCheckService") private TaskNoCheckManager
 * taskNoCheckService;
 * 
 * @Resource(name="tkCheckSubmitService") private TkCheckSubmitManager
 * tkCheckSubmitService;
 * 
 * @Resource(name="taskConfirmService") private TaskConfirmManager
 * taskConfirmService;
 * 
 * @Resource(name="uploadFileService") private UploadFileManager
 * uploadFileService;
 * 
 * @Resource(name="userRoleService") private UserRoleManager userRoleService;
 * 
 * @Resource(name="menuService") private MenuManager menuService;
 * 
 * @Resource(name="smsService") private SmsService smsService;
 * 
 * @Resource(name="customersService") private CustomersManager customersService;
 * 
 * @Resource(name="requestService") private RequestManager requestService;
 * 
 * @Resource(name="serviceService") private ServiceManager serviceService;
 * 
 * @Resource(name="stageService") private StageManager stageService;
 * 
 * @Resource(name="registerService") private RegisterManager registerService;
 * 
 * @Resource(name="roleService") private RoleManager roleService;
 * 
 * @Resource(name="departmentService") private DepartmentManager
 * departmentService;
 * 
 * @Resource(name="workReportService") private WorkReportManager
 * workReportService;
 * 
 * @Resource(name="saleLeadsService") private SaleLeadsManager saleLeadsService;
 * 
 * @Resource(name="followRecordService") private FollowRecordManager
 * followRecordService;
 * 
 * @Resource(name="contractService") private ContractManager contractService;
 * 
 * @Resource(name="custHandleService") private CustHandleManager
 * custHandleService;
 * 
 * @Resource(name="targetCustomerInfoService") private TargetCustomerInfoManager
 * targetCustomerInfoService;
 * 
 * @Resource(name="sysDataSetService") private SysDataSetManager
 * sysDataSetService;
 * 
 * @Resource(name="progressSubmitService") private ProgressSubmitManager
 * progressSubmitService;
 * 
 *//**
	 * @Title: targetCustomerSave
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/targetCustomerSave",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object targetCustomerSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=targetCustomerInfoService.saveApp(pd, mRequest); } catch (Exception e) {
 * // TODO: handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 * 
 *//**
	 * @Title: contractSave
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/contractSave",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object contractSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=contractService.saveApp(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: contractEdit
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月14日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/contractEdit",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object contractEdit(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=contractService.editApp(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: customerSave
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerSave",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=customersService.saveApp(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: customerEdit
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月15日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerEdit",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerEdit(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=customersService.editApp(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: contractInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月30日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="contractInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object contractInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData();
 * 
 * 
 * List<PageData> list=contractService.listAll(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: contactStateInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="contactStateInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object contactStateInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData(); List<PageData>
 * list=sysDataSetService.listAll(pd); String json=JsonUtil.list2json(list);
 * return json; } catch (Exception e) { // TODO: handle exception
 * logger.error(e.toString(), e); } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: saveTaskMove
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月29日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/saveTaskMove",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object saveTaskMove() { PageData pd=new PageData();
 * Map<String,String> map=new HashMap<String, String>(); String
 * rtnInfo="success"; try { pd=this.getPageData();
 * map=tkTaskService.editMove(pd, null); String result=map.get("result");
 * if(result.equals("success")) { PushUtils.sendToPc(map.get("loginUserName"));
 * } } catch (Exception e) { logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: trackLimitStaffInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="trackLimitStaffInfo",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object trackLimitStaffInfo(){ PageData pd=new
 * PageData(); try { pd=this.getPageData(); List<PageData>
 * list=staffService.listByUserAndTrackLimitCode(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * 
	 * @Title: trackTaskPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="trackTaskPage",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object trackTaskPage(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * 
 * String taskReveiveStaffIdStr=pd.getString("taskReveiveStaffIdStr");
 * if(StringUtils.isNotEmpty(taskReveiveStaffIdStr)) {
 * 
 * String taskReveiveStaffIdArr[]=taskReveiveStaffIdStr.split(",");
 * 
 * List<String> limitUserLst=new ArrayList<String>(); for(int
 * i=0;i<taskReveiveStaffIdArr.length;i++) {
 * limitUserLst.add(taskReveiveStaffIdArr[i]); } pd.remove("limitUserLst");
 * pd.put("limitUserLst", limitUserLst); }else { pd.put("limitUserLst", null); }
 * 
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.tracklistPage(page); String json=JsonUtil.list2json(list);
 * JSONObject jo=new JSONObject(); jo.put("total",
 * Integer.valueOf(page.getTotalResult())); jo.put("rows", json);
 * 
 * return jo.toString();
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: delayTaskPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月30日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="delayTaskPage",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object delayTaskPage(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * 
 * String taskReveiveStaffIdStr=pd.getString("taskReveiveStaffIdStr");
 * if(StringUtils.isNotEmpty(taskReveiveStaffIdStr)) {
 * 
 * String taskReveiveStaffIdArr[]=taskReveiveStaffIdStr.split(",");
 * 
 * List<String> limitUserLst=new ArrayList<String>(); for(int
 * i=0;i<taskReveiveStaffIdArr.length;i++) {
 * limitUserLst.add(taskReveiveStaffIdArr[i]); } pd.remove("limitUserLst");
 * pd.put("limitUserLst", limitUserLst); }else { pd.put("limitUserLst", null); }
 * 
 * pd.put("nowDay", DateUtil.getDay());
 * 
 * pd.put("sort", "chaoQiDay"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.delaylistPage(page); String json=JsonUtil.list2json(list);
 * JSONObject jo=new JSONObject(); jo.put("total",
 * Integer.valueOf(page.getTotalResult())); jo.put("rows", json);
 * 
 * return jo.toString();
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: trackWorkReportPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="trackWorkReportPage",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object trackWorkReportPage(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * 
 * String createStaffIdStr=pd.getString("createStaffIdStr");
 * if(StringUtils.isNotEmpty(createStaffIdStr)) {
 * 
 * String createStaffIdArr[]=createStaffIdStr.split(",");
 * 
 * List<String> limitUserLst=new ArrayList<String>(); for(int
 * i=0;i<createStaffIdArr.length;i++) { limitUserLst.add(createStaffIdArr[i]); }
 * pd.remove("limitUserLst"); pd.put("limitUserLst", limitUserLst); }else {
 * pd.put("limitUserLst", null); }
 * 
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=workReportService.tracklistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString();
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: saleLeadTrackPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="saleLeadTrackPage",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object saleLeadTrackPage(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * 
 * String saleStaffIdStr=pd.getString("saleStaffIdStr");
 * if(StringUtils.isNotEmpty(saleStaffIdStr)) {
 * 
 * String saleStaffIdArr[]=saleStaffIdStr.split(",");
 * 
 * List<String> limitUserLst=new ArrayList<String>(); for(int
 * i=0;i<saleStaffIdArr.length;i++) { limitUserLst.add(saleStaffIdArr[i]); }
 * pd.remove("limitUserLst"); pd.put("limitUserLst", limitUserLst); }else {
 * pd.put("limitUserLst", null); }
 * 
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=saleLeadsService.tracklistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString();
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: mySaleLead
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="mySaleLead",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object mySaleLead(HttpServletRequest request){ PageData
 * pd=new PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("sort", "createTime,followState");
 * pd.put("order", "desc,asc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=saleLeadsService.appquerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 * 
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: myFuSaleLead
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月11日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="myFuSaleLead",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myFuSaleLead(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("saleUser", STAFF_ID); pd.put("sort", "createTime,followState");
 * pd.put("order", "desc,asc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=saleLeadsService.appquerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: saleLeadSave
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/saleLeadSave",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object saleLeadSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=saleLeadsService.saveApp(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: saveSaleLeadSaleUser
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月15日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/saveSaleLeadSaleUser",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object saveSaleLeadSaleUser() { PageData pd=new
 * PageData(); Map<String,String> map=new HashMap<String, String>(); String
 * rtnInfo="success"; try { pd=this.getPageData(); String
 * STAFF_ID=pd.getString("STAFF_ID"); String account=pd.getString("account");
 * pd=saleLeadsService.findById(pd); if(pd!=null) {
 * if(pd.getString("saleUser").equals(STAFF_ID)) { rtnInfo="noChange";
 * map.put("result", rtnInfo); map.put("errmsg", "不能转给自己！"); }else {
 * pd.put("saleUser", STAFF_ID); pd.put("account", account);
 * map=saleLeadsService.editApp(pd,null); } }else { rtnInfo="errorLead";
 * map.put("result", rtnInfo); map.put("errmsg", "未查询到销售线索信息！"); }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: saveMoveToSaleLead
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年3月8日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/saveMoveToSaleLead",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object saveMoveToSaleLead() { PageData pd=new
 * PageData(); Map<String,String> map=new HashMap<String, String>(); String
 * rtnInfo="success"; try { pd=this.getPageData();
 * 
 * String saleUser=pd.getString("STAFF_ID");//销售经理
 * 
 * String account=pd.getString("account"); PageData staffPd=new PageData();
 * staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * createUser=staffPd.getString("STAFF_ID");
 * 
 * PageData targetPd=targetCustomerInfoService.findById(pd); if(targetPd!=null)
 * {
 * 
 * targetPd.put("saleLeadsId", UuidUtil.get32UUID()); //主键
 * targetPd.put("createUser",createUser);
 * targetPd.put("createTime",DateUtil.getTime());
 * targetPd.put("updateUser",null); targetPd.put("updateTime",null);
 * targetPd.put("followState","1"); targetPd.put("culeSource","11");
 * targetPd.put("saleUser",saleUser);
 * targetPd.put("phone",targetPd.getString("contactWay"));
 * 
 * PageData tagPd=saleLeadsService.findByName(targetPd); if(tagPd!=null) {
 * rtnInfo="errorLead"; map.put("result", rtnInfo); map.put("errmsg",
 * "客户名称重复，销售线索中已经有该客户名称！"); }else { saleLeadsService.save(targetPd, null);
 * targetCustomerInfoService.changeTransformState(targetPd);
 * 
 * rtnInfo="success"; map.put("result", rtnInfo); }
 * 
 * }else { rtnInfo="errorLead"; map.put("result", rtnInfo); map.put("errmsg",
 * "未查询到目标客户信息！"); }
 * 
 * }else { rtnInfo="errorLead"; map.put("result", rtnInfo); map.put("errmsg",
 * "未查询到人员信息！"); }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: saleLeadInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月10日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="saleLeadInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object saleLeadInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData();
 * 
 * 
 * String account=pd.getString("account"); String STAFF_ID="";
 * 
 * if(StringUtils.isNotEmpty(account)) { PageData staffPd=new PageData();
 * staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * STAFF_ID=staffPd.getString("STAFF_ID"); } }
 * 
 * 
 * if(StringUtils.isNotEmpty(pd.getString("smsId"))) { PageData
 * smsPd=smsService.findById(pd); smsPd.put("isRead","1");
 * smsService.edit(smsPd); }
 * 
 * pd=saleLeadsService.findById(pd);
 * 
 * String saleUser=pd.getString("saleUser");
 * 
 * if(StringUtils.isNotEmpty(STAFF_ID) && StringUtils.isNotEmpty(saleUser)) {
 * if(STAFF_ID.equals(saleUser)) { pd.put("saleUserEqual", "1"); }else {
 * pd.put("saleUserEqual", "0"); } }
 * 
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: targetCustomerInfoBySale
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年3月8日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="targetCustomerInfoBySale",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object targetCustomerInfoBySale(){ PageData pd=new
 * PageData(); try { pd=this.getPageData(); pd=saleLeadsService.findById(pd);
 * 
 * String targetCustomerInfoId=pd.getString("targetCustomerInfoId");
 * if(StringUtils.isNotEmpty(targetCustomerInfoId)) { pd=new PageData();
 * pd.put("targetCustomerInfoId", targetCustomerInfoId);
 * pd=targetCustomerInfoService.findById(pd); if(pd!=null) { pd.put("relate",
 * "1"); }else { pd=new PageData(); pd.put("relate", "0"); } }else { pd=new
 * PageData(); pd.put("relate", "0"); }
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 *//**
	 * @Title: projectDetail
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月26日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="projectDetail",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object projectDetail(){ PageData pd=new PageData(); try
 * { pd=this.getPageData();
 * 
 * pd=projectService.findById(pd);
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 *//**
	 * @Title: followRecordSave
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月10日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/followRecordSave",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object followRecordSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=followRecordService.saveApp(pd, mRequest); } catch (Exception e) { //
 * TODO: handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: progressMonitoringSave
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月26日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/progressMonitoringSave",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object progressMonitoringSave(HttpServletRequest
 * request) { PageData pd=new PageData(); Map<String,String> map=new
 * HashMap<String, String>(); String rtnInfo=""; try {
 * pd=this.getPageDataByReq(request); MultipartHttpServletRequest
 * mRequest=(MultipartHttpServletRequest)request;
 * map=progressSubmitService.saveApp(pd, mRequest); } catch (Exception e) { //
 * TODO: handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: appFileSave
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月11日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/appFileSave",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object appFileSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=uploadFileService.saveAppFile(pd, mRequest); } catch (Exception e) { //
 * TODO: handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: followRecordInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月10日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="followRecordInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object followRecordInfo(HttpServletRequest request){
 * PageData pd=new PageData(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request); list=followRecordService.listByKey(pd);
 * String json=JsonUtil.list2json(list); return json.toString(); } catch
 * (Exception e) { // TODO: handle exception logger.error(e.toString(), e); }
 * return null; }
 * 
 *//**
	 * @Title: projectProgressInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月26日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="projectProgressInfo",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object projectProgressInfo(HttpServletRequest request){
 * PageData pd=new PageData(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request); list=progressSubmitService.listByKey(pd);
 * String json=JsonUtil.list2json(list); return json.toString(); } catch
 * (Exception e) { // TODO: handle exception logger.error(e.toString(), e); }
 * return null; }
 * 
 *//**
	 * @Title: customerReqInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月19日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="customerReqInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerReqInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData(); String account=pd.getString("account");
 * 
 * pd=requestService.findById(pd);
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID"); pd.put("staffId",STAFF_ID); }
 * 
 * PageData custPd = new PageData(); custPd = custHandleService.findByReqId(pd);
 * 
 * if(custPd!=null) { pd.put("handleState","1");
 * pd.put("result",custPd.getString("result"));
 * pd.put("startTime",custPd.get("startTime").toString());
 * pd.put("endTime",custPd.get("endTime").toString());
 * pd.put("servDays",custPd.get("servDays").toString());
 * pd.put("servUserName",custPd.getString("servUserName")); }else {
 * pd.put("handleState","0"); } String json=JsonUtil.map2json(pd); return json;
 * } catch (Exception e) { // TODO: handle exception } return null; }
 * 
 *//**
	 * @Title: deleteCustomerReqInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月11日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/deleteCustomerReqInfo",produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object deleteCustomerReqInfo(){ PageData pd=new
 * PageData(); Map<String, String> map=new HashMap<String,String>(); String
 * rtnInfo=""; try { pd=this.getPageData();
 * 
 * requestService.delete(pd);
 * 
 * map.put("result", rtnInfo); } catch (Exception e) {
 * logger.error(e.toString(), e); map.put("errmsg",e.getMessage());
 * rtnInfo="error"; map.put("result", rtnInfo); } return
 * AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: activeServiceInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月20日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="activeServiceInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object activeServiceInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData(); String account=pd.getString("account");
 * 
 * pd=serviceService.findById(pd);
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID"); pd.put("staffId",STAFF_ID); }
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 *//**
	 * @Title: deleteActiveServiceInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月12日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/deleteActiveServiceInfo",produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object deleteActiveServiceInfo(){ PageData pd=new
 * PageData(); Map<String, String> map=new HashMap<String,String>(); String
 * rtnInfo=""; try { pd=this.getPageData();
 * 
 * serviceService.delete(pd);
 * 
 * map.put("result", rtnInfo); } catch (Exception e) {
 * logger.error(e.toString(), e); map.put("errmsg",e.getMessage());
 * rtnInfo="error"; map.put("result", rtnInfo); } return
 * AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: targetCustomerInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="targetCustomerInfo",method=RequestMethod.POST,produces
 * ="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object targetCustomerInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData(); pd=targetCustomerInfoService.findById(pd);
 * 
 * String targetCustomerInfoId=pd.getString("targetCustomerInfoId"); PageData
 * salePd=new PageData(); salePd.put("targetCustomerInfoId",
 * targetCustomerInfoId); salePd=saleLeadsService.findByTarget(salePd);
 * if(salePd!=null) { pd.put("hasMoveToSale","1"); }else {
 * pd.put("hasMoveToSale","0"); }
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 *//**
	 * @Title: contractListInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="contractListInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object contractListInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData(); String account=pd.getString("account");
 * 
 * pd=contractService.findById(pd);
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID"); pd.put("staffId",STAFF_ID); }
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 *//**
	 * @Title: customerListInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="customerListInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerListInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData(); pd=customersService.findById(pd);
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 *//**
	 * 
	 * @Title: workReportInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="workReportInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object workReportInfo(){ PageData pd=new PageData(); try
 * { pd=this.getPageData(); pd=workReportService.findById(pd); String
 * json=JsonUtil.map2json(pd); return json; } catch (Exception e) { // TODO:
 * handle exception } return null; }
 * 
 *//**
	 * @Title: deleteWorkReportInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月14日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/deleteWorkReportInfo",produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object deleteWorkReportInfo(){ PageData pd=new
 * PageData(); Map<String, String> map=new HashMap<String,String>(); String
 * rtnInfo=""; try { pd=this.getPageData();
 * 
 * workReportService.delete(pd);
 * 
 * map.put("result", rtnInfo); } catch (Exception e) {
 * logger.error(e.toString(), e); map.put("errmsg",e.getMessage());
 * rtnInfo="error"; map.put("result", rtnInfo); } return
 * AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: workReportSave
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/workReportSave",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object workReportSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * 
 * pd.put("readState", "0");
 * 
 * String workReportType=pd.getString("workReportType"); String
 * weekStartTime=pd.getString("weekStartTime"); String
 * weekEndTime=pd.getString("weekEndTime");
 * 
 * if(workReportType.equals("2")) { String weekStart
 * =DateUtil.dateToWeek(weekStartTime); String weekEnd
 * =DateUtil.dateToWeek(weekEndTime);
 * 
 * //开始日期不能大于结束日期 boolean tag=DateUtil.compareDateDayu(weekStartTime,
 * weekEndTime); if(tag) { map.put("errmsg","周报的开始日期不能大于结束日期！");
 * rtnInfo="error"; map.put("result", rtnInfo); return AppUtil.returnObject(new
 * PageData(), map); }
 * 
 * 
 * if(!weekStart.equals("星期一") || !weekEnd.equals("星期日")) {
 * map.put("errmsg","周报的开始和结束日期,只能选择星期一和星期日！"); rtnInfo="error";
 * map.put("result", rtnInfo); return AppUtil.returnObject(new PageData(), map);
 * }
 * 
 * String today=DateUtil.getDay();
 * 
 * int weekStartDay=(int)DateUtil.getDaySub(weekStartTime, today); int
 * weekEndDay=(int)DateUtil.getDaySub(weekEndTime, today);
 * 
 * if(weekStartDay>9 || weekEndDay>9) {
 * map.put("errmsg","周报的开始和结束日期,不能往前选择超过9天！"); rtnInfo="error";
 * map.put("result", rtnInfo); return AppUtil.returnObject(new PageData(), map);
 * }
 * 
 * }
 * 
 * 
 * 
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=workReportService.saveApp(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: workReportEdit
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月14日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/workReportEdit",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object workReportEdit(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=workReportService.editApp(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: workMonthInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="workMonthInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object workMonthInfo(){
 * 
 * List<Map<String, String>> dateLst = new ArrayList<Map<String, String>>();
 * Map<String,String> map=new HashMap<String, String>();
 * 
 * //获取当前月的前后2个月 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); Calendar
 * c=Calendar.getInstance(); c.setTime(new Date());
 * 
 * c.add(Calendar.MONTH,-2); map=new HashMap<String, String>();
 * map.put("monthStr",sdf.format(c.getTime())); dateLst.add(map);
 * 
 * c.add(Calendar.MONTH,+1); map=new HashMap<String, String>();
 * map.put("monthStr",sdf.format(c.getTime())); dateLst.add(map);
 * 
 * c.add(Calendar.MONTH,+1); map=new HashMap<String, String>();
 * map.put("monthStr",sdf.format(c.getTime())); dateLst.add(map);
 * 
 * c.add(Calendar.MONTH,+1); map=new HashMap<String, String>();
 * map.put("monthStr",sdf.format(c.getTime())); dateLst.add(map);
 * 
 * c.add(Calendar.MONTH,+1); map=new HashMap<String, String>();
 * map.put("monthStr",sdf.format(c.getTime())); dateLst.add(map);
 * 
 * String json=JsonUtil.list2json(dateLst); return json; }
 * 
 * 
 *//**
	 * 
	 * @Title: lastWorkMonthInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年3月15日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="lastWorkMonthInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object lastWorkMonthInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData();
 * 
 * pd.put("createUser",pd.getString("staffId"));
 * 
 * pd=workReportService.findByNew(pd); if(pd!=null) {
 * 
 * }else { pd=new PageData(); pd.put("workPlan", ""); }
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: myDayWorkReport
	 * @Description: 我的日报
	 * @author yk
	 * @date: 2019年1月3日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="myWorkReport",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myWorkReport(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("sort", "createTime"); pd.put("order",
 * "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=workReportService.querylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 *//**
	 * 
	 * @Title: tomyWorkReport
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="tomyWorkReport",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object tomyWorkReport(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("markUser", STAFF_ID); pd.put("sort", "createTime"); pd.put("order",
 * "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=workReportService.querylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: myCopyWorkReport
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="myCopyWorkReport",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myCopyWorkReport(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("copyStaffIds", STAFF_ID); pd.put("sort", "createTime");
 * pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=workReportService.querylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: helloPeps
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月21日
	 * @param: @return
	 * @return: ModelAndView
	 * @throws:
	 */
/*
 * @RequestMapping(value="/helloPeps") public ModelAndView helloPeps() {
 * ModelAndView mv=this.getModelAndView();
 * mv.setViewName("system/index/helloPeps"); return mv; }
 * 
 * @RequestMapping(value="/toRegister") public ModelAndView toRegister() {
 * ModelAndView mv=this.getModelAndView();
 * mv.setViewName("system/index/register"); return mv; }
 * 
 * 
 *//**
	 * 
	 * @Title: generateCheckCode
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @return
	 * @return: String
	 * @throws:
	 */
/*
 * public String generateCheckCode(){ String
 * random=(int)((Math.random()*9+1)*100000)+""; return random; }
 * 
 *//**
	 * 
	 * @Title: getCheckCode
	 * @Description: 获取验证码
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/getCheckCode",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object getCheckCode() { PageData pd=new PageData();
 * Map<String,String> map=new HashMap<String, String>(); String
 * rtnInfo="success"; try { pd=this.getPageData(); pd.put("USERNAME",
 * pd.getString("phone"));
 * 
 * PageData pdTag=new PageData(); pdTag=userService.findByUsername(pd);
 * if(pdTag!=null) { rtnInfo="hasReg"; map.put("result", rtnInfo); }else {
 * String checkCode=this.generateCheckCode();
 * 
 * String rtn=SmsUtils.sendCheckCode(checkCode,pd.getString("phone"));
 * JSONObject rtnObj=JSONObject.fromObject(rtn); String
 * code=rtnObj.getString("code"); if(code.equals("000000")) {
 * pdTag=registerService.findByPhone(pd); if(pdTag!=null) {//已经有过记录
 * pdTag.put("checkCode", checkCode); pdTag.put("sendTime", DateUtil.getTime());
 * registerService.edit(pdTag); map.put("result", rtnInfo); }else { pdTag=new
 * PageData(); pdTag.put("registerId", this.get32UUID()); pdTag.put("phone",
 * pd.getString("phone")); pdTag.put("checkCode", checkCode);
 * pdTag.put("sendTime", DateUtil.getTime()); registerService.save(pdTag);
 * map.put("result", rtnInfo); } }else { rtnInfo="errSms"; map.put("result",
 * rtnInfo); } }
 * 
 * } catch (Exception e) { map.put("errmsg",e.getMessage()); rtnInfo="error";
 * map.put("result", rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: saveRegister
	 * @Description: 验证码注册
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/saveRegister",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object saveRegister() { PageData pd=new PageData();
 * Map<String,String> map=new HashMap<String, String>(); String
 * rtnInfo="success"; try { pd=this.getPageData(); pd.put("USERNAME",
 * pd.getString("phone")); String checkCode=pd.getString("checkCode");
 * 
 * PageData pdTag=new PageData(); pdTag=userService.findByUsername(pd);
 * if(pdTag!=null) { rtnInfo="hasReg"; map.put("result", rtnInfo); }else {
 * pdTag=registerService.findByPhone(pd); if(pdTag!=null) {//已经有过记录 String
 * checkCodeTag=pdTag.getString("checkCode"); if(checkCode.equals(checkCodeTag))
 * { int differMinute=(int)pdTag.get("differMinute"); if(differMinute>10) {
 * rtnInfo="errChaoShi"; map.put("result", rtnInfo); }else { PageData
 * staffPd=new PageData(); staffPd.put("STAFF_ID", this.get32UUID());
 * 
 * //获取角色id，默认编码yanShi pdTag=new PageData(); pdTag.put("RNUMBER", "yanShi");
 * pdTag=roleService.findByRNUMBER(pdTag); staffPd.put("ROLE_ID",
 * pdTag.getString("ROLE_ID"));
 * 
 * //获取部门，默认编码001 pdTag=new PageData(); pdTag.put("BIANMA", "001");
 * pdTag=departmentService.findByBianma(pdTag);
 * staffPd.put("DEPARTMENT_ID",pdTag.getString("DEPARTMENT_ID"));
 * 
 * staffPd.put("BIANMA",pd.getString("phone"));
 * staffPd.put("NAME",pd.getString("linkman"));
 * staffPd.put("USERNAME",pd.getString("phone"));
 * staffPd.put("PASSWORD",pd.getString("PASSWORD")); staffPd.put("SEX","男");
 * staffPd.put("TEL",pd.getString("phone"));
 * staffPd.put("BZ",pd.getString("companyName"));
 * 
 * staffService.saveRegister(staffPd); registerService.deleteByPhone(pd);
 * map.put("result", rtnInfo); } }else { rtnInfo="errCheckCode";
 * map.put("result", rtnInfo); }
 * 
 * }else {//注册码发送过后换了一手机号 rtnInfo="errPhone"; map.put("result", rtnInfo); } }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: receive
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月24日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/receive",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object receive(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo="success"; try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * 
 * String STAFF_ID=staffPd.getString("STAFF_ID"); pd.put("taskReceiveId",
 * this.get32UUID()); //主键 pd.put("createUser",STAFF_ID);
 * pd.put("createTime",DateUtil.getTime()); taskReceiveService.save(pd);
 * map.put("result", rtnInfo); }else { map.put("errmsg","未获取到登录人员信息");
 * rtnInfo="error"; map.put("result", rtnInfo); }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: send
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/send",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object send(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo="success"; try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * 
 * String STAFF_ID=staffPd.getString("STAFF_ID"); pd.put("taskSendId",
 * this.get32UUID()); //主键 pd.put("createUser",STAFF_ID);
 * pd.put("createTime",DateUtil.getTime()); pd.put("taskSendStaffId",STAFF_ID);
 * map=taskSendService.save(pd);
 * 
 * String result=map.get("result"); if(result.equals("success")) {
 * PushUtils.sendToPc(map.get("loginUserName")); }
 * 
 * 
 * map.put("result", rtnInfo); }else { map.put("errmsg","未获取到登录人员信息");
 * rtnInfo="error"; map.put("result", rtnInfo); }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: submitLoad
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="taskInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object taskInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData();
 * 
 * String account=pd.getString("account"); String STAFF_ID="";
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * STAFF_ID=staffPd.getString("STAFF_ID"); }
 * 
 * 
 * if(StringUtils.isNotEmpty(pd.getString("smsId"))) { PageData
 * smsPd=smsService.findById(pd); smsPd.put("isRead","1");
 * smsService.edit(smsPd);
 * 
 * PushUtils.sendToPcRefresh(account);
 * 
 * }
 * 
 * pd=tkTaskService.findById(pd);
 * 
 * String taskReveiveStaffId=pd.getString("taskReveiveStaffId");
 * if(taskReveiveStaffId.equals(STAFF_ID)) { pd.put("hasMove", "0"); }else {
 * pd.put("hasMove", "1"); }
 * 
 * //获取最新的返回原因 PageData backPd=new PageData();
 * backPd=taskNoReceiveService.findNew(pd); if(backPd!=null) {
 * pd.put("taskNoReceiveMemo",backPd.getString("taskNoReceiveMemo")); }
 * backPd=null;
 * 
 * //获取最新不验收 PageData noCheckPd=new PageData();
 * noCheckPd=taskNoCheckService.findNew(pd); if(noCheckPd!=null) {
 * pd.put("taskNoCheckMemo",noCheckPd.getString("taskNoCheckMemo")); }
 * 
 * 
 * //获取最新的下达备注 //获取最新的下达备注 PageData sendPd=taskSendService.findNew(pd);
 * if(sendPd!=null) { pd.put("taskSendMemo", sendPd.getString("taskSendMemo"));
 * }
 * 
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 *//**
	 * 
	 * @Title: performanceInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月15日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="performanceInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object performanceInfo(){ PageData pd=new PageData();
 * try { String performance=""; //是否启用绩效管理 String PerformanceSet =
 * Tools.readTxtFile(Const.PerformanceSet);//读取WEBSOCKET配置
 * if(StringUtils.isNotEmpty(PerformanceSet)){ String strIW[] =
 * PerformanceSet.split("="); if(strIW.length == 2){ performance=strIW[1]; } }
 * 
 * pd.put("performance", performance);
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: smsInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月7日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="smsInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object smsInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData(); String account=pd.getString("account");
 * pd=smsService.findById(pd); pd.put("isRead","1"); smsService.edit(pd);
 * 
 * PushUtils.sendToPcRefresh(account);
 * 
 * String json=JsonUtil.map2json(pd); return json; } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: smsInfoNoRead
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年3月6日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="smsInfoNoRead",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object smsInfoNoRead(){ PageData pd=new PageData(); try
 * { pd=this.getPageData(); pd=smsService.findById(pd); String
 * json=JsonUtil.map2json(pd); return json; } catch (Exception e) { // TODO:
 * handle exception } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: noReceive
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月24日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/noReceive",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object noReceive(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo="success"; try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * 
 * String STAFF_ID=staffPd.getString("STAFF_ID"); pd.put("taskNoReceiveId",
 * this.get32UUID()); //主键 pd.put("createUser",STAFF_ID);
 * pd.put("createTime",DateUtil.getTime()); taskNoReceiveService.save(pd);
 * map.put("result", rtnInfo); }else { map.put("errmsg","未获取到登录人员信息");
 * rtnInfo="error"; map.put("result", rtnInfo); }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: noCheck
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/noCheck",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object noCheck(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo="success"; try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * 
 * String STAFF_ID=staffPd.getString("STAFF_ID"); pd.put("taskNoCheckId",
 * this.get32UUID()); //主键 pd.put("createUser",STAFF_ID);
 * pd.put("createTime",DateUtil.getTime()); taskNoCheckService.save(pd);
 * map.put("result", rtnInfo); }else { map.put("errmsg","未获取到登录人员信息");
 * rtnInfo="error"; map.put("result", rtnInfo); }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: myCompleteTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月24日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/myCompleteTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myCompleteTask(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("taskReveiveStaffId", STAFF_ID); pd.put("taskCompleteState", "1");
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: sms
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月7日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/sms",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object sms(HttpServletRequest request) { PageData pd=new
 * PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("fromUser", STAFF_ID); pd.put("sort", "isRead,sendTime");
 * pd.put("order", "asc,desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=smsService.queryReceivelistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: smsMy
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年3月6日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/smsMy",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object smsMy(HttpServletRequest request) { PageData
 * pd=new PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("toUser", STAFF_ID); pd.put("sort", "isRead,sendTime");
 * pd.put("order", "asc,desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=smsService.queryReceivelistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 * 
 * 
 *//**
	 * @Title: smsNoRead
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月22日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/smsNoRead",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object smsNoRead(HttpServletRequest request) { PageData
 * pd=new PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("toUser", STAFF_ID); pd.put("isRead","0"); pd.put("sort",
 * "isRead,sendTime"); pd.put("order", "asc,desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=smsService.queryReceivelistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * @Title: smsRead
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月22日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/smsRead",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object smsRead(HttpServletRequest request) { PageData
 * pd=new PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("toUser", STAFF_ID); pd.put("isRead","1"); pd.put("sort",
 * "isRead,sendTime"); pd.put("order", "asc,desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=smsService.queryReceivelistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * @Title: smsSaveSend
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月22日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/smsSaveSend",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object smsSaveSend(HttpServletRequest request){ PageData
 * pd=new PageData(); Map<String, String> map=new HashMap<String,String>();
 * String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd);
 * 
 * if(staffPd!=null) { String STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("smsId", this.get32UUID()); //主键 pd.put("type","sms"); //主键
 * pd.put("fromUser", STAFF_ID); pd.put("isRead","0"); smsService.save(pd);
 * rtnInfo="success"; map.put("result", rtnInfo); }else { return null; } } catch
 * (Exception e) { // TODO: handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: noCompleteTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/noCompleteTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object noCompleteTask(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("taskReveiveStaffId", STAFF_ID); pd.put("taskReveiveState", "1");
 * pd.put("taskCompleteState", "0"); pd.put("sort", "createTime");
 * pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: myShenHeTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/myShenHeTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myShenHeTask(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * 
 * pd.put("taskCheckState", "1"); pd.put("taskConfirmState", "1");
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: myShenHeTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/myNoShenHeTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myNoShenHeTask(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) {
 * 
 * pd.put("taskCheckState", "1"); pd.put("taskConfirmState", "0");
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * 
	 * @Title: myCopyTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/myCopyTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myCopyTask(HttpServletRequest request) { PageData
 * pd=new PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("taskCopyStaffIds", STAFF_ID); pd.put("sort", "createTime");
 * pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: myNoCheckTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/myNoCheckTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myNoCheckTask(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("taskCheckStaffId", STAFF_ID); pd.put("taskCompleteState", "1");
 * pd.put("taskCheckState", "0"); pd.put("sort", "createTime"); pd.put("order",
 * "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 * 
 *//**
	 * 
	 * @Title: myCheckTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/myCheckTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myCheckTask(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("taskCheckStaffId", STAFF_ID); pd.put("taskCheckState", "1");
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 * 
 *//**
	 * 
	 * @Title: myNoSendTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/myNoSendTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myNoSendTask(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("taskSendState", "0"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 * 
 *//**
	 * 
	 * @Title: mySendTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/mySendTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object mySendTask(HttpServletRequest request) { PageData
 * pd=new PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("taskSendState", "1"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * @Title: customerReqNoEnd
	 * @Description: 客户请求单-未结案
	 * @author: sr
	 * @date: 2019年2月19日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerReqNoEnd",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerReqNoEnd(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("status", "0"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=requestService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: customerReqEnd
	 * @Description: 客户请求单-已结案
	 * @author: sr
	 * @date: 2019年2月19日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerReqEnd",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerReqEnd(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("status", "1"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=requestService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: activeServiceNoEnd
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月20日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/activeServiceNoEnd",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object activeServiceNoEnd(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("status", "0"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=serviceService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: activeServiceEnd
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月20日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/activeServiceEnd",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object activeServiceEnd(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("status", "1"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=serviceService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e); } return null; }
 * 
 * 
 *//**
	 * @Title: targetCustomerList
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/targetCustomerList",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object targetCustomerList(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("sort", "createTime"); pd.put("order",
 * "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=targetCustomerInfoService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: contractList
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/contractList",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object contractList(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * //String account=pd.getString("account");
 * 
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=contractService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); } catch (Exception e) { logger.error(e.toString(), e);
 * } return null; }
 * 
 *//**
	 * @Title: customerList
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerList",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerList(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * //String account=pd.getString("account");
 * 
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=customersService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); } catch (Exception e) { logger.error(e.toString(), e);
 * } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: noReceiveTask
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月24日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="noReceiveTask",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object noReceiveTask(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("taskReveiveStaffId", STAFF_ID); pd.put("taskSendState", "1");
 * pd.put("taskReveiveState", "0"); pd.put("sort", "createTime");
 * pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 *//**
	 * @Title: myProject
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月26日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="myProject",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object myProject(HttpServletRequest request){ PageData
 * pd=new PageData(); Page page=new Page(); List<PageData> list=null; try {
 * pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("projectManagerId", STAFF_ID); pd.put("sort", "createTime");
 * pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=projectService.appQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 *//**
	 * @Title: projectMonitoring
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月26日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="projectMonitoring",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object projectMonitoring(HttpServletRequest request){
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * pd.put("sort", "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=projectService.tracklistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString();
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null; }
 * 
 *//**
	 * 
	 * @Title: taskSaveSend
	 * @Description: 任务保存下达
	 * @author: HB-PC-042
	 * @date: 2018年10月23日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/taskSaveSend",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object taskSaveSend(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=tkTaskService.saveAppTask(pd, mRequest); String result=map.get("result");
 * if(result.equals("success")) { PushUtils.sendToPc(map.get("loginUserName"));
 * }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: customerReqSave
	 * @Description: 客户请求单
	 * @author: HB-PC-042
	 * @date: 2018年12月5日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerReqSave",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerReqSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=requestService.saveAppReq(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: customerReqEdit
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月11日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerReqEdit",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerReqEdit(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=requestService.editAppReq(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: customerReqHandleSave
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月19日
	 * @param: @param  files
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerReqHandleSave",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerReqHandleSave(HttpServletRequest
 * request){ PageData pd=new PageData(); Map<String, String> map=new
 * HashMap<String,String>(); String rtnInfo=""; try {
 * pd=this.getPageDataByReq(request); MultipartHttpServletRequest
 * mRequest=(MultipartHttpServletRequest)request;
 * map=custHandleService.saveAppReq(pd,mRequest); } catch (Exception e) {
 * logger.error(e.toString(), e); map.put("errmsg",e.getMessage());
 * rtnInfo="error"; map.put("result", rtnInfo); } return
 * AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: customerReqFinish
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月19日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/customerReqFinish",produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerReqFinish() { PageData pd=new PageData();
 * Map<String,String> map=new HashMap<String, String>(); String rtnInfo=""; try
 * { pd=this.getPageData();
 * 
 * String ArrayKeyIdS[] = pd.getString("reqId").split(",");
 * 
 * requestService.editState(ArrayKeyIdS); rtnInfo="success"; map.put("result",
 * rtnInfo); } catch (Exception e) { // TODO: handle exception
 * logger.error(e.toString(), e); map.put("errmsg",e.getMessage());
 * rtnInfo="error"; map.put("result", rtnInfo); } return
 * AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: activeServiceFinish
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月20日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/activeServiceFinish",produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object activeServiceFinish() { PageData pd=new
 * PageData(); Map<String,String> map=new HashMap<String, String>(); String
 * rtnInfo=""; try { pd=this.getPageData();
 * 
 * String ArrayKeyIdS[] = pd.getString("servId").split(",");
 * 
 * serviceService.editState(ArrayKeyIdS); rtnInfo="success"; map.put("result",
 * rtnInfo); } catch (Exception e) { // TODO: handle exception
 * logger.error(e.toString(), e); map.put("errmsg",e.getMessage());
 * rtnInfo="error"; map.put("result", rtnInfo); } return
 * AppUtil.returnObject(new PageData(), map); }
 * 
 * 
 *//**
	 * 
	 * @Title: activeServiceSave
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年12月5日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/activeServiceSave",method=RequestMethod.POST,produces
 * ="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object activeServiceSave(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=serviceService.saveAppReq(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * @Title: activeServiceEdit
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年3月12日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/activeServiceEdit",method=RequestMethod.POST,produces
 * ="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object activeServiceEdit(HttpServletRequest request) {
 * PageData pd=new PageData(); Map<String,String> map=new HashMap<String,
 * String>(); String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
 * map=serviceService.editAppReq(pd, mRequest); } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e);
 * map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(new PageData(), map); }
 * 
 *//**
	 * 
	 * @Title: taskSubmit
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/taskSubmit",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object taskSubmit(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * pd.put("tkProgressSubmitId", this.get32UUID()); //主键
 * pd.put("createTime",DateUtil.getTime()); MultipartHttpServletRequest
 * mRequest=(MultipartHttpServletRequest)request;
 * map=tkProgressSubmitService.saveAppSubmit(pd, mRequest);
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 * 
 *//**
	 * 
	 * @Title: taskShenHe
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/taskShenHe",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object taskShenHe(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * pd.put("taskConfirmId", this.get32UUID()); //主键
 * pd.put("createTime",DateUtil.getTime()); MultipartHttpServletRequest
 * mRequest=(MultipartHttpServletRequest)request;
 * map=taskConfirmService.saveAppShenHe(pd, mRequest);
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: taskCheck
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/taskCheck",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object taskCheck(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * pd.put("tkCheckSubmitId", this.get32UUID()); //主键 MultipartHttpServletRequest
 * mRequest=(MultipartHttpServletRequest)request;
 * map=tkCheckSubmitService.saveAppCheck(pd, mRequest);
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 * 
 *//**
	 * 
	 * @Title: staffInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月23日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="staffInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object staffInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData(); List<PageData> list=staffService.listAll(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: stageInfo
	 * @Description: TODO
	 * @author: sr
	 * @date: 2019年2月26日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="stageInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object stageInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData(); List<PageData>
 * list=stageService.listAllByProjectId(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: curStaffInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="curStaffInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object curStaffInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData(); String account=pd.getString("account"); PageData
 * staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * json=JsonUtil.map2json(staffPd); return json; } } catch (Exception e) { //
 * TODO: handle exception } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: projectInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月23日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="projectInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object projectInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData(); List<PageData> list=projectService.listAll(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: parentTaskInfo
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年3月15日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="parentTaskInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object parentTaskInfo(){ PageData pd=new PageData(); try
 * { pd=this.getPageData(); List<PageData> list=tkTaskService.listForSelect(pd);
 * String json=JsonUtil.list2json(list); return json; } catch (Exception e) { //
 * TODO: handle exception logger.error(e.toString(), e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: projectStageInfo
	 * @Description:项目阶段信息
	 * @author: HB-PC-042
	 * @date: 2018年12月7日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="projectStageInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object projectStageInfo(){ PageData pd=new PageData();
 * try { pd=this.getPageData(); List<PageData>
 * list=stageService.listAllByProjectId(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: customerInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年12月5日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="customerInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object customerInfo(){ PageData pd=new PageData(); try {
 * pd=this.getPageData(); List<PageData> list=customersService.listAll(pd);
 * String json=JsonUtil.list2json(list); return json; } catch (Exception e) { //
 * TODO: handle exception logger.error(e.toString(), e); } return null; }
 * 
 * 
 *//**
	 * 
	 * @Title: menuInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月1日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="menuInfo",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object menuInfo(HttpServletRequest request){ PageData
 * pd=new PageData(); JSONObject rtn=new JSONObject(); try {
 * 
 * pd=this.getPageDataByReq(request); String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { PageData
 * pdRole=new PageData(); pdRole.put("userId",staffPd.getString("USER_ID"));
 * List<PageData> userRoleLst=userRoleService.listAllByUserId(pdRole);
 * if(userRoleLst!=null && userRoleLst.size()>0){ PageData menuPd=new
 * PageData(); menuPd.put("MENU_ID", "407"); List<Menu> menuList
 * =menuService.menulist(menuPd); if(menuList!=null && menuList.size()>0) {
 * menuList = this.readMenu(menuList, userRoleLst); if(menuList!=null &&
 * menuList.size()>0) { if(menuList.get(0).isHasMenu()) {
 * rtn.accumulate("hasMenu","yes"); }else { rtn.accumulate("hasMenu","no"); }
 * 
 * }else { rtn.accumulate("hasMenu","no"); } } } }else {
 * rtn.accumulate("hasMenu","no"); } } catch (Exception e) { // TODO: handle
 * exception logger.error(e.toString(), e); } return rtn.toString(); }
 * 
 * 
 *//**
	 * 
	 * @Title: readMenu
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年11月1日
	 * @param: @param  menuList
	 * @param: @param  userRoleLst
	 * @param: @return
	 * @return: List<Menu>
	 * @throws:
	 */
/*
 * public List<Menu> readMenu(List<Menu> menuList,List<PageData> userRoleLst){
 * for(int i=0;i<menuList.size();i++){ String roleRights=""; for(int
 * z=0;z<userRoleLst.size();z++){ if(menuList.get(i).isHasMenu())break;
 * roleRights=userRoleLst.get(z).getString("RIGHTS");
 * menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights,
 * menuList.get(i).getMENU_ID())); } } return menuList; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: taskFile
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月25日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="taskFile",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object taskFile(){ PageData pd=new PageData(); try {
 * pd=this.getPageData();
 * 
 * pd.put("tableKeyValue", pd.getString("taskId"));
 * pd.put("tableName","TK_task");
 * 
 * List<PageData> list=uploadFileService.listByKey(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: appFile
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月9日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="appFile",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object appFile(){ PageData pd=new PageData(); try {
 * pd=this.getPageData();
 * 
 * pd.put("tableKeyValue", pd.getString("tableKeyValue"));
 * pd.put("tableName",pd.getString("tableName"));
 * 
 * List<PageData> list=uploadFileService.listByKey(pd); String
 * json=JsonUtil.list2json(list); return json; } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: appLogin
	 * @Description: app验证登录
	 * @author: HB-PC-042
	 * @date: 2018年10月23日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/appLogin",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object appLogin(HttpServletRequest request) { PageData
 * pd=new PageData(); Map<String,String> map=new HashMap<String, String>();
 * String rtnInfo=""; try { pd=this.getPageDataByReq(request);
 * 
 * String USERNAME=pd.getString("account");//登录用户名 String
 * PASSWORD=pd.getString("password");//登录密码
 * 
 * String id=pd.getString("id"); String token=pd.getString("token"); String
 * clientid=pd.getString("clientid"); String appid=pd.getString("appid"); String
 * appkey=pd.getString("appkey");
 * 
 * 
 * if(StringUtils.isNotEmpty(USERNAME) && StringUtils.isNotEmpty(PASSWORD)) {
 * pd.put("USERNAME", USERNAME); String passwd = new SimpleHash("SHA-1",
 * USERNAME, PASSWORD).toString(); // 密码加密 pd.put("PASSWORD", passwd);
 * 
 * pd = userService.getUserByNameAndPwd(pd); //根据用户名和密码去读取用户信息 if(pd!=null){
 * 
 * pd=staffService.findByUserId(pd); if(pd!=null) { pd.put("id", id);
 * pd.put("token", token); pd.put("clientid", clientid); pd.put("appid", appid);
 * pd.put("appkey", appkey);
 * 
 * staffService.editAppInfo(pd);
 * 
 * } map.put("STAFF_ID",pd.getString("STAFF_ID")); rtnInfo="success";
 * map.put("result", rtnInfo); }else{ rtnInfo="usererror"; map.put("result",
 * rtnInfo); }
 * 
 * }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); }
 * 
 * return AppUtil.returnObject(new PageData(), map);
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 *//**
	 * 
	 * @Title: downApp
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月23日
	 * @param: @param  response
	 * @param: @throws Exception
	 * @return: void
	 * @throws:
	 */
/*
 * @RequestMapping(value="/downApp") public void downApp(HttpServletResponse
 * response)throws Exception{ String fileName="io.hibao.PEPSApp.apk";
 * FileDownload.fileDownload(response, PathUtil.getClasspath() +
 * Const.APPFILEPATHFILE + fileName, fileName); }
 * 
 * 
 *//**
	 * 
	 * @Title: downFile
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月26日
	 * @param: @param  response
	 * @param: @throws Exception
	 * @return: void
	 * @throws:
	 */
/*
 * @RequestMapping(value="/downFile") public void downFile(HttpServletRequest
 * request,HttpServletResponse response)throws Exception{ PageData pd=new
 * PageData(); pd=this.getPageDataByReq(request);
 * pd=uploadFileService.findById(pd); FileDownload.fileDownload(response,
 * PathUtil.getClasspath() + Const.FILEPATHFILE + pd.getString("fileRealName"),
 * pd.getString("fileName")); }
 * 
 * 
 * 
 *//**
	 * @Title:checkAppUpdate @Description: TODO @author yk @date
	 * 2018年7月20日 @param @return @return Object @throws
	 */
/*
 * @RequestMapping(value="checkAppUpdate",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object checkAppUpdate(){ PageData pd=new PageData();
 * Map<String,String> map=new HashMap<String,String>(); String rtnInfo=""; try {
 * pd=this.getPageData();
 * 
 * String version=pd.getString("version");
 * 
 * pd.put("dataSetTypeCode", "BBH");
 * 
 * List<PageData> list = null;
 * 
 * list=sysDataSetService.findByCode(pd); if(list!=null && list.size()>0) {
 * PageData pd1 = list.get(0); if(pd1!=null) { String
 * newVerion=pd1.getString("dataSetValue"); if(StringUtils.isNotEmpty(newVerion)
 * && StringUtils.isNotEmpty(version)){ if(version.equals(newVerion)){
 * rtnInfo="error"; map.put("result", rtnInfo); }else{ rtnInfo="success";
 * map.put("result", rtnInfo); } }else{ rtnInfo="error"; map.put("result",
 * rtnInfo); } }else{ rtnInfo="error"; map.put("result", rtnInfo); } }else {
 * rtnInfo="error"; map.put("result", rtnInfo); }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(pd, map); }
 * 
 * 
 *//**
	 * 
	 * @Title: changePwd
	 * @Description: 修改密码
	 * @author: HB-PC-042
	 * @date: 2018年10月23日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="changePwd",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object changePwd(){ PageData pd=new PageData();
 * Map<String,String> map=new HashMap<String,String>(); String rtnInfo=""; try {
 * pd=this.getPageData();
 * 
 * String account=pd.getString("account"); String newPwd=pd.getString("newPwd");
 * 
 * pd.put("USERNAME", account); pd=userService.findByUsername(pd); if(pd!=null){
 * if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
 * pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"),
 * newPwd).toString()); userService.updatePwd(pd); //执行修改 rtnInfo="success";
 * map.put("result", rtnInfo); } }else{ rtnInfo="error"; map.put("result",
 * rtnInfo); map.put("errmsg", "未找到该用户信息"); }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); map.put("errmsg",e.getMessage()); rtnInfo="error"; map.put("result",
 * rtnInfo); } return AppUtil.returnObject(pd, map); }
 * 
 *//**
	 * 
	 * @Title: monthJieXiao
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月30日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/monthJieXiao",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object monthJieXiao(HttpServletRequest request) {
 * PageData pd=new PageData(); pd=this.getPageDataByReq(request); JSONObject
 * jsonRtn=new JSONObject(); JSONArray nameArray=new JSONArray(); JSONArray
 * jiXiaoDataArray=new JSONArray();
 * 
 * try {
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * 
 * String Jan="0"; String Feb="0"; String Mar="0"; String Apr="0"; String
 * May="0"; String Jun="0"; String Jul="0"; String Aug="0"; String Sept="0";
 * String Oct="0"; String Nov="0"; String Dec="0";
 * 
 * for(int i=1;i<=12;i++) { nameArray.add(i+""); }
 * 
 * pd.put("nowYear",DateUtil.getYear()); pd.put("taskReveiveStaffId",STAFF_ID);
 * 
 * 
 * String performance=""; //是否启用绩效管理 String PerformanceSet =
 * Tools.readTxtFile(Const.PerformanceSet);//读取WEBSOCKET配置
 * if(StringUtils.isNotEmpty(PerformanceSet)){ String strIW[] =
 * PerformanceSet.split("="); if(strIW.length == 2){ performance=strIW[1]; } }
 * 
 * List<PageData> list=null;
 * 
 * if(performance.equals("noStartUse")) { list=tkTaskService.monthTask(pd);
 * }else { list=tkTaskService.monthJieXiao(pd); }
 * 
 * 
 * if(list!=null) { for(int i=0;i<list.size();i++) { pd=list.get(i);
 * 
 * String monthStr=pd.get("monthStr").toString(); if(monthStr.length()==1) {
 * monthStr="0"+monthStr; }
 * 
 * if(monthStr.equals("01"))Jan=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("02"))Feb=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("03"))Mar=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("04"))Apr=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("05"))May=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("06"))Jun=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("07"))Jul=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("08"))Aug=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("09"))Sept=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("10"))Oct=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("11"))Nov=pd.get("jiXiaoTimes").toString();
 * if(monthStr.equals("12"))Dec=pd.get("jiXiaoTimes").toString(); } }
 * 
 * 
 * jiXiaoDataArray.add(Jan); jiXiaoDataArray.add(Feb); jiXiaoDataArray.add(Mar);
 * jiXiaoDataArray.add(Apr); jiXiaoDataArray.add(May); jiXiaoDataArray.add(Jun);
 * jiXiaoDataArray.add(Jul); jiXiaoDataArray.add(Aug);
 * jiXiaoDataArray.add(Sept); jiXiaoDataArray.add(Oct);
 * jiXiaoDataArray.add(Nov); jiXiaoDataArray.add(Dec);
 * 
 * jsonRtn.accumulate("name", nameArray); jsonRtn.accumulate("jiXiaoData",
 * jiXiaoDataArray);
 * 
 * 
 * pd=new PageData(); pd.put("planEndTime",DateUtil.getMonthFirstDay());
 * pd.put("planEndTime2",DateUtil.getMonthLastDay());
 * pd.put("taskReveiveStaffId",STAFF_ID);
 * 
 * int hasComplete=0; int noComplete=0;
 * 
 * int noReceive=0; int receiveNoComplete=0; int completeNoCheck=0; int
 * checkNOShenHe=0; int shenHe=0;
 * 
 * int taskReveiveState=0; int taskCompleteState=0; int taskCheckState=0; int
 * taskConfirmState=0;
 * 
 * list=tkTaskService.monthTaskFenBu(pd); if(list!=null) { for(int
 * i=0;i<list.size();i++) { pd=list.get(i);
 * taskReveiveState=(Integer)pd.get("taskReveiveState");
 * taskCompleteState=(Integer)pd.get("taskCompleteState");
 * taskCheckState=(Integer)pd.get("taskCheckState");
 * taskConfirmState=(Integer)pd.get("taskConfirmState");
 * 
 * //完成，未完成 if(taskCompleteState==0) { noComplete=noComplete+1; }
 * if(taskCompleteState==1) { hasComplete=hasComplete+1; }
 * 
 * //未接收 if(taskReveiveState==0) { noReceive=noReceive+1; } //已接收未完成
 * if(taskReveiveState==1 && taskCompleteState==0 ) {
 * receiveNoComplete=receiveNoComplete+1; }
 * 
 * //已完成未验收 if(taskReveiveState==1 && taskCompleteState==1 && taskCheckState==0)
 * { completeNoCheck=completeNoCheck+1; }
 * 
 * if(!performance.equals("noStartUse")) { //已验收未审核 if(taskReveiveState==1 &&
 * taskCompleteState==1 && taskCheckState==1 && taskConfirmState==0) {
 * checkNOShenHe=checkNOShenHe+1; } }else { //已验收 if(taskReveiveState==1 &&
 * taskCompleteState==1 && taskCheckState==1) { checkNOShenHe=checkNOShenHe+1; }
 * }
 * 
 * //已审核 if(taskReveiveState==1 && taskCompleteState==1 && taskCheckState==1 &&
 * taskConfirmState==1) { shenHe=shenHe+1; }
 * 
 * 
 * } }
 * 
 * JSONArray nameArray2=new JSONArray(); JSONArray FenBuNumArray=new
 * JSONArray();
 * 
 * 
 * nameArray2.add("未接收"); nameArray2.add("未完成"); nameArray2.add("未验收");
 * 
 * if(!performance.equals("noStartUse")) { nameArray2.add("未审核");
 * nameArray2.add("已审核"); }else { nameArray2.add("已验收"); }
 * 
 * FenBuNumArray.add(noReceive); FenBuNumArray.add(receiveNoComplete);
 * FenBuNumArray.add(completeNoCheck); if(!performance.equals("noStartUse")) {
 * FenBuNumArray.add(checkNOShenHe); FenBuNumArray.add(shenHe); }else {
 * FenBuNumArray.add(checkNOShenHe); }
 * 
 * jsonRtn.accumulate("hasComplete", hasComplete);
 * jsonRtn.accumulate("noComplete", noComplete);
 * 
 * 
 * jsonRtn.accumulate("fenBuName", nameArray2);
 * jsonRtn.accumulate("fenBuNumData", FenBuNumArray);
 * 
 * 
 * }
 * 
 * } catch (Exception e) { // TODO: handle exception } return
 * jsonRtn.toString(); }
 * 
 *//**
	 * @Title: createTaskNoSend
	 * @Description: 项目任务未下达
	 * @author: sr
	 * @date: 2019年2月15日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/createTaskNoSend",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object createTaskNoSend(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("taskSendState", "0"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appProjectQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; } } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: createTaskSend
	 * @Description: 项目任务已下达
	 * @author: sr
	 * @date: 2019年2月15日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/createTaskSend",method=RequestMethod.POST,produces=
 * "text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object createTaskSend(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("taskSendState", "1"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appProjectQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; }
 * 
 * } catch (Exception e) { // TODO: handle exception logger.error(e.toString(),
 * e); } return null;
 * 
 * }
 * 
 *//**
	 * @Title: createTaskDailyNoSend
	 * @Description: 日常任务未下达
	 * @author: sr
	 * @date: 2019年2月15日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/createTaskDailyNoSend",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object createTaskDailyNoSend(HttpServletRequest request)
 * { PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("taskSendState", "0"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appDailyQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; } } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: createTaskDailySend
	 * @Description: 日常任务已下达
	 * @author: sr
	 * @date: 2019年2月15日
	 * @param: @param  request
	 * @param: @return
	 * @return: Object
	 * @throws:
	 */
/*
 * @RequestMapping(value="/createTaskDailySend",method=RequestMethod.POST,
 * produces="text/html;charset=UTF-8")
 * 
 * @ResponseBody public Object createTaskDailySend(HttpServletRequest request) {
 * PageData pd=new PageData(); Page page=new Page(); List<PageData> list=null;
 * try { pd=this.getPageDataByReq(request);
 * 
 * String account=pd.getString("account");
 * 
 * PageData staffPd=new PageData(); staffPd.put("USERNAME",account);
 * staffPd=staffService.findByUSERNAME(staffPd); if(staffPd!=null) { String
 * STAFF_ID=staffPd.getString("STAFF_ID");
 * 
 * pd.put("createUser", STAFF_ID); pd.put("taskSendState", "1"); pd.put("sort",
 * "createTime"); pd.put("order", "desc");
 * 
 * String pageNumber=pd.getString("page");//当前页 if(Tools.notEmpty(pageNumber)){
 * page.setCurrentPage(Integer.valueOf(pageNumber)); } String
 * pageSize=pd.getString("rows");//每页数量 if(Tools.notEmpty(pageSize)){
 * page.setShowCount(Integer.valueOf(pageSize)); }
 * 
 * page.setPd(pd);
 * 
 * list=tkTaskService.appDailyQuerylistPage(page); String
 * json=JsonUtil.list2json(list); JSONObject jo=new JSONObject();
 * jo.put("total", Integer.valueOf(page.getTotalResult())); jo.put("rows",
 * json);
 * 
 * return jo.toString(); }else { return null; } } catch (Exception e) { // TODO:
 * handle exception logger.error(e.toString(), e); } return null; }
 * 
 *//**
	 * @Title: read
	 * @Description: 工作报告批阅
	 * @author: sr
	 * @date: 2019年2月15日
	 * @param: @return
	 * @return: Object
	 * @throws:
	 *//*
		 * @RequestMapping(value="/read",method=RequestMethod.POST,produces=
		 * "text/html;charset=UTF-8")
		 * 
		 * @ResponseBody public Object read(){ PageData pd=new PageData(); Map<String,
		 * String> map=new HashMap<String,String>(); String rtnInfo=""; try {
		 * pd=this.getPageData();
		 * 
		 * pd.put("readState","1");
		 * 
		 * workReportService.read(pd); rtnInfo="success";
		 * 
		 * map.put("result", rtnInfo); } catch (Exception e) {
		 * logger.error(e.toString(), e); map.put("errmsg",e.getMessage());
		 * rtnInfo="error"; map.put("result", rtnInfo); } return
		 * AppUtil.returnObject(new PageData(), map); }
		 * 
		 * }
		 */