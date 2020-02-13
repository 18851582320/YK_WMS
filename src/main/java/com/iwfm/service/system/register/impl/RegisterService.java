package com.iwfm.service.system.register.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.service.system.register.RegisterManager;
import com.iwfm.util.PageData;

/**
 * 
 * ClassName: RegisterService 
 * @Description: TODO
 * @author yk
 * @date: 2018年12月25日
 */
@Service("registerService")
public class RegisterService implements RegisterManager {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	/**
	 * @Title: save
	 * @Description: 保存
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @throws
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("RegisterMapper.save", pd);
	}

	/**
	 * @Title: edit
	 * @Description: 编辑
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @throws
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("RegisterMapper.edit", pd);
	}
	
	

	/**
	 * @Title: findByPhone
	 * @Description: 通过电话号码查询数据
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	@Override
	public PageData findByPhone(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RegisterMapper.findByPhone", pd);
	}
	
	/**
	 * 
	 * @Title: deleteByPhone
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年12月26日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void deleteByPhone(PageData pd)throws Exception{
		dao.delete("RegisterMapper.deleteByPhone", pd);
	}
	
}
