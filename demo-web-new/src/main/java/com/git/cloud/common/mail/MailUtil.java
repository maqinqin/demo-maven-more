package com.git.cloud.common.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.tools.PropertiesTools;

/**
 * 邮件工具类
 * @author SunHailong
 */
public class MailUtil {
	private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

	private final static String SENDER_USER = "SENDER_USER";
	private final static String SENDER_PWD = "SENDER_PWD";
	private final static String REPLY_ADDRESS = "REPLY_ADDRESS";
	private final static String SMTP_HOST = "SMTP_HOST";
	private final static String SMTP_AUTH = "SMTP_AUTH";
	private final static String MAIL_DEBUG = "MAIL_DEBUG";
	
	public static void sendMail(EmailBean email) throws MessagingException {
		String sender = PropertiesTools.getPropertiesMailValue(SENDER_USER);
		Session mailSession = getMailSession();
		MimeMessage message = new MimeMessage(mailSession);
		// 发件人
		message.setFrom(new InternetAddress(sender));
		// 收件人
		int mailtolen = 0;
		if (email.getMailTo() != null)
			mailtolen = email.getMailTo().size();
		if (mailtolen == 0) {
			System.out.println("没有指定收件人邮件地址！");
			return;
		}
		InternetAddress[] address = new InternetAddress[mailtolen];
		for (int i = 0; i < mailtolen; i++) {
			address[i] = new InternetAddress(email.getMailTo().get(i));
			System.out.println("收件人Email地址：" + email.getMailTo());
		}
		message.setRecipients(Message.RecipientType.TO, address);
		// 抄送人
		int mailccLen = 0;
		if (email.getMailccTo() != null)
			mailccLen = email.getMailccTo().size();
		if(mailccLen>0){
			InternetAddress[] ccaddress = new InternetAddress[mailccLen];
			for (int i = 0; i < mailccLen; i++) {
				ccaddress[i] = new InternetAddress(email.getMailccTo().get(i));
				System.out.println("抄送人Email地址：" + email.getMailccTo());
			}
			message.setRecipients(Message.RecipientType.CC, ccaddress);
		}
		// 主题
		message.setSubject(email.getSubject());
		// 设定发送时间
		message.setSentDate(new Date());
		// 设定退件接收地址
		InternetAddress[] replyAddress = {new InternetAddress(PropertiesTools.getPropertiesMailValue(REPLY_ADDRESS))};
		message.setReplyTo(replyAddress);
		// 邮件内容
		MimeMultipart multi = new MimeMultipart();
		BodyPart textBodyPart = new MimeBodyPart();
		textBodyPart.setText(email.getMsgContent());
		multi.addBodyPart(textBodyPart);
		message.setContent(multi);
		message.saveChanges();
		// 发送邮件
		Transport.send(message);
	}
	
	public static Session getMailSession() {
		String sender = PropertiesTools.getPropertiesMailValue(SENDER_USER);
		String password = PropertiesTools.getPropertiesMailValue(SENDER_PWD);
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.host", PropertiesTools.getPropertiesMailValue(SMTP_HOST));
		mailProps.put("mail.smtp.auth", PropertiesTools.getPropertiesMailValue(SMTP_AUTH));
		mailProps.put("username", sender);
		mailProps.put("password", password);
		Authenticator auth = new PopupAuthenticator(sender, password);
		Session mailSession = Session.getInstance(mailProps, auth);
		String mailDebug = PropertiesTools.getPropertiesMailValue(MAIL_DEBUG);
		mailSession.setDebug((mailDebug != null && "true".equals(mailDebug)));
		return mailSession;
	}
	
	public static void main(String[] args) {
		//测试邮件发送
		EmailBean emailBean = new EmailBean();
		List<String> to = new ArrayList<String> ();
		to.add("sunhailong@gitcloud.com.cn");
		emailBean.setMailTo(to);
		emailBean.setSubject("测试邮件");
		emailBean.setMsgContent("吃过了吗？");
		try {
			MailUtil.sendMail(emailBean);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}
}
