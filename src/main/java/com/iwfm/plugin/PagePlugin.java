package com.iwfm.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.iwfm.entity.Page;
import com.iwfm.service.system.sysDataDetail.SysDataDetailManager;
import com.iwfm.util.PageData;
import com.iwfm.util.ReflectHelper;
import com.iwfm.util.Tools;

/**
 * ClassName: PagePlugin 
 * @Description: 分页插件
 * @author yk
 * @date 2017年8月2日
 */

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PagePlugin implements Interceptor {

	private static String dialect = "";	//数据库方言
	private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)
	
	public Object intercept(Invocation ivk) throws Throwable {
		// TODO Auto-generated method stub
		if(ivk.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			
			if(mappedStatement.getId().matches(pageSqlId)){ //拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if(parameterObject==null){
					throw new NullPointerException("parameterObject尚未实例化！");
				}else{
					Connection connection = (Connection) ivk.getArgs()[0];
					
					String sql = boundSql.getSql();
					String fhsql = sql;
					
					if("sqlserver".equals(dialect) || "sqlserver2012".equals(dialect)){
						fhsql = sql;
						
					}
					
					Page page = null;
					if(parameterObject instanceof Page){	//参数就是Page实体
						 page = (Page) parameterObject;
						 page.setEntityOrField(true);	 
					}else{	//参数为某个实体，该实体拥有Page属性
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,"page");
						if(pageField!=null){
							page = (Page) ReflectHelper.getValueByFieldName(parameterObject,"page");
							if(page==null)
								page = new Page();
							page.setEntityOrField(false); 
							ReflectHelper.setValueByFieldName(parameterObject,"page", page); //通过反射，对实体对象设置分页对象
						}else{
							throw new NoSuchFieldException(parameterObject.getClass().getName()+"不存在 page 属性！");
						}
					}
					
					//判断是导出还是列表查询
					PageData pd=page.getPd();
					boolean exportToExcelTag=false;
					int count = 0;
					if(pd!=null){
						if(pd.containsKey("exportToExcelTag")) {
							if(StringUtils.isNotEmpty(pd.getString("exportToExcelTag")) && pd.getString("exportToExcelTag").equals("1")) {
								//导出
								exportToExcelTag=true;
							}
						}
					}
					
					
					fhsql=generateQuerySql(fhsql, page, exportToExcelTag);
					
					if(!exportToExcelTag) {
						
						String countSql = "select count(0) from (" + fhsql+ ")  tmp_count"; //记录统计 == oracle 加 as 报错(SQL command not properly ended)
						PreparedStatement countStmt = connection.prepareStatement(countSql);
						BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
						setParameters(countStmt,mappedStatement,countBS,parameterObject);
						ResultSet rs = countStmt.executeQuery();
						if (rs.next()) {
							count = rs.getInt(1);
						}
						rs.close();
						countStmt.close();
						
						page.setTotalResult(count);
					}
					
					
					String pageSql = generatePageSql(fhsql,page,exportToExcelTag);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.
				}
			}
		}
		return ivk.proceed();
	}

	
	/**
	 * @Title: setParameters
	 * @Description: 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param ps
	 * @param @param mappedStatement
	 * @param @param boundSql
	 * @param @param parameterObject
	 * @param @throws SQLException
	 * @return void
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: generatePageSql
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @param sql
	 * @param: @param page
	 * @param: @param exportToExcelTag
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	private String generatePageSql(String sql,Page page,boolean exportToExcelTag){
		if(page!=null && Tools.notEmpty(dialect)){
			
			StringBuffer pageSql = new StringBuffer();
			StringBuffer orderbySql = new StringBuffer(" order by SStbzd ");
			
			
			if("sqlserver2012".equals(dialect)){
				String fhsql = sql;
				String field = ", 'SS' SStbzd";
				
				/*拼写查询条件和排序*/
				PageData pd=page.getPd();
				if(pd!=null){
					String sort=pd.getString("sort");
					String order=pd.getString("order");
					if(StringUtils.isNotEmpty(sort) && StringUtils.isNotEmpty(order)){
						String sortArr[]=sort.split(",");
						String orderArr[]=order.split(",");
						
						orderbySql=new StringBuffer(" order by ");
						
						if(sortArr.length==orderArr.length){
							for(int i=0;i<sortArr.length;i++){
								if(i==0){
									orderbySql.append(" "+sortArr[i]+" "+orderArr[i]);
								}else{
									orderbySql.append(","+sortArr[i]+" "+orderArr[i]);
								}
							}
							
						}
					}else {
						
						if(exportToExcelTag) {
							orderbySql=new StringBuffer(" ");
						}else {
							orderbySql.append("");
						}
						
					}
					
				}
				
				//导出，不需要分页
				if(exportToExcelTag) {
					pageSql.append(fhsql);
					pageSql.append(orderbySql);
				}else {
					pageSql.append("select SS_tab.*"+field+" from (");
					pageSql.append(fhsql);
					pageSql.append(") SS_tab "+orderbySql.toString()+" offset "+page.getCurrentResult()+" row fetch next "+page.getShowCount()+" rows only");
				}
				
				orderbySql=null;
				
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
	
	/**
	 * 
	 * @Title: generateCountSql
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年9月29日
	 * @param: @param sql
	 * @param: @param page
	 * @param: @param exportToExcelTag
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	@SuppressWarnings("unchecked")
	private String generateQuerySql(String sql,Page page,boolean exportToExcelTag){
		if(page!=null && Tools.notEmpty(dialect)){
			
			StringBuffer pageSql = new StringBuffer();
			StringBuffer whereSql = new StringBuffer();
			
			
			if("sqlserver2012".equals(dialect)){
				String fhsql = sql;
				
				/*拼写查询条件和排序*/
				PageData pd=page.getPd();
				if(pd!=null){
					
					//获取bean
					WebApplicationContext webctx=ContextLoader.getCurrentWebApplicationContext();
					SysDataDetailManager sysDataDetailService=(SysDataDetailManager)webctx.getBean("sysDataDetailService");
					PageData dataPd=new PageData();
					dataPd.put("sysDataIndex",pd.get("sysDataIndex"));
					List<PageData> fieldLst=null;
					try {
						fieldLst=sysDataDetailService.listAllByKeyId(dataPd);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(fieldLst!=null && fieldLst.size()>0) {
						
						//去除page的一些条件，剩下的就是查询条件。
						
						/*
						pd.remove("page");
						pd.remove("sort");
						pd.remove("rows");
						pd.remove("order");
						pd.remove("exportToExcelTag");
						pd.remove("sysDataIndex");
						*/
						
						String isAccurateQuery=pd.getString("isAccurateQuery");//是否精确查询
						if(StringUtils.isEmpty(isAccurateQuery))isAccurateQuery="false";
						
						pd.remove("isAccurateQuery");
						
						Set<String> keySet=pd.keySet();
						if(keySet!=null && keySet.size()>0){
							
							boolean existTag=false;
							String sysDataDeFieldType="";//字段类型
							String sysDataDeFieldCode="";//字段编码
							String queryFieldName="";//查询字段
							String numTag="";//字段最后一位
							
							String asQueryCondition="";//是否作为查询条件
							String isJingQueQuery="";//不作为查询条件，nvarchar类型时，是否精确查询
							String keyTag="";
							
							
							
							for (String key : keySet) {
								//判断key是否在配置好的查询条件中
								
								if(pd.get(key)!=null && StringUtils.isNotEmpty(pd.get(key).toString())) {
									
									//排除这些关键字
									if(key.equals("1")  || key.equals("page") || key.equals("sort") || key.equals("rows") || key.equals("order") || key.equals("exportToExcelTag") || key.equals("sysDataIndex")) {
										continue;
									}
									
								}else {
									continue;
								}
								
								
								existTag=false;
								for(PageData fieldPd : fieldLst) {
									
									sysDataDeFieldCode=fieldPd.getString("sysDataDeFieldCode");//字段编码
									sysDataDeFieldType=fieldPd.getString("sysDataDeFieldType");//字段类型
									queryFieldName=fieldPd.getString("queryFieldName");//查询字段
									
									asQueryCondition=fieldPd.getString("asQueryCondition");//是否作为查询条件
									isJingQueQuery=fieldPd.getString("isJingQueQuery");//不作为查询条件，nvarchar类型时，是否精确查询
									
									//numTag=sysDataDeFieldCode.substring(sysDataDeFieldCode.length()-1,sysDataDeFieldCode.length());//字段最后一位
									numTag=key.substring(key.length()-1,key.length());//字段最后一位
									
									
									
									//最后一位为2
									if(numTag.equals("2")) {
										
										//sysDataDeFieldCode=sysDataDeFieldCode.substring(0, sysDataDeFieldCode.length()-1);
										keyTag=key.substring(0, key.length()-1);
										if(sysDataDeFieldCode.equals(keyTag)) {
											
											if( sysDataDeFieldType.equals("decimal") || sysDataDeFieldType.equals("int")) {
												whereSql.append(" and "+queryFieldName+" <="+pd.get(key));
											}
											
											if(sysDataDeFieldType.equals("date") || sysDataDeFieldType.equals("datetime") ) {
													whereSql.append(" and "+queryFieldName+" <='"+pd.get(key)+"'");
											}
											
											existTag=true;
											break;
										}
										
										//date datetime    
										
									}else {//最后一位不为2
										
										if(sysDataDeFieldCode.equals(key)) {
											
											//作为查询
											if(asQueryCondition.equals("是")) {
												
												if( sysDataDeFieldType.equals("decimal") || sysDataDeFieldType.equals("int")) {
													whereSql.append(" and "+queryFieldName+" >="+pd.get(key));
												}
												
												if(sysDataDeFieldType.equals("date") || sysDataDeFieldType.equals("datetime") ) {
													whereSql.append(" and "+queryFieldName+" >='"+pd.get(key)+"'");
												}
												
												if(sysDataDeFieldType.equals("nvarchar")){
													if(isAccurateQuery.equals("false")) {
														whereSql.append(" and "+queryFieldName+" LIKE CONCAT(CONCAT('%','"+pd.get(key)+"'),'%') ");
													}else {
														whereSql.append(" and "+queryFieldName+" ='"+pd.get(key)+"'");
													}
														
												}
												
												if(sysDataDeFieldType.equals("boolean")){
													whereSql.append(" and "+queryFieldName+"="+pd.get(key));
												}
												
												if(sysDataDeFieldType.equals("booleanVarchar")){
													whereSql.append(" and "+queryFieldName+"='"+pd.get(key)+"'");
												}
												
											}else {//不作为查询，一些筛选条件，如下拉等
												
												if( sysDataDeFieldType.equals("decimal") || sysDataDeFieldType.equals("int")) {
													whereSql.append(" and "+queryFieldName+" ="+pd.get(key));
												}
												
												if(sysDataDeFieldType.equals("date") || sysDataDeFieldType.equals("datetime") ) {
													whereSql.append(" and "+queryFieldName+" ='"+pd.get(key)+"'");
												}
												
												if(sysDataDeFieldType.equals("nvarchar")){
													if(isJingQueQuery.equals("否")) {
														whereSql.append(" and "+queryFieldName+" LIKE CONCAT(CONCAT('%','"+pd.get(key)+"'),'%') ");
													}else {
														whereSql.append(" and "+queryFieldName+" ='"+pd.get(key)+"'");
													}
														
												}
												
												if(sysDataDeFieldType.equals("boolean")){
													whereSql.append(" and "+queryFieldName+"="+pd.get(key));
												}
												
												if(sysDataDeFieldType.equals("booleanVarchar")){
													whereSql.append(" and "+queryFieldName+"='"+pd.get(key)+"'");
												}
											}
											
											
											existTag=true;
											break;
										}
									}
									
									
								}
								
								if(!existTag) {
									if(pd.get(key)!=null) {
										//whereSql.append(" and "+key+"='"+pd.get(key)+"'");
									}
								}
							}
						}
						
						
					}else {
						if(pd.get("sysDataIndex")!=null && StringUtils.isNotEmpty(pd.get("sysDataIndex").toString())) {
							pd.remove("isAccurateQuery");
							Set<String> keySet=pd.keySet();
							if(keySet!=null && keySet.size()>0){
								for (String key : keySet) {
									
									if(pd.get(key)!=null && StringUtils.isNotEmpty(pd.get(key).toString())) {
										
										//排除这些关键字
										if(key.equals("1")  || key.equals("page") || key.equals("sort") || key.equals("rows") || key.equals("order") || key.equals("exportToExcelTag") || key.equals("sysDataIndex")) {
											continue;
										}else {
											//whereSql.append(" and "+key+"='"+pd.get(key)+"'");
										}
										
									}else {
										continue;
									}
								}
							}
						}
					}
					
					
				}
				
				//导出，不需要分页
				if(exportToExcelTag) {
					pageSql.append(fhsql);
					pageSql.append(whereSql.toString());
				}else {
					pageSql.append(fhsql);
					pageSql.append(whereSql.toString());
				}
				
				whereSql=null;
				
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
	
	
	public Object plugin(Object arg0) {
		// TODO Auto-generated method stub
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (Tools.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (Tools.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
