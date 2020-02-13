package com.iwfm.task.entity;

import java.util.Date;

public class ScheduleJob{

	private static final long serialVersionUID = 1L;

	/** 任务id */
	private String  timedTaskId;

	/** 任务名称 */
	private String timedTaskName;

	/** 任务分组 */
	private String timedTaskType;
	
    /** 任务别名 */
    private String  timedTaskAliasName;
	
	/** 指定执行类 */
	private String timedTaskClass;

	/** 任务状态  0停用 1启用 2删除 */
	private Integer timedTaskState;

	/** 任务运行时间表达式 */
	private String cronExpression;

	/** 任务描述 */
	private String timedTaskMemo;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date updateTime;

	private String keyWord;

	public String getTimedTaskId() {
		return timedTaskId;
	}

	public void setTimedTaskId(String timedTaskId) {
		this.timedTaskId = timedTaskId;
	}

	public String getTimedTaskName() {
		return timedTaskName;
	}

	public void setTimedTaskName(String timedTaskName) {
		this.timedTaskName = timedTaskName;
	}

	public String getTimedTaskType() {
		return timedTaskType;
	}

	public void setTimedTaskType(String timedTaskType) {
		this.timedTaskType = timedTaskType;
	}

	public String getTimedTaskAliasName() {
		return timedTaskAliasName;
	}

	public void setTimedTaskAliasName(String timedTaskAliasName) {
		this.timedTaskAliasName = timedTaskAliasName;
	}

	public String getTimedTaskClass() {
		return timedTaskClass;
	}

	public void setTimedTaskClass(String timedTaskClass) {
		this.timedTaskClass = timedTaskClass;
	}

	public Integer getTimedTaskState() {
		return timedTaskState;
	}

	public void setTimedTaskState(Integer timedTaskState) {
		this.timedTaskState = timedTaskState;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getTimedTaskMemo() {
		return timedTaskMemo;
	}

	public void setTimedTaskMemo(String timedTaskMemo) {
		this.timedTaskMemo = timedTaskMemo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
