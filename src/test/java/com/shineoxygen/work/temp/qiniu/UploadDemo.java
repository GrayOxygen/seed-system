package com.shineoxygen.work.temp.qiniu;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class UploadDemo {
	// 设置好账号的ACCESS_KEY和SECRET_KEY
	private String ACCESS_KEY = "BtPMWEF6gEAHZCC-YT1lqyfUpmWVgo17ByXftlCh";
	private String SECRET_KEY = "XOt_zr_5Dok8ZXlJZPjDX9AMMDhvw4_XtjfBoS9w";
	// 要上传的空间
	private String bucketname = "test";
	// 上传到七牛后保存的文件名
	private String key = "upload1.png";
	// 上传文件的路径
	private String FilePath = "/.../...";

	private String token;
	// 密钥配置
	private Auth auth;
	// 创建上传对象
	private UploadManager uploadManager;

	@Before
	public void setup() {
		auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		token = auth.uploadToken(bucketname);
		uploadManager = new UploadManager();

	}

	// 普通上传图片
	@Test
	public void uploadPic() throws FileNotFoundException {
		try {
			File file = ResourceUtils.getFile("classpath:wallpaper.png");
			// 调用put方法上传
			Response res = uploadManager.put(file, key, token);
			// 打印返回的信息
			System.out.println(res.bodyString());
			System.out.println(bucketname + "/" + key);
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	@Test
	public void uploadAfterBreak() throws Exception {
		// 设置断点记录文件保存在指定文件夹或的File对象
		String recordPath = "test.zip";
		// 实例化recorder对象
		Recorder recorder = new FileRecorder(recordPath);
		// 实例化上传对象，并且传入一个recorder对象
		UploadManager uploadManager = new UploadManager(recorder);

		try {
			// 调用put方法上传
			Response res = uploadManager.put(ResourceUtils.getFile("classpath:test.zip"), "key", token);
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	@Test
	public void uploadCallback() {
		token = auth.uploadToken(bucketname, null, 3600, new StringMap().put("callbackUrl", "http://your.domain.com/callback").put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
		try {
			// 调用put方法上传
			Response res = uploadManager.put("some file path", null, token);
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	@Test
	public void download() {
		// 构造私有空间需要生成的下载的链接
		String URL = "http://bucketdomain/key";
		// 调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
		String downloadRUL = auth.privateDownloadUrl(URL, 3600);
		System.out.println(downloadRUL);
	}

}
