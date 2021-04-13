package com.javaee.stockmarket.email;

import org.springframework.stereotype.Service;

import com.javaee.stockmarket.config.EmailConfig;

@Service
public class Sender {

	public void sendEmail(String toEmail, String subject, String body) {		
		EmailConfig config = new EmailConfig();
		
		System.out.println("Sending Email");
		
		config.sendEmail(toEmail, subject, body);
	}
}
