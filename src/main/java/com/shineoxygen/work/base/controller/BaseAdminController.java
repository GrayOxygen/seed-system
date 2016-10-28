package com.shineoxygen.work.base.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserService;
import com.shineoxygen.work.base.model.bootstraptable.SentParameters;
import com.shineoxygen.work.base.utils.RegexUtil;

public class BaseAdminController extends BaseController {

	public final static String ADMINUSER_NAME = "ADMINUSER_NAME";
	public final static String ADMINUSER_PHONE = "ADMINUSER_PHONE";
	public final static String ADMINUSER = "ADMINUSER";
	public final static String ADMINUSER_ROLE_TYPE = "ADMINUSER_ROLE_TYPE";
	public final static String ADMINUSER_ROLE_NAME = "ADMINUSER_ROLE_NAME";
	public final static String STRUCTURE_MANAGE_NAME = "STRUCTURE_MANAGE_NAME";
	public final static String ADMINUSER_NODE = "ADMINUSER_NODE";
	public final static String ADMINUSER_NODE_LEVEL = "ADMINUSER_NODE_LEVEL";

	@Autowired
	private AdminUserService adminMgr;

	/**
	 * 准备分页数据以及分页页码js
	 * 
	 * @author 王辉阳
	 * @date 2015年9月1日 上午11:06:05
	 * @param page
	 * @param model
	 */
	protected <T> void pageModel(Page<T> page, Model model) {
		// 准备参数
		model.addAttribute("page", page);
		// model.addAttribute("pageInfo",
		// PageInfo.getAdminPagingNavigation(page));
	}

	/**
	 * 返回当前登录的管理员
	 *
	 * @param req
	 * @return
	 */
	public static String getCurrentAdminUserName(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(BaseAdminController.ADMINUSER_NAME);
		return (obj != null) ? (String) obj : null;
	}

	/**
	 * 获取当前登录的管理员用户
	 *
	 * @return
	 */
	public static AdminUser getCurrentAdminUser(HttpServletRequest req) {
		AdminUser obj = (AdminUser) req.getSession().getAttribute(BaseAdminController.ADMINUSER);
		return (obj != null) ? obj : null;
	}

	/**
	 * 设置管理员登录后的session值
	 *
	 * @param req
	 * @param adminUser
	 */
	public static void setAdminUserSession(HttpServletRequest req, AdminUser adminUser) {
		req.getSession().setAttribute(BaseAdminController.ADMINUSER_NAME, adminUser.getUserName());
		req.getSession().setAttribute(BaseAdminController.ADMINUSER, adminUser);
	}

	/**
	 * 
	 * @param sourceStr
	 *            如columns[0][searchable]=true columns[3][search][value]
	 * @param regexStr
	 * @param map
	 * @return
	 */
	protected static void wrapToMap(String sourceStr, Map<String, List<String>> map) {
		List<String> indexAndPropName = RegexUtil.getStrings(sourceStr.split("=")[0], "\\[(.*?)\\]");
		String[] sourceArray = sourceStr.split("=");
		// 属性值
		String value = sourceArray.length > 1 ? sourceArray[1] : "";
		if (map.containsKey(indexAndPropName.get(0))) {
			List<String> tempList = new ArrayList<>();
			tempList.addAll(map.get(indexAndPropName.get(0)));

			if (indexAndPropName.size() == 2) {
				tempList.add(indexAndPropName.get(1) + "=" + value);
			}
			if (indexAndPropName.size() == 3) {
				tempList.add(indexAndPropName.get(1) + "." + indexAndPropName.get(2) + "=" + value);
			}
			map.put(indexAndPropName.get(0), tempList);
		} else {
			map.put(indexAndPropName.get(0), Arrays.asList(indexAndPropName.get(1) + "=" + value));
		}
	}

	/**
	 * 解析bootstrap datatable传到后台的参数
	 * （datatable默认用ajax:{}传来的参数进行解析，因为存在二维数组的参数不能自动绑定）
	 * 
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static SentParameters wrapSentParameters(byte[] bytes) throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException {
		String url = URLDecoder.decode(new String(bytes, "UTF-8"));
		List<String> properties = new ArrayList<>();
		SentParameters sentParameters = new SentParameters();
		CollectionUtils.addAll(properties, StringUtils.split(url, "&"));
		// columns[0][searchable]=true变成key:0 value:searchable=true...
		Map<String, List<String>> columnMap = new HashMap<>();
		Map<String, List<String>> orderMap = new HashMap<>();
		Map<String, List<String>> searchMap = new HashMap<>();
		// search[value]:search[regex]:false
		for (String str : properties) {
			String[] array = str.split("=");
			// 找到columns
			if (RegexUtil.isMatch(str, "columns\\[\\d+\\]\\[\\w+\\]")) {
				wrapToMap(str, columnMap);
			}
			// 找到order
			if (RegexUtil.isMatch(str, "order\\[\\d+\\]\\[\\w+\\]")) {
				wrapToMap(str, orderMap);
			}
			// 找到search
			if (RegexUtil.isMatch(str, "search\\[\\w+\\]")) {
				String temp = StringUtils.replace(str, "[", ".");
				if (array.length > 1) {// 确保有值
					BeanUtils.setProperty(sentParameters, temp.substring(0, temp.indexOf("]")), array[1]);
				}
			}
			// 设置datatable列与POJO属性名称的关系，出来的参数为"0":"userName","1":"age"这种
			if (RegexUtil.isMatch(str, "\\d+=\\w+")) {
				if (array.length > 1) {
					sentParameters.getColumnProperty().addRelation(Integer.parseInt(array[0]), array[1]);
				}
			}
			// 自定义的查询条件，如filters[userName]=wang
			if (StringUtils.isNoneBlank(array[0]) && RegexUtil.isMatch(str, "filters\\[.+\\]")) {
				String temp = StringUtils.replace(str, "[", ".");
				if (array.length > 1) {// 确保有值
					BeanUtils.setProperty(sentParameters, temp.substring(0, temp.indexOf("]")), array[1]);
				}
			}

			// 普通属性，直接加入:draw start length
			if (StringUtils.containsAny(array[0], "draw", "start", "length")) {
				BeanUtils.setProperty(sentParameters, RegexUtil.getStrings(str, "(.*?)=").get(0), (array.length > 1 ? array[1] : ""));
			}
		}
		// 列信息
		sentParameters.wrapColumns(columnMap);
		// 排序信息
		sentParameters.wrapOrder(orderMap);
		// 列下标与POJO的属性名称关系
		sentParameters.getColumnProperty().wireRelation();

		return sentParameters;
	}

	/**
	 * 获取bootstrap datable前台参数，转为spring data的分页对象
	 * 
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Pageable toPageable(byte[] bytes) throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException {
		SentParameters sentParameters = wrapSentParameters(bytes);
		Sort sort = new Sort(sentParameters.toSpringOrder());
		return new PageRequest(sentParameters.getStart() / sentParameters.getLength(), sentParameters.getLength(), sort);
	}

}
