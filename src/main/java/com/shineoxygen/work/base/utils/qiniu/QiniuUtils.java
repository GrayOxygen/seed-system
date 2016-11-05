package com.shineoxygen.work.base.utils.qiniu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 
 * @author ShineOxygen
 * @description 七牛云服务工具类：文件上传下载
 * @date 2016年11月4日下午8:34:02
 */
public class QiniuUtils {
	private static Logger logger = LogManager.getLogger(QiniuUtils.class);
	// 设置好账号的ACCESS_KEY和SECRET_KEY
	private static String ACCESS_KEY;
	private static String SECRET_KEY;
	// 七牛给的域名
	private static String BUCKET_HOST_NAME;
	// 要上传的空间
	private static String bucketName;
	// 上传文件的路径
	private static String TEMP;// 临时存储地址
	private static String IMG;// 图片存储地址
	private static String AUDIO;// 音频存储地址
	private static String VEDIO;// 视频存储地址

	private String destFilePath;

	// 断点记录的文件保存在recordPath中，可为文件夹或文件
	private static String recordPath;

	// 密钥配置
	private static Auth auth;
	// 创建上传对象
	private static UploadManager uploadManager;
	// 访问token
	private static String token;

	// 回调地址
	private static String callbackUrl;
	private static String callbackBody;

	static {
		Properties properties = new Properties();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("qiniu.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 必须存在空间名和断点暂存路径
		Assert.assertNotNull(properties.getProperty("qiniu.bucketName"));
		Assert.assertNotNull(properties.getProperty("qiniu.recordPath"));
		Assert.assertNotNull(properties.getProperty("qiniu.callbackUrl"));
		Assert.assertNotNull(properties.getProperty("qiniu.callbackBody"));
		Assert.assertNotNull(properties.getProperty("qiniu.ACCESS_KEY"));
		Assert.assertNotNull(properties.getProperty("qiniu.SECRET_KEY"));
		Assert.assertNotNull(properties.getProperty("qiniu.BUCKET_HOST_NAME"));

		BUCKET_HOST_NAME = properties.getProperty("qiniu.BUCKET_HOST_NAME");
		ACCESS_KEY = properties.getProperty("qiniu.ACCESS_KEY");
		SECRET_KEY = properties.getProperty("qiniu.SECRET_KEY");
		bucketName = properties.getProperty("qiniu.bucketName");
		recordPath = properties.getProperty("qiniu.recordPath");
		callbackUrl = properties.getProperty("qiniu.callbackUrl");
		callbackBody = properties.getProperty("qiniu.callbackBody");

		// UploadDir
		TEMP = null != properties.getProperty("TEMP") ? properties.getProperty("TEMP") : UploadDir.TEMP.label;
		IMG = null != properties.getProperty("IMG") ? properties.getProperty("IMG") : UploadDir.IMG.label;
		AUDIO = null != properties.getProperty("AUDIO") ? properties.getProperty("AUDIO") : UploadDir.AUDIO.label;
		VEDIO = null != properties.getProperty("VEDIO") ? properties.getProperty("VEDIO") : UploadDir.VEDIO.label;

		auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		token = auth.uploadToken(bucketName);
		uploadManager = new UploadManager();

	}

	/**
	 * 
	 * @author ShineOxygen
	 * @description 文件夹名称，暂存，图片，音频，视频的存放文件夹
	 * @date 2016年11月4日下午7:50:03
	 */
	public enum UploadDir {
		// label为默认的上传存储文件夹名，位于七牛空间下直接子级
		TEMP("temp"), IMG("img"), AUDIO("audio"), VEDIO("vedio");

		private String label;

		private UploadDir(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

	/**
	 * 上传图片
	 * 
	 * UploadDir.TEMP和hello.png，则会存在temp/hello.png
	 * 
	 * @param fileBytes
	 *            文件二进制数据
	 * @param dir
	 *            存储目录，以UploadDir的label值为名的文件夹，不可为null
	 * @param fileNameWitSuffix
	 *            带有后缀名的文件名
	 * @return 文件地址
	 * @throws FileNotFoundException
	 */
	public static String upload(byte[] fileBytes, UploadDir dir, String fileNameWitSuffix) throws FileNotFoundException {
		if (null == dir) {
			return null;
		}
		try {
			// 调用put方法上传
			Response res = uploadManager.put(fileBytes, dir.label + File.separator + fileNameWitSuffix, token);
			// 打印返回的信息
			logger.debug("upload response from qiniu " + res.bodyString());
			return BUCKET_HOST_NAME + File.separator + dir.label + File.separator + fileNameWitSuffix;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			logger.debug("upload异常，上传失败，七牛返回的错误信息(response.toString())：" + r.toString());
			try {
				// 响应的文本信息
				logger.debug("upload异常，上传失败，七牛返回的错误信息(response.bodyString())" + r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
			return null;
		}
	}

	/**
	 * TODO 前后台测试
	 * 
	 * 断点上传
	 * 
	 * @param fileBytes
	 *            文件二进制数据
	 * @param dir
	 *            存储目录，以UploadDir的label值为名的文件夹，不可为null
	 * @param fileNameWitSuffix
	 *            带有后缀名的文件名
	 * @throws Exception
	 * @return 文件名称
	 */
	public static String uploadAfterBreak(byte[] fileBytes, UploadDir dir, String fileNameWitSuffix) throws Exception {
		if (null == dir) {
			return null;
		}
		// 设置断点记录文件保存在指定文件夹或File对象中
		File f = File.createTempFile("qiniu_xxxx", ".tmp");
		recordPath = f.getParent();

		Recorder recorder = new FileRecorder(recordPath);

		// 实例化上传对象，并且传入一个recorder对象
		UploadManager uploadManager = new UploadManager(recorder);

		try {
			// 调用put方法上传
			Response res = uploadManager.put(fileBytes, dir.label + File.separator + fileNameWitSuffix, token);
			// 打印返回的信息
			System.out.println(res.bodyString());
			logger.debug("upload response from qiniu " + res.bodyString());
			return BUCKET_HOST_NAME + File.separator + dir.label + File.separator + fileNameWitSuffix;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			logger.debug("upload异常，上传失败，七牛返回的错误信息(response.toString())：" + r.toString());
			try {
				// 响应的文本信息
				logger.debug("upload异常，上传失败，七牛返回的错误信息(response.bodyString())" + r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
			return null;
		}
	}

	/**
	 * 该方法上传后，会回调指定callbackUrl，传回文件信息，如文件名，文件的访问url
	 * 
	 * @param fileBytes
	 *            上传的文件
	 * @param destFilePath
	 *            指定上传文件所保存在的文件路径
	 */
	public static void uploadCallback(byte[] fileBytes, UploadDir dir, String fileNameWitSuffix) {
		token = auth.uploadToken(bucketName, null, 3600, new StringMap().put("callbackUrl", callbackUrl).put("callbackBody", callbackBody));
		try {
			// 调用put方法上传
			Response res = uploadManager.put(fileBytes, null, token);
			// 打印返回的信息
			logger.debug("upload response from qiniu " + res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常信息
			logger.debug("upload异常，上传失败，七牛返回的错误信息(response.toString())：" + r.toString());
			try {
				// 响应的文本信息
				logger.debug("upload异常，上传失败，七牛返回的错误信息(response.bodyString())" + r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	/**
	 * TODO 私有空间？
	 */
	public static void download() {
		// 构造私有空间需要生成的下载的链接
		String URL = "http://bucketdomain/key";
		// 调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
		String downloadRUL = auth.privateDownloadUrl(URL, 3600);
		System.out.println(downloadRUL);
	}

}
