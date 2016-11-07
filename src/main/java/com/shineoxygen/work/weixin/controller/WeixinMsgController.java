package com.shineoxygen.work.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saysth.weixin.sdk.msg.TextMsg;
import com.saysth.weixin.sdk.msg.WeixinMsg;
import com.saysth.weixin.sdk.web.WeixinControllerSupport;

/**
 * 
 * @author 王辉阳
 * @date 2016年11月6日 下午2:38:04
 * @Description 微信消息处理
 */
@Controller
@RequestMapping("/wx")
public class WeixinMsgController extends WeixinControllerSupport {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 提供token，appid，EncodingAESKey用于开发者签名验证
	 * 
	 * 提供了EncodingAESKey则表示默认用的加密消息，接受时解密，回复消息加密
	 */
	@Override
	protected String getTokenAppidAESKey(String paramString) {
		// redis缓存中取，没有存入缓存 "token,appid,key"
		return "token,appid";
	}

	/**
	 * 被动回复消息
	 */
	@Override
	protected WeixinMsg handleTextMsg(TextMsg msg) {
		return new TextMsg("我是公众号！");
	}
}
