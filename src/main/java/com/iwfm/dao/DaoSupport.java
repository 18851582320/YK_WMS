package com.iwfm.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/**
 * ClassName: DaoSupport 
 * @Description: TODO
 * @author yk
 * @date 2017年8月2日
 */
@Repository("daoSupport")
public class DaoSupport implements DAO{
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
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
	public Object save(String str, Object obj) throws Exception {
		return sqlSessionTemplate.insert(str, obj);
	}
	
	/**
	 * @Title: batchSave
	 * @Description: 批量更新
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param objs
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public Object saveBatch(String str, List objs )throws Exception{
		return sqlSessionTemplate.insert(str, objs);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object update(String str, Object obj) throws Exception {
		return sqlSessionTemplate.update(str, obj);
	}

	/**
	 * @Title: batchUpdate
	 * @Description: 批量
	 * @author yk
	 * @date 2017年8月2日
	 * @param @return
	 * @param @throws Exception
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public void updateBatch(String str, List objs )throws Exception{
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.update(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}
	
	/**
	 * @Title: batchDelete
	 * @Description: 批量删除
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param objs
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public Object batchDelete(String str, List objs )throws Exception{
		return sqlSessionTemplate.delete(str, objs);
	}
	
	/**
	 * @Title: delete
	 * @Description: 删除
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object delete(String str, Object obj) throws Exception {
		return sqlSessionTemplate.delete(str, obj);
	}
	 
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
	public Object findForObject(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * @Title: findForList
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
	public Object findForList(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(str, obj);
	}
	
	
	/**
	 * @Title: findForMap
	 * @Description: 查找对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param str
	 * @param @param obj
	 * @param @param key
	 * @param @param value
	 * @param @return
	 * @param @throws Exception
	 * @return Object
	 * @throws
	 */
	public Object findForMap(String str, Object obj, String key, String value) throws Exception {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}
}
