package com.shineoxygen.work.base.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @author 王辉阳
 * 
 * @date 2015年9月1日 下午2:33:09
 * 
 * @Description 排序工具类
 */
public class SortUtil {
	/**
	 * 根据指定属性升序排序(自然顺序)
	 * 
	 * @author 王辉阳
	 * @date 2015年9月1日 下午2:35:24
	 * @param list
	 *            需要排序的list
	 * @param propertyName
	 *            属性名称，根据该属性值进行升序排序
	 */
	public static <T> void listAscend(List<T> list, final String propertyName) {
		if (list == null || list.size() <= 1) {
			return;
		}
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				try {
					return (int) (BeanUtils.getProperty(o1, propertyName).compareTo(BeanUtils.getProperty(o2, propertyName)));
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}

	/**
	 * 根据指定属性升序排序(自然顺序)
	 * 
	 * @author 王辉阳
	 * @date 2015年9月1日 下午2:35:24f
	 * @param list
	 *            需要排序的list
	 * @param propertyName
	 *            属性名称，根据该属性值进行倒序排序
	 */
	public static <T> void listDescend(List<T> list, final String propertyName) {
		if (list == null || list.size() <= 1) {
			return;
		}
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				try {
					return (int) (BeanUtils.getProperty(o2, propertyName).compareTo(BeanUtils.getProperty(o1, propertyName)));
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}

	/**
	 * 根据指定属性的和进行升序排序(自然顺序)
	 * 
	 * @author 王辉阳
	 * @date 2015年9月1日 下午2:35:24
	 * @param list
	 *            需要排序的list
	 * @param propertyName
	 *            属性名称，根据该属性值进行升序排序
	 */
	public static <T> void listAscendBySumDouble(List<T> list, final List<String> proList) {
		if (list == null || list.size() <= 1) {
			return;
		}
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				try {
					Double preValue = 0D;
					Double postValue = 0D;
					for (String property : proList) {
						preValue += Double.parseDouble(BeanUtils.getProperty(o1, property));
						postValue += Double.parseDouble(BeanUtils.getProperty(o2, property));
					}

					return (int) preValue.compareTo(postValue);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
	public static <T> void listAscendBySumDouble(List<T> list, final String[] proList) {
		if (list == null || list.size() <= 1) {
			return;
		}
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				try {
					Double preValue = 0D;
					Double postValue = 0D;
					for (String property : proList) {
						preValue += Double.parseDouble(BeanUtils.getProperty(o1, property));
						postValue += Double.parseDouble(BeanUtils.getProperty(o2, property));
					}

					return (int) preValue.compareTo(postValue);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
	
	/**
	 * 根据指定属性的和进行升序排序(自然顺序)
	 * 
	 * @author 王辉阳
	 * @date 2015年9月1日 下午2:35:24
	 * @param list
	 *            需要排序的list
	 * @param propertyName
	 *            属性名称，根据该属性值进行升序排序
	 */
	public static <T> void listDescendBySumDouble(List<T> list, final List<String> proList) {
		if (list == null || list.size() <= 1) {
			return;
		}
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				try {
					Double preValue = 0D;
					Double postValue = 0D;
					for (String property : proList) {
						preValue += Double.parseDouble(BeanUtils.getProperty(o1, property));
						postValue += Double.parseDouble(BeanUtils.getProperty(o2, property));
					}

					return (int) postValue.compareTo(preValue);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
	
	/**
	 * 根据指定属性的和进行升序排序(自然顺序)
	 * 
	 * @author 王辉阳
	 * @date 2015年9月1日 下午2:35:24
	 * @param list
	 *            需要排序的list
	 * @param propertyName
	 *            属性名称，根据该属性值进行升序排序
	 */
	public static <T> void listDescendBySumDouble(List<T> list, final String[] proList) {
		if (list == null || list.size() <= 1) {
			return;
		}
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				try {
					Double preValue = 0D;
					Double postValue = 0D;
					for (String property : proList) {
						preValue += Double.parseDouble(BeanUtils.getProperty(o1, property));
						postValue += Double.parseDouble(BeanUtils.getProperty(o2, property));
					}

					return (int) postValue.compareTo(preValue);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
}
