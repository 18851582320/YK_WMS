
package com.iwfm.service.system.sysTrackLimit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.staff.StaffManager;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.sysTrackLimit.SysTrackLimitManager;
import com.iwfm.service.system.uploadFile.UploadFileManager;
import com.iwfm.util.PageData;
import com.iwfm.util.UuidUtil;

/**
 * 
 * ClassName: SysDataSetService 
 * @Description: TODO
 * @author yk
 * @date: 2019年1月24日
 */
@Service(value="sysTrackLimitService")
public class SysTrackLimitService implements SysTrackLimitManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="uploadFileService")
	private UploadFileManager uploadFileService;
	
	@Resource(name="staffService")
	private StaffManager staffService;
	
	
	/**
	 * 
	 * @Title: save
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> save(PageData pd,CommonsMultipartFile []files)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		dao.save("SysTrackLimitMapper.save", pd);
		map.put("result", rtnInfo);
		return map;
		
	}
	
	/**
	 * 
	 * @Title: saveByDept
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> saveByDept(PageData pd,CommonsMultipartFile []files)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		
		String DEPARTMENT_IDS=pd.getString("DEPARTMENT_IDS");
		String mainUser=pd.getString("mainUser");
		String trackLimitTypeCode=pd.getString("trackLimitTypeCode");
		
		if(StringUtils.isNotEmpty(DEPARTMENT_IDS)) {
			String deptAarray[]=DEPARTMENT_IDS.split(",");
			
			PageData staffPd=new PageData();
			PageData trackPd=new PageData();
			PageData tagPd=new PageData();
			List<PageData> staffLst=null;
			
			for(int i=0;i<deptAarray.length;i++) {
				
				staffPd=new PageData();
				staffPd.put("DEPARTMENT_ID", deptAarray[i]);
				
				staffLst=staffService.listByDept(staffPd);
				if(staffLst!=null && staffLst.size()>0) {
					
					for(int z=0;z<staffLst.size();z++) {
						trackPd=new PageData();
						trackPd.put("trackLimitId",UuidUtil.get32UUID());
						trackPd.put("mainUser",mainUser);
						trackPd.put("trackLimitTypeCode",trackLimitTypeCode);
						trackPd.put("limitUser",staffLst.get(z).getString("STAFF_ID"));
						tagPd=findByUserAndTrackLimitCode(trackPd);
						if(tagPd!=null) {
							continue;
						}else {
							dao.save("SysTrackLimitMapper.save", trackPd);
						}
					}
					
				}
			}
		}
		map.put("result", rtnInfo);
		return map;
		
	}
	
	
	/**
	 * 
	 * @Title: saveByUser
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> saveByUser(PageData pd,CommonsMultipartFile []files)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		
		String STAFF_IDS=pd.getString("STAFF_IDS");
		String mainUser=pd.getString("mainUser");
		String trackLimitTypeCode=pd.getString("trackLimitTypeCode");
		
		if(StringUtils.isNotEmpty(STAFF_IDS)) {
			String deptAarray[]=STAFF_IDS.split(",");
			
			PageData trackPd=new PageData();
			PageData tagPd=new PageData();
			
			for(int i=0;i<deptAarray.length;i++) {
				
				trackPd=new PageData();
				trackPd.put("trackLimitId",UuidUtil.get32UUID());
				trackPd.put("mainUser",mainUser);
				trackPd.put("trackLimitTypeCode",trackLimitTypeCode);
				trackPd.put("limitUser",deptAarray[i]);
				tagPd=findByUserAndTrackLimitCode(trackPd);
				if(tagPd!=null) {
					continue;
				}else {
					dao.save("SysTrackLimitMapper.save", trackPd);
				}
					
			}
		}
		map.put("result", rtnInfo);
		return map;
		
	}
	
	
	
	/**
	 * 
	 * @Title: edit
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @param files
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public Map<String, String> edit(PageData pd,CommonsMultipartFile []files)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		String rtnInfo="success";
		dao.update("SysTrackLimitMapper.edit", pd);
		map.put("result", rtnInfo);
		return map;
	}
	
	
	/**
	 * 
	 * @Title: delete
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysTrackLimitMapper.delete", pd);
	}
	
	
	/**
	 * 
	 * @Title: findById
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysTrackLimitMapper.findById", pd);
	}
	
	/**
	 * 
	 * @Title: findByUserAndTrackLimitCode
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws:
	 */
	public PageData findByUserAndTrackLimitCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysTrackLimitMapper.findByUserAndTrackLimitCode", pd);
	}
	
	
	/**
	 * 
	 * @Title: querylistPage
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param page
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysTrackLimitMapper.querylistPage", page);
	}
	
	
	/**
	 * 
	 * @Title: listAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysTrackLimitMapper.listAll", pd);
	}
	
	
	/**
	 * 
	 * @Title: listByMainUserAndType
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByMainUserAndType(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysTrackLimitMapper.listByMainUserAndType", pd);
	}
	
	

	/**
	 * 
	 * @Title: deleteAll
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月21日
	 * @param: @param ArrayDATA_IDS
	 * @param: @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysTrackLimitMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
}
