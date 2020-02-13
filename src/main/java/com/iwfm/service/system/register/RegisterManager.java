package com.iwfm.service.system.register;



import com.iwfm.util.PageData;

/**
 * 
 * ClassName: RegisterManager 
 * @Description: TODO
 * @author yk
 * @date: 2018年12月25日
 */
public interface RegisterManager {

	/**
	 * @Title: save
	 * @Description: 保存
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws: 
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * @Title: edit
	 * @Description: 编辑
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws: 
	 */
	public void edit(PageData pd)throws Exception;
	
	
	/**
	 * @Title: findByPhone
	 * @Description: 通过主键获取数据
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws: 
	 */
	public PageData findByPhone(PageData pd)throws Exception;
	
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
	public void deleteByPhone(PageData pd)throws Exception;
	

}
