package com.iwfm.service.system.sysAreas;

import java.util.List;

import com.iwfm.util.PageData;

public interface SysAreasManager {
	
	public List<PageData> initProvince(PageData pd) throws Exception;
	
	public List<PageData> getCity(PageData pd) throws Exception;
	
	public List<PageData> getArea(PageData pd) throws Exception;

}
