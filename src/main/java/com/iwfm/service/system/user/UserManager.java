package com.iwfm.service.system.user;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.entity.system.User;
import com.iwfm.util.PageData;

/**
 * ClassName: UserManager 
 * @Description: 用户接口
 * @author yk
 * @date 2017年8月1日
 */
public interface UserManager {
	/**
	 * @Title: getUserByNameAndPwd
	 * @Description: 登录判断
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData getUserByNameAndPwd(PageData pd)throws Exception;
	
	/**
	 * @Title: updateLastLogin
	 * @Description: 更新登录时间
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void updateLastLogin(PageData pd)throws Exception;
	
	/**
	 * @Title: updatePwd
	 * @Description: 修改密码
	 * @author yk
	 * @date 2017年9月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void updatePwd(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title:updateLanguage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年9月19日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void updateLanguage(PageData pd)throws Exception;
		
	/**
	 * @Title: getUserAndRoleById
	 * @Description: 通过用户ID获取用户信息和角色信息
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param USER_ID
	 * @param @return
	 * @param @throws Exception
	 * @return User
	 * @throws
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception;
	
	/**
	 * @Title: findByUsername
	 * @Description: 通过USERNAEME获取数据
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findByUsername(PageData pd)throws Exception;
	
	/**
	 * @Title: 列出某角色下的所有用户
	 * @Description: TODO
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return List<PageData>
	 * @throws
	 */
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception;
	
	/**
	 * @Title: saveIP
	 * @Description: 保存用户IP
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveIP(PageData pd)throws Exception;
	
	/**
	 * @Title:editRoleId
	 * @Description: 修改角色
	 * @author yk
	 * @date 2018年5月29日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void editRoleId(PageData pd)throws Exception;

	/**
	 * @Title: saveSKIN
	 * @Description: 换肤
	 * @author yk
	 * @date 2017年8月25日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveSKIN(PageData pd)throws Exception;
	
	/**
	 * @Title:saveWitSKIN
	 * @Description: 设置Wit皮肤
	 * @author yk
	 * @date 2018年2月27日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveWitSkin(PageData pd)throws Exception;
	
	
	/**
	 * @Title: listUsers
	 * @Description: 用户列表(组件)
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception
	 * @return List<PageData>
	 * @throws
	 */
	
	public List<PageData> listUsers(Page page)throws Exception;
	
	
	/**
	 * @Title:querylistPage
	 * @Description: 用户列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> querylistPage(Page page)throws Exception;
	
	/**
	 * @Title: listUsersBystaff
	 * @Description: 用户列表(弹窗选择用)
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception
	 * @return List<PageData>
	 * @throws
	 */
	public List<PageData> listUsersBystaff(Page page)throws Exception;
	
	/**
	 * @Title: findByUE
	 * @Description: 通过邮箱获取数据
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findByUE(PageData pd)throws Exception;
	
	/**
	 * @Title: findByUN
	 * @Description: 通过编号获取数据
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findByUN(PageData pd)throws Exception;
	
	/**
	 * @Title: findById
	 * @Description: 通过Id获取数据
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * @Title: editU
	 * @Description: 修改用户
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * @Title: saveU
	 * @Description: 保存用户
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * @Title:saveBatch
	 * @Description: 批量保存
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param pdLst
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveBatch(List<PageData> pdLst)throws Exception;
	
	
	/**
	 * @Title:savePwdReset
	 * @Description: 密码重置
	 * @author yk
	 * @date 2018年4月25日
	 * @param @param USER_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void savePwdReset(String[] USER_IDS)throws Exception;
	
	/**
	 * @Title: delete
	 * @Description: 删除用户
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * @Title: deleteAllU
	 * @Description: 批量删除用户
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param USER_IDS
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception;
	
	/**
	 * @Title: listAllUser
	 * @Description: 用户列表(全部)
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return List<PageData>
	 * @throws
	 */
	public List<PageData> listAllUser(PageData pd)throws Exception;
	
	/**
	 * @Title: getUserCount
	 * @Description: 获取总数
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param value
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData getUserCount(String value)throws Exception;
	
	/**
	 * @Title:listUserForStaff
	 * @Description: 用户（员工添加是选择）
	 * @author yk
	 * @date 2018年2月24日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listUserForStaff(PageData pd)throws Exception;
	
	
}
