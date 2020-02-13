package com.iwfm.service.system.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.entity.system.User;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.user.UserManager;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: UserService 
 * @Description: 用户
 * @author yk
 * @date 2017年8月2日
 */
@Service("userService")
public class UserService implements UserManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="logService")
	private LogManager logService;
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;
	
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
	public PageData getUserByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.getUserInfo", pd);
	}
	
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
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("UserMapper.updateLastLogin", pd);
	}
	
	
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
	public void updatePwd(PageData pd)throws Exception{
		dao.update("UserMapper.updatePwd", pd);
	}
	
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
	public void updateLanguage(PageData pd)throws Exception{
		dao.update("UserMapper.updateLanguage", pd);
	}
	
	
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
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}
	
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
	public PageData findByUsername(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findByUsername", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllUserByRoldId", pd);
		
	}
	
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
	public void saveIP(PageData pd)throws Exception{
		dao.update("UserMapper.saveIP", pd);
	}
	
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
	public void editRoleId(PageData pd)throws Exception{
		dao.update("UserMapper.editRoleId", pd);
	}
	
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
	public void saveSKIN(PageData pd)throws Exception{
		dao.update("UserMapper.saveSKIN", pd);
	}
	
	
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
	public void saveWitSkin(PageData pd)throws Exception{
		dao.update("UserMapper.saveWitSkin", pd);
	}
	
	
	/**
	 * @Title: listUsers
	 * @Description: 用户列表（组件）
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception
	 * @return List<PageData>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUsers(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.userlistPage", page);
	}
	
	/**
	 * @Title:querylistPage
	 * @Description: 用户列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.querylistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listUsersBystaff(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.userBystafflistPage", page);
	}
	
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
	public PageData findByUE(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findByUE", pd);
	}
	
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
	public PageData findByUN(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findByUN", pd);
	}
	
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
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findById", pd);
	}
	
	/**
	 * @Title: save
	 * @Description: 保存用户
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserMapper.save", pd);
		logService.save(Jurisdiction.getUsername(), "新增系统用户："+pd.getString("USERNAME"));
		//保存成功后反写流水号
		String codeRuleType=pd.getString("codeRuleType");
		if(StringUtils.isNotEmpty(codeRuleType)){
			codeRuleService.updateFormCodeSerialNum("sys_user", "NUMBER", codeRuleType);
		}
	}
	
	/**
	 * @Title:saveBatch
	 * @Description: 批量保存
	 * @author yk
	 * @date 2018年2月8日
	 * @param @param pdLst
	 * @param @throws Exception   
	 * @throws
	 */
	public void saveBatch(List<PageData> pdLst)throws Exception{
		for(int i=0;i<pdLst.size();i++){
			dao.save("UserMapper.save", pdLst.get(i));
		}
	}
	
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
	public void savePwdReset(String[] USER_IDS)throws Exception{
		if(USER_IDS!=null && USER_IDS.length>0){
			PageData pd=new PageData();
			for(int i=0;i<USER_IDS.length;i++){
				
				pd.put("USER_ID", USER_IDS[i]);
				pd=(PageData)dao.findForObject("UserMapper.findById", pd);
				if(pd!=null){
					pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"),"111111").toString());	//密码加密
				}
				dao.update("UserMapper.updatePwd", pd);
			}
		}
		
	}
	
	
	 
	/**
	 * @Title: edit
	 * @Description: 修改用户
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UserMapper.edit", pd);
		logService.save(Jurisdiction.getUsername(), "修改系统用户："+pd.getString("USERNAME"));
	}
	
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
	public void delete(PageData pd)throws Exception{
		dao.delete("UserMapper.delete", pd);
	}
	
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
	public void deleteAllU(String[] USER_IDS)throws Exception{
		dao.delete("UserMapper.deleteAllU", USER_IDS);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.listAllUser", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listUserForStaff(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.listUserForStaff", pd);
	}
	
	
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
	public PageData getUserCount(String value)throws Exception{
		return (PageData)dao.findForObject("UserMapper.getUserCount", value);
	}
	
}
