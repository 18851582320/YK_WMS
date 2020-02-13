package com.iwfm.util;

import java.lang.reflect.Field;

/**
 * ClassName: ReflectHelper 
 * @Description: 反射工具
 * @author yk
 * @date 2017年8月2日
 */
public class ReflectHelper {
	/**
	 * @Title: getFieldByFieldName
	 * @Description: 获取obj对象fieldName的Field
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param obj
	 * @param @param fieldName
	 * @param @return
	 * @return Field
	 * @throws
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * @Title: getValueByFieldName
	 * @Description: 获取obj对象fieldName的属性值
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param obj
	 * @param @param fieldName
	 * @param @return
	 * @param @throws SecurityException
	 * @param @throws NoSuchFieldException
	 * @param @throws IllegalArgumentException
	 * @param @throws IllegalAccessException
	 * @return Object
	 * @throws
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if(field!=null){
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * @Title: setValueByFieldName
	 * @Description: 设置obj对象fieldName的属性值
	 * @author yk
	 * @date 2017年8月2日
	 * @param @param obj
	 * @param @param fieldName
	 * @param @param value
	 * @param @throws SecurityException
	 * @param @throws NoSuchFieldException
	 * @param @throws IllegalArgumentException
	 * @param @throws IllegalAccessException
	 * @return void
	 * @throws
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}
}
