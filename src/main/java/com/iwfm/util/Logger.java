package com.iwfm.util;


/**
 * ClassName: Logger 
 * @Description: 日志处理类
 * @author yk
 * @date 2017年8月2日
 */
public class Logger {
	private org.apache.log4j.Logger logger;
	
	private Logger(org.apache.log4j.Logger log4jLogger) {
		logger = log4jLogger;
	}

	/**
	 * @Title: getLogger
	 * @Description: 获取构造器，根据类初始化Logger对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param classObject
	 * @param @return
	 * @return Logger
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class classObject) {
		return new Logger(org.apache.log4j.Logger.getLogger(classObject));
	}

	/**
	 * @Title: getLogger
	 * @Description: 获取构造器，根据类名初始化Logger对象
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param loggerName:类名字符串
	 * @param @return
	 * @return Logger
	 * @throws
	 */
	public static Logger getLogger(String loggerName) {
		return new Logger(org.apache.log4j.Logger.getLogger(loggerName));
	}

	public void debug(Object object) {
		logger.debug(object);
	}

	public void debug(Object object, Throwable e) {
		logger.debug(object, e);
	}

	public void info(Object object) {
		logger.info(object);
	}

	public void info(Object object, Throwable e) {
		logger.info(object, e);
	}

	public void warn(Object object) {
		logger.warn(object);
	}

	public void warn(Object object, Throwable e) {
		logger.warn(object, e);
	}

	public void error(Object object) {
		logger.error(object);
	}

	public void error(Object object, Throwable e) {
		logger.error(object, e);
	}

	public void fatal(Object object) {
		logger.fatal(object);
	}

	public String getName() {
		return logger.getName();
	}

	public org.apache.log4j.Logger getLog4jLogger() {
		return logger;
	}

	public boolean equals(Logger newLogger) {
		return logger.equals(newLogger.getLog4jLogger());
	}
}
