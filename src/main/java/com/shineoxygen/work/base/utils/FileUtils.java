package com.shineoxygen.work.base.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtils extends org.apache.commons.io.FileUtils {
	private static final Logger log = LogManager.getLogger(FileUtils.class);

	protected static byte[] buf = new byte[1024];

	public static final boolean isValid(String filename) {
		String regex = "(.*[\\\\|/]{1})*(.+\\.+.+)$";
		Pattern pattern = Pattern.compile(regex, 2);
		Matcher match = pattern.matcher(filename);
		if ((match.find()) && (match.groupCount() == 2)) {
			String name = match.group(2);
			String illegalRegex = "\\\\+|/+|:+|\\*+|\\?+|\"+|<+|>+";
			pattern = Pattern.compile(illegalRegex, 2);
			match = pattern.matcher(name);

			return (!(match.find()));
		}

		return false;
	}

	public static final String replaceInvalidFileChars(String fileName) {
		StringBuffer out = new StringBuffer();

		for (int i = 0; i < fileName.length(); ++i) {
			char ch = fileName.charAt(i);

			switch (ch) {
			case '"':
			case '*':
			case '/':
			case ':':
			case '<':
			case '>':
			case '?':
			case '\\':
			case '|':
				out.append('_');
				break;
			default:
				out.append(ch);
			}
		}

		return out.toString();
	}

	public static final String filePathToURL(String fileName) {
		String fileUrl = new File(fileName).toURI().toString();
		return fileUrl;
	}

	public static final void writeToFile(String filename, InputStream in) throws IOException {
		writeToFile(new File(filename), in);
	}

	public static final void writeToFile(File file, InputStream in) throws IOException {
		OutputStream out = null;
		try {
			out = org.apache.commons.io.FileUtils.openOutputStream(file);
			IOUtils.copy(in, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	public static final String getFilenameExtension(String filename) {
		if (filename.indexOf(".") < 0) {
			return null;
		}
		return filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
	}

	private static final void deleteFolder(File oPath) {
		File[] dirs = oPath.listFiles();
		if (dirs != null) {
			for (File oSubPath : dirs) {
				if (oSubPath.isDirectory()) {
					deleteFolder(oSubPath);
				}
			}
		}
		oPath.delete();
	}

	public static final void deleteFolder(String sPath) {
		File oPath = new File(sPath);
		if ((!(oPath.exists())) || (!(oPath.isDirectory()))) {
			return;
		}
		deleteFolder(oPath);
	}

	public static final void deleteFile(String filename) {
		File file = new File(filename);
		if ((file.exists()) && (file.isFile()))
			file.delete();
	}

	public static final boolean createFolder(String sPath) {
		try {
			File oPath = new File(sPath);
			if (!(oPath.exists())) {
				oPath.mkdirs();
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static final void copyFile(String sourceFile, String targetFile) throws IOException {
		File oFile1 = new File(sourceFile);
		if (oFile1.exists()) {
			String sPath = targetFile.substring(0, targetFile.lastIndexOf(92));
			createFolder(sPath);

			File oFile2 = new File(targetFile);
			RandomAccessFile inData = new RandomAccessFile(oFile1, "r");
			RandomAccessFile opData = new RandomAccessFile(oFile2, "rw");
			FileChannel inChannel = inData.getChannel();
			FileChannel opChannel = opData.getChannel();
			inChannel.transferTo(0L, inChannel.size(), opChannel);

			inChannel.close();
			inData.close();
			opChannel.close();
			opData.close();
		}
	}

	public static final String getRealPath(ServletContext servletContext, String path) {
		File file = new File(path);
		return ((file.isAbsolute()) ? path : servletContext.getRealPath(path));
	}

	public static final String getRelationPath(String basePath, String filename) {
		int beginIndex = filename.indexOf(basePath);
		int endIndex = filename.lastIndexOf(File.separator);
		return filename.substring(beginIndex, endIndex + 1);
	}

	public static final String getClassRootPath(Class<?> clazz) {
		String path = getDirFromClassLoader(clazz);
		if (path == null) {
			path = System.getProperty("user.dir");
		}
		return path;
	}

	private static final String getDirFromClassLoader(Class<?> clazz) {
		try {
			String path = clazz.getName().replace(".", "/");
			path = "/" + path + ".class";
			URL url = clazz.getResource(path);
			String classPath = url.getPath();
			if (classPath.startsWith("file:")) {
				if (classPath.length() > 5) {
					classPath = classPath.substring(5);
				}
				classPath = classPath.split("!")[0];
			} else {
				classPath = clazz.getResource("/").toString().substring(5);
			}
			return classPath;
		} catch (Exception localException) {
		}
		return null;
	}

	public static final InputStream getInputStreamFromJar(String jarPath, String filePath) throws IOException {
		JarFile jarFile = new JarFile(jarPath);
		JarEntry jarEntry = jarFile.getJarEntry(filePath);
		InputStream in = jarFile.getInputStream(jarEntry);
		return in;
	}

	public static final void closeIO(Closeable[] io) {
		for (Closeable aIO : io) {
			if (aIO == null)
				continue;
			try {
				aIO.close();
			} catch (Exception ex) {
				log.error("Close io failed!", ex);
			}
		}
	}
}