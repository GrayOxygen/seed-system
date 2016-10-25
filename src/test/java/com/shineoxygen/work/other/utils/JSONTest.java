package com.shineoxygen.work.other.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.shineoxygen.work.base.model.bootstraptable.Column;
import com.shineoxygen.work.base.model.bootstraptable.Search;

public class JSONTest {
	@Test
	public void url2POJO() throws IllegalAccessException, InvocationTargetException {
		Column column=new Column();
		column.setSearch(new Search());
		BeanUtils.setProperty(column,  "search.regex","true");
		System.out.println(column.getSearch());
	}
}
