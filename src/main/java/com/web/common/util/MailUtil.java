package com.web.common.util;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.common.command.CommandMap;

/**
 * @파일명 : DisMailUtil.java
 * @프로젝트 : gePower
 * @날짜 : 2015. 12. 17.
 * @작성자 : 조 흠준
 * @설명
 * 
 *     <pre>
 * 	메일 발송에 관한 Util
 *     </pre>
 */
public class MailUtil
{

	@Resource(name = "mailSender")
	private static JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender)
	{
		MailUtil.mailSender = mailSender;
	}

	@RequestMapping(value = "/mailSender")
	public static void sendEmail(CommandMap commandMap) throws AddressException, MessagingException
	{

		String fromEmail = (String) commandMap.get("fromEmail");
		String toEmail = (String) commandMap.get("toEmail");
		//String ccEmail = (String) commandMap.get("ccEmail");
		String subject = (String) commandMap.get("subject");
		String body = (String) commandMap.get("body");

		// 네이버일 경우 smtp.naver.com,smtp.gmail.com 을 입력합니다.
		String host = MessageUtil.getMessage("mail.smtp.host");
		// 네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시고요
		final String username = MessageUtil.getMessage("mail.admin.username");
		// 네이버 이메일 비밀번호를 입력해주세요.
		final String password = MessageUtil.getMessage("mail.admin.password");
		// 포트번호
		int port = Integer.parseInt(MessageUtil.getMessage("mail.smtp.port"));

		Properties props = System.getProperties();
		// 정보를 담기 위한 객체 생성
		// SMTP 서버 정보 설정
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);

		// Session 생성
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String	un	= username;
			String	pw	= password;

			protected javax.mail.PasswordAuthentication getPasswordAuthentication()
			{
				return new javax.mail.PasswordAuthentication(un, pw);
			}
		});
		// for debug
		session.setDebug(Boolean.valueOf(MessageUtil.getMessage("mail.smtp.debug"))); 

		// MimeMessage 생성
		MimeMessage mimeMessage = new MimeMessage(session);
		// 발신자 셋팅, 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
		mimeMessage.setFrom(new InternetAddress(fromEmail));

		// 수신자셋팅
		// .TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

		mimeMessage.setSubject(subject, "UTF-8");
		mimeMessage.setText(body, "UTF-8", "html");
		// 내용셋팅
		Transport.send(mimeMessage); // javax.mail.Transport.send() 이용

	}

	// 스프링을 이용한 메일 보내기 개인서버 전용으로 전환시
	/*
	 * public static void sendEmail1(CommandMap commandMap) throws Exception {
	 * 
	 * // String fromEmail = (String) commandMap.get("fromEmail"); String
	 * fromEmail = "jjs6582@gmail.com"; String toEmail = (String)
	 * commandMap.get("toEmail"); String ccEmail = (String)
	 * commandMap.get("ccEmail"); String subject = (String)
	 * commandMap.get("subject"); String body = (String) commandMap.get("body");
	 * 
	 * System.out.println(">>> " + fromEmail); System.out.println(">>> " +
	 * toEmail); System.out.println(">>> " + ccEmail); System.out.println(">>> "
	 * + subject); System.out.println(">>> " + body);
	 * 
	 * MimeMessage message = mailSender.createMimeMessage();
	 * 
	 * if (Constants.Y.equals(MessageUtil.getMessage("mail.test"))) {
	 * message.addRecipients(RecipientType.TO, "heumjoon.cho@doosan.com"); }
	 * else { message.addRecipients(RecipientType.TO, toEmail);
	 * message.addRecipients(RecipientType.CC, ccEmail); }
	 * 
	 * message.setFrom(new InternetAddress("jjs6582@gmail.com"));
	 * message.setSubject(subject, "UTF-8"); message.setText(body, "UTF-8",
	 * "html");
	 * 
	 * try { mailSender.send(message); } catch (MailException e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
}
