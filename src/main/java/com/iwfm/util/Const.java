package com.iwfm.util;

import org.springframework.context.ApplicationContext;

/**
 * ClassName: Const 
 * @Description: TODO
 * @author yk
 * @date 2017年8月1日
 */
public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";	//验证码
	public static final String SESSION_USER = "sessionUser";				//session用的用户
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList";				//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";			//全部菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";			
	public static final String SESSION_USERROLE = "USERROLE";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";				//用户名
	public static final String SESSION_USER_ID = "USER_ID";				//用户名
	public static final String SESSION_USERSKIN = "USERSKIN";				//用户皮肤
	public static final String SESSION_USERWITSKIN = "USERWITSKIN";			//用户工位机皮肤
	public static final String DEPARTMENT_IDS = "DEPARTMENT_IDS";			//当前用户拥有的最高部门权限集合
	public static final String DEPARTMENT_ID = "DEPARTMENT_ID";				//当前用户拥有的最高部门权限
	public static final String STAFF_ID = "STAFF_ID";				        //当前账户所绑定的员工id
	public static final String STAFF_NAME = "STAFF_NAME";				    //当前账户所绑定的员工姓名
	public static final String STAFF_DEPARTMENT_ID = "STAFF_DEPARTMENT_ID";	//当前账户所绑定的员工的部门id
	public static final String STAFF_DEPARTMENT_NAME = "STAFF_DEPARTMENT_NAME";	//当前账户所绑定的员工的部门名称
	public static final String STATION_ID = "stationId";	//当前账户登陆时选择的工位id
	public static final String STATIONNAME = "stationName";	//当前账户登陆时选择的工位名称
	public static final String SESSION_RoleNUMBERS = "RNUMBERS";				//角色编码数组
	public static final String SESSION_language= "language";				//角色编码数组
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_toLogin.do";					//登录地址
	public static final String login_errorCode = "/login_errorCode.do";					//登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt";		//系统名称路径
	public static final String USERSKIN = "admin/config/USERSKIN.txt";		//系统名称路径
	public static final String USERWITSKIN = "admin/config/USERWITSKIN.txt";		//系统名称路径
	public static final String PAGE	= "admin/config/PAGE.txt";				//分页条数配置路径
	public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";	//WEBSOCKET配置路径
	public static final String FolderSet = "admin/config/FolderSet.txt";	//文档管理配置路径
	public static final String Performance = "Performance";				//是否启用绩效（session中）
	public static final String PerformanceSet = "admin/config/PerformanceSet.txt";	//绩效是否启用配置
	public static final String LOGINEDIT = "admin/config/LOGIN.txt";		//登录页面配置
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";		//图片上传路径
	public static final String FILEPATHFILE = "upDownloadFiles/file/";			//文件上传路径
	public static final String TemplateFILEPATHFILE = "upDownloadFiles/templatefile/";			//文件上传路径
	public static final String APPFILEPATHFILE = "upDownloadFiles/appfile/";			//app路径
	public static final String FILEPATHFILEOA = "upDownloadFiles/uploadFile/";	//文件上传路径(oa管理)
	public static final String FILEPATHTWODIMENSIONCODE = "upDownloadFiles/twoDimensionCode/"; //二维码存放路径
	public static final String FILEACTIVITI = "upDownloadFiles/activitiFile/";	//工作流生成XML和PNG目录
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(kanban)|(weChat)|(static)|(plugins)|(main)|(websocket)|(uploadImgs)).*";	//不对匹配该值的访问路径拦截（正则）
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	//系统用户注册接口_请求协议参数)
	public static final String[] SYSUSER_REGISTERED_PARAM_ARRAY = new String[]{"USERNAME","PASSWORD","NAME","EMAIL","rcode"};
	public static final String[] SYSUSER_REGISTERED_VALUE_ARRAY = new String[]{"用户名","密码","姓名","邮箱","验证码"};
	
	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
}
