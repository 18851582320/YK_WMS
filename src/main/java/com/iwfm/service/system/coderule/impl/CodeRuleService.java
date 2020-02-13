package com.iwfm.service.system.coderule.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.util.DateUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;


/**
 * ClassName: CodeRuleService 
 * @Description: 编码规则设置service
 * @author yk
 * @date 2018年1月19日
 */
@Service("codeRuleService")
public class CodeRuleService implements CodeRuleManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="logService")
	private LogManager logService;
	
	/**
	 * @Title: save
	 * @Description:新增
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CoderuleMapper.save", pd);
		logService.save(Jurisdiction.getUsername(), "新增编码规则："+pd.getString("explain"));
	}
	
	/**
	 * @Title: delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CoderuleMapper.delete", pd);
	}
	
	/**
	 * @Title: edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CoderuleMapper.edit", pd);
		logService.save(Jurisdiction.getUsername(), "修改编码规则："+pd.getString("explain"));
	}
	
	/**
	 * @Title: list
	 * @Description: 列表
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CoderuleMapper.datalistPage", page);
	}
	
	/**
	 * @Title:querylistPage
	 * @Description: 编码规则列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CoderuleMapper.querylistPage", page);
	}
	
	/**
	 * @Title: listAll
	 * @Description: 列表(全部)
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CoderuleMapper.listAll", pd);
	}
	
	/**
	 * @Title: findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CoderuleMapper.findById", pd);
	}
	
	
	
	
	
	/**
	 * @Title:findByTabAndFie
	 * @Description: TODO
	 * @author yk
	 * @date 2018年1月22日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findByTabAndFie(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CoderuleMapper.findByTabAndFie", pd);
	}

	/**
	 * @Title:getFormCodeByRule
	 * @Description: TODO
	 * @author yk
	 * @date 2018年1月22日
	 * @param @param tableName
	 * @param @param fieldName
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public String[] getFormCodeByRule(String tableName,String fieldName)throws Exception{
		String code="";
		String now_Dates="";
		String rtnCode[]=new String[3];
		
		PageData pd=new PageData();
		pd.put("tableName", tableName);
		pd.put("fieldName",fieldName);
		PageData pd2=(PageData)dao.findForObject("CoderuleMapper.findByTabAndFie", pd);
		if(pd2!=null){
			
			now_Dates=DateUtil.getYYMMDD();
			
			String codeRuleType=pd2.getString("codeRuleType");//编码规则类型
			String prefix=pd2.getString("prefix");//前缀
			String serialNumLength=pd2.getString("serialNumLength");//流水号长度
			String nowDate=pd2.getString("nowDate");//记录的日期
			String seq_two=pd2.getString("seq_two");//编码规则为2的流水号
			String seq_three=pd2.getString("seq_three");//编码规则为3的流水号
			
			if(StringUtils.isNotEmpty(codeRuleType)){
				if(StringUtils.isEmpty(prefix))prefix="";
				
				if(codeRuleType.equals("1")){//手动输入
					rtnCode[0]="1";
				}else if(codeRuleType.equals("2")){//前缀+自增
					int num_two=0;
					if(StringUtils.isNotEmpty(seq_two)){
						num_two=Integer.valueOf(seq_two)+1;
					}else{
						num_two=1;
					}
					
					code=prefix+String.valueOf(num_two);
					rtnCode[0]="2";
					rtnCode[1]=code;
					
				}else if(codeRuleType.equals("3")){//前缀+年月日+流水号
					int num_three=0;
					
					if(StringUtils.isEmpty(nowDate)){//为空，第一次
						nowDate=now_Dates;
						pd2.put("nowDate", nowDate);
						pd2.put("seq_three", "0");
						dao.update("CoderuleMapper.editThreeTime", pd2);
						num_three=1;
					}else{
						if(nowDate.equals(now_Dates)){//日期一样（同一天）
							if(StringUtils.isNotEmpty(seq_three)){
								num_three=Integer.valueOf(seq_three)+1;
							}else{
								num_three=1;
							}
						}else{
							nowDate=now_Dates;
							pd2.put("nowDate", nowDate);
							pd2.put("seq_three", "0");
							dao.update("CoderuleMapper.editThreeTime", pd2);
							num_three=1;
						}
					}
					
					code=prefix+now_Dates+"-"+Tools.fillPrefix(String.valueOf(num_three),"0",Integer.valueOf(serialNumLength));
					
					rtnCode[0]="3";
					rtnCode[1]=code;
				}
			}else{//没有设置编码规则
				rtnCode[0]="1";
				rtnCode[1]="";
			}
		}else{
			rtnCode[0]="1";
			rtnCode[1]="";
		}
		
		return rtnCode;
	}
	
	
	/**
	 * @Title:updateFormCodeSerialNum
	 * @Description: 更新表单字段流水号
	 * @author yk
	 * @date 2018年1月24日
	 * @param @param tableName
	 * @param @param fieldName
	 * @param @param codeRuleType
	 * @param @throws Exception   
	 * @throws
	 */
	public void updateFormCodeSerialNum(String tableName,String fieldName,String codeRuleType)throws Exception{
		PageData pd=new PageData();
		pd.put("tableName", tableName);
		pd.put("fieldName",fieldName);
		PageData pd2=(PageData)dao.findForObject("CoderuleMapper.findByTabAndFie", pd);
		if(pd2!=null){
			String seq_two=pd2.getString("seq_two");//编码规则为2的流水号
			String seq_three=pd2.getString("seq_three");//编码规则为3的流水号
		
			int num=0;
			if(codeRuleType.equals("2")){
				num=Integer.valueOf(seq_two)+1;
				pd2.put("seq_two", num);
				dao.update("CoderuleMapper.editTwoType", pd2);
			}
			if(codeRuleType.equals("3")){
				num=Integer.valueOf(seq_three)+1;
				pd2.put("seq_three", num);
				dao.update("CoderuleMapper.editThreeType", pd2);
			}
		}
	}
	
	
	/**
	 * @Title: deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CoderuleMapper.deleteAll", ArrayDATA_IDS);
		logService.save(Jurisdiction.getUsername(), "修改编码规则："+ArrayDATA_IDS.toString());
	}
}
