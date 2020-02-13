package com.iwfm.service.system.sysSet.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.service.system.sysSet.SysSetManager;
import com.iwfm.util.PageData;


/**
 * ClassName: SysSetService 
 * @Description: 系统设置
 * @author yk
 * @date 2018年6月5日
 */
@Service("sysSetService")
public class SysSetService implements SysSetManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * @Title: save
	 * @Description:新增
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysSetMapper.save", pd);
	}
	
	/**
	 * @Title: delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysSetMapper.delete", pd);
	}
	
	/**
	 * @Title: edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void edit(PageData pd)throws Exception{
		if(pd!=null){
			Set<String> keySet=pd.keySet();
			if(keySet!=null && keySet.size()>0){
				PageData pd2=new PageData();
				for(String key:keySet){
					pd2.put("setFieldCode",key);
					pd2.put("setFieldValue",pd.getString(key));
					dao.update("SysSetMapper.edit", pd2);
				}
			}
		}
	}
	
	
	/**
	 * @Title: listAll
	 * @Description: 列表(全部)
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysSetMapper.listAll", pd);
	}
	
	/**
	 * @Title: findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysSetMapper.findById", pd);
	}
	
	/**
	 * @Title:findByFieldCode
	 * @Description: TODO
	 * @author yk
	 * @date 2018年7月20日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByFieldCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysSetMapper.findByFieldCode", pd);
	}
	
	
	/**
	 * @Title: deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysSetMapper.deleteAll", ArrayDATA_IDS);
	}
}
