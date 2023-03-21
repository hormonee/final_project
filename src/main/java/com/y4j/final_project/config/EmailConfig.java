package com.y4j.final_project.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailConfig {

	@Value("${spring.mail.smtp.port}")
	private int port;
	@Value("${spring.mail.smtp.socketFactory.port}")
	private int socketPort;
	@Value("${spring.mail.smtp.auth}")
	private boolean auth;
	@Value("${spring.mail.smtp.starttls.enable}")
	private boolean starttls;
	@Value("${spring.mail.smtp.starttls.required}")
	private boolean startlls_required;
	@Value("${spring.mail.smtp.socketFactory.fallback}")
	private boolean fallback;
	@Value("${spring.AdminMail.id}")
	private String id;
	@Value("${spring.AdminMail.password}")
	private String password;

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setUsername(id);
		javaMailSender.setPassword(password);
		javaMailSender.setPort(port);
		javaMailSender.setJavaMailProperties(getMailProperties());
		javaMailSender.setDefaultEncoding("UTF-8");
		return javaMailSender;
	}
	private Properties getMailProperties()
	{
		Properties pt = new Properties();
		pt.put("spring.mail.smtp.socketFactory.port", socketPort);
		pt.put("spring.mail.smtp.auth", auth);
		pt.put("spring.mail.smtp.starttls.enable", starttls);
		pt.put("spring.mail.smtp.starttls.required", startlls_required);
		pt.put("spring.mail.smtp.socketFactory.fallback",fallback);
		pt.put("spring.mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		return pt;
	}
}
