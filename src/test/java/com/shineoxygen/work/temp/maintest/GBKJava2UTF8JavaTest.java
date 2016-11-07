package com.shineoxygen.work.temp.maintest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 王辉阳
 * @date 2016年11月6日 上午10:09:59
 * @Description 反编译了一个jar是GBK编码的，而我要的是UTF-8的，该测试将源码进行转码
 */
public class GBKJava2UTF8JavaTest {
	public static List<File> getAllFiles(File dir) {
		List<File> list = new ArrayList<>();
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// 这里面用了递归的算法
				list.addAll(getAllFiles(files[i]));
			}
			list.add(files[i]);
		}
		return list;
	}

	public static void main(String[] args) throws Exception {

		File project = new File("C:\\Users\\Administrator.PC-201607290938\\Desktop\\commons");
		if (project.isDirectory()) {
			List<File> files = getAllFiles(project);
			for (File file : files) {
				if (file.isFile()) {// utf-8 反编译时用了gbk生成的，然后打开乱码
					System.out.println(file.getName() + "==" + file.getAbsolutePath());
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
					List<String> lines = new ArrayList<>();
					String temp = null;
					while (null != (temp = reader.readLine())) {
						lines.add(temp);
					}
					reader.close();
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
					for (String line : lines) {
						writer.write(line);
						writer.write("\n");
					}
					writer.close();// gbk----gbk--unicode--utf8
				}
			}

		}
	}
}
