package com.iwfm.entity.system;


/**
 * ClassName: Role 
 * @Description: 角色
 * @author yk
 * @date 2017年8月1日
 */
public class Role {
	private String ROLE_ID;    //角色id
	private String ROLE_NAME;  //角色名称
	private String RIGHTS;     //权限
	private String PARENT_ID;  //上级ID
	private String ADD_QX;     //新增权限
	private String DEL_QX;     //删除权限
	private String EDIT_QX;    //修改权限
	private String CHA_QX;     //查看权限
	private String IMPORT_QX;  //导入权限
	private String EXPORT_QX;  //导出权限
	private String RNUMBER;  //角色编码
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_NAME() {
		return ROLE_NAME;
	}
	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getADD_QX() {
		return ADD_QX;
	}
	public void setADD_QX(String aDD_QX) {
		ADD_QX = aDD_QX;
	}
	public String getDEL_QX() {
		return DEL_QX;
	}
	public void setDEL_QX(String dEL_QX) {
		DEL_QX = dEL_QX;
	}
	public String getEDIT_QX() {
		return EDIT_QX;
	}
	public void setEDIT_QX(String eDIT_QX) {
		EDIT_QX = eDIT_QX;
	}
	public String getCHA_QX() {
		return CHA_QX;
	}
	public String getRNUMBER() {
		return RNUMBER;
	}
	public void setRNUMBER(String rNUMBER) {
		RNUMBER = rNUMBER;
	}
	public void setCHA_QX(String cHA_QX) {
		CHA_QX = cHA_QX;
	}
	public String getIMPORT_QX() {
		return IMPORT_QX;
	}
	public void setIMPORT_QX(String iMPORT_QX) {
		IMPORT_QX = iMPORT_QX;
	}
	public String getEXPORT_QX() {
		return EXPORT_QX;
	}
	public void setEXPORT_QX(String eXPORT_QX) {
		EXPORT_QX = eXPORT_QX;
	}
	
	
	
}
