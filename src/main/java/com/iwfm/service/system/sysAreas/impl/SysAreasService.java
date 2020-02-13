package com.iwfm.service.system.sysAreas.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.service.system.sysAreas.SysAreasManager;
import com.iwfm.util.PageData;

@Service("sysAreasService")
public class SysAreasService implements SysAreasManager {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> initProvince(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SysAreasMapper.initProvince", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getCity(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SysAreasMapper.getCity", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getArea(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SysAreasMapper.getArea", pd);
	}

}
