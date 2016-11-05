package com.shineoxygen.work.other.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.shineoxygen.work.base.utils.qiniu.QiniuUtils;
import com.shineoxygen.work.base.utils.qiniu.QiniuUtils.UploadDir;

/**
 * 七牛测试
 * 
 * @author ShineOxygen
 * @description TODO
 * @date 2016年11月4日下午9:18:23
 */
public class QiniuUtilsTest {
	@Test
	public void upload() throws IllegalAccessException, InvocationTargetException, FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:wallpaper.png");
		System.out.println(QiniuUtils.upload(getBytes(file), UploadDir.TEMP, file.getName()));

	}

	@Test
	public void uploadAfterBreak() throws Exception {
		File file = ResourceUtils.getFile("classpath:test.zip");
		System.out.println(QiniuUtils.uploadAfterBreak(getBytes(file), UploadDir.TEMP, file.getName()));

	}

	public static byte[] getBytes(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
}
