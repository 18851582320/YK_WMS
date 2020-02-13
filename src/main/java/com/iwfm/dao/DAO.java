package com.iwfm.dao;

/**
 * ClassName: DAO 
 * @Description: TODO
 * @author yk
 * @date 2017年8月2日
 */
public interface DAO {
	
	/**
	 * @Title: save
	 * @Description: 保存对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object save(String str, Object obj) throws Exception;
	
	/**
	 * @Title: update
	 * @Description: 修改对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object update(String str, Object obj) throws Exception;
	
	/**
	 * @Title: delete
	 * @Description: 删除对象 
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object delete(String str, Object obj) throws Exception;

	/**
	 * @Title: findForObject
	 * @Description: 查找对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object findForObject(String str, Object obj) throws Exception;

	/**
	 * @Title: findForList
	 * @Description: 查找对象List
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object findForList(String str, Object obj) throws Exception;
	
	/**
	 * @Title: findForMap
	 * @Description: 查找对象封装成Map
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param sql
	 * @param @param obj
	 * @param @param key
	 * @param @param value
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object findForMap(String sql, Object obj, String key , String value) throws Exception;
}
