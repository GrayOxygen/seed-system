package com.shineoxygen.work.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import com.shineoxygen.work.base.utils.annotation.ExtendParent;

@SuppressWarnings("all")
public class ReflectionUtils {

	private static Logger logger = LogManager.getLogger(ReflectionUtils.class);

	public static Object invokeGetterMethod(Object object, Method method) {
		try {
			return method.invoke(object, new Object[0]);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[0], new Object[0]);
	}

	public static void invokeSetterMethod(Object object, Method method, Object value) {
		try {
			method.invoke(object, new Object[] { value });
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class type = (propertyType != null) ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	public static Object getFieldValue(Object obj, Field field) {
		Assert.notNull(obj);
		Assert.notNull(field);
		try {
			return field.get(obj);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	public static Object getFieldValue(Object obj, String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	public static void setFieldValue(Object obj, String fieldName, Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}
		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	public static Field getAccessibleField(Object obj, String fieldName) {
		Assert.notNull(obj, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException localNoSuchFieldException) {
			}
		return null;
	}

	public static Class<?> getUserClass(Class<?> clazz) {
		if ((clazz != null) && (clazz.getName().contains("$$"))) {
			Class superClass = clazz.getSuperclass();
			if ((superClass != null) && (!(Object.class.equals(superClass)))) {
				return superClass;
			}
		}
		return clazz;
	}

	public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	public static Method getAccessibleMethod(Object obj, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(obj, "object不能为空");

		for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;
			} catch (NoSuchMethodException localNoSuchMethodException) {
			}
		}
		return null;
	}

	public static <T> Class<T> getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if ((index >= params.length) || (index < 0)) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return ((Class) params[index]);
	}

	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException) || (e instanceof NoSuchMethodException))
			return new IllegalArgumentException("Reflection Exception.", e);
		if (e instanceof InvocationTargetException)
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		if (e instanceof RuntimeException) {
			return ((RuntimeException) e);
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	public static List<Field> findAllField(Class c) {
		return findAllField(c, null);
	}

	public static Field[] findAllFieldArray(Class c, String... filterFields) {
		List<Field> list = findAllField(c, Arrays.asList(filterFields));
		return list.toArray(new Field[0]);
	}

	public static List<Field> findAllField(Class c, List<String> filterFields) {
		List list = new ArrayList(12);
		try {
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields) {
				if ((filterFields == null) || (!(filterFields.contains(f.getName())))) {
					list.add(f);
				}
			}

			Class parent = c.getSuperclass();
			boolean hasAnnotation = null == parent ? false : parent.isAnnotationPresent(ExtendParent.class);
			if (hasAnnotation) {
				list.addAll(findAllField(parent, filterFields));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public static void setFieldVlaue(Object obj, Field field, String value) throws Exception {
		field.setAccessible(true);
		if ((field.getType() == Boolean.TYPE) || (field.getType() == Boolean.class))
			if (value.getClass() == String.class) {
				String str = value;
				if (str.equalsIgnoreCase("true"))
					field.set(obj, Boolean.valueOf(true));
				else if (str.equalsIgnoreCase("false"))
					field.set(obj, Boolean.valueOf(false));
			} else {
				field.set(obj, Boolean.valueOf(Integer.parseInt(value) == 1));
			}
		else if ((field.getType() == Integer.TYPE) || (field.getType() == Integer.class)) {
			if ((field.getType() == Integer.TYPE) && (value == null))
				field.set(obj, Integer.valueOf(0));
			else
				field.set(obj, Integer.valueOf(Integer.parseInt(value)));
		} else if ((field.getType() == Long.TYPE) || (field.getType() == Long.class)) {
			if ((field.getType() == Long.TYPE) && (value == null))
				field.set(obj, Integer.valueOf(0));
			else {
				field.set(obj, Long.valueOf(Long.parseLong(value)));
			}
		} else if ((field.getType() == Double.TYPE) || (field.getType() == Double.class)) {
			if ((field.getType() == Double.TYPE) && (value == null))
				field.set(obj, Double.valueOf(0.0D));
			else
				field.set(obj, Double.valueOf(Double.parseDouble(value)));
		} else if ((field.getType() == Float.TYPE) || (field.getType() == Float.class)) {
			if ((field.getType() == Float.TYPE) && (value == null))
				field.set(obj, Float.valueOf(0.0F));
			else
				field.set(obj, Float.valueOf(Float.parseFloat(value)));
		} else if ((field.getType() == Short.TYPE) || (field.getType() == Short.class)) {
			if ((field.getType() == Short.TYPE) && (value == null))
				field.set(obj, Integer.valueOf(0));
			else
				field.set(obj, Short.valueOf(Short.parseShort(value)));
		} else if ((field.getType() == Byte.TYPE) || (field.getType() == Byte.class)) {
			if ((field.getType() == Byte.TYPE) && (value == null))
				field.set(obj, Integer.valueOf(0));
			else
				field.set(obj, Byte.valueOf(Byte.parseByte(value)));
		} else if (field.getType() == String.class)
			field.set(obj, value);
		else
			throw new Exception(field.getType().getName() + ":type not support for:" + value);
	}

	private static String getFieldCacheKey(Object obj, String fieldName) {
		return obj.getClass().getName() + "_" + fieldName;
	}

	// public static void setFieldVlaue(Object obj, String fieldName, Object
	// value) {
	// Field field = null;
	// try {
	// String key = getFieldCacheKey(obj, fieldName);
	// field = (Field) FieldCache.get(key);
	// if (field == null) {
	// Class clazz = obj.getClass();
	// for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
	// try {
	// field = clazz.getDeclaredField(fieldName);
	// field.setAccessible(true);
	// FieldCache.put(key, field);
	// } catch (Exception localException1) {
	// }
	// }
	//
	// }
	//
	// if (field == null)
	// return;
	// if (value == null) {
	// field.set(obj, null);
	// return;
	// }
	// if ((((field.getType() == Boolean.TYPE) || (field.getType() ==
	// Boolean.class))) && (value.getClass() != Boolean.TYPE) &&
	// (value.getClass() != Boolean.class)) {
	// if (value.getClass() == String.class) {
	// String str = (String) value;
	// if (str.equalsIgnoreCase("true")) {
	// field.set(obj, Boolean.valueOf(true));
	// return;
	// }
	// if (str.equalsIgnoreCase("false")) {
	// field.set(obj, Boolean.valueOf(false));
	//
	// return;
	// }
	// }
	// Number n = (Number) value;
	// field.set(obj, Boolean.valueOf(n.intValue() == 1));
	//
	// return;
	// }
	// field.set(obj, value);
	// } catch (Exception e) {
	// if (field != null) {
	// System.out.println(field.getName() + ":" + value);
	// }
	// throw new RuntimeException(e);
	// }
	// }

	// public static Field getField(Object obj, String fieldName) throws
	// SecurityException, NoSuchFieldException {
	// String key = getFieldCacheKey(obj, fieldName);
	// Field field = (Field) FieldCache.get(key);
	// if (field != null)
	// return field;
	// Class c = obj.getClass();
	// field = c.getDeclaredField(fieldName);
	// field.setAccessible(true);
	// FieldCache.put(key, field);
	// return field;
	// }

	// public static Object getFieldVlaue(Object obj, String fieldName) throws
	// SecurityException, NoSuchFieldException, IllegalArgumentException,
	// IllegalAccessException {
	// Field field = getField(obj, fieldName);
	// if (field != null) {
	// return field.get(obj);
	// }
	// return null;
	// }

	public static List<Field> findNotNullField(Object obj) {
		Class c = obj.getClass();
		List list = new ArrayList(10);
		try {
			List<Field> fields = findAllField(c);
			for (Field f : fields) {
				f.setAccessible(true);
				if (f.get(obj) != null)
					list.add(f);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public static boolean isBaseType(Field f) {
		if (f.getType().isPrimitive()) {
			return true;
		}

		return ((f.getType() == Integer.class) || (f.getType() == Long.class) || (f.getType() == Short.class) || (f.getType() == Boolean.class) || (f.getType() == Character.class)
				|| (f.getType() == Byte.class) || (f.getType() == Double.class) || (f.getType() == Float.class));
	}
}