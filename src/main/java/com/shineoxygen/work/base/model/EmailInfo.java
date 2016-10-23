package com.shineoxygen.work.base.model;

import java.util.Properties;

/**
 * Email Informations
 *
 * @author
 */
public class EmailInfo {
	private String mailServerHost;
	private String mailServerPort;
	private String fromAddress;
	private String[] toAddress;
	private String username;
	private String password;
	private boolean validate;
	private String subject;
	private String content;
	private String[] attachFileNames;

	public EmailInfo() {
		// mailServerHost = ResourceUtil.getProperty("mail.smtp.host");
		// mailServerPort = ResourceUtil.getProperty("mail.smtp.port");
		// fromAddress = ResourceUtil.getProperty("mail.user.account");
		// username = ResourceUtil.getProperty("mail.user.userName");
		// password = ResourceUtil.getProperty("mail.user.password");
		// validate = ResourceUtil.getProperty("mail.smtp.auth").equals("true");
	}

	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

}
