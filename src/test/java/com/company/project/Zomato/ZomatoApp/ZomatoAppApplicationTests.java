package com.company.project.Zomato.ZomatoApp;

import com.company.project.Zomato.ZomatoApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZomatoAppApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;


	@Test
	void contextLoads() {
		emailSenderService.sendEmail("yashchauhan.gaya@gmail.com",
				                  "koi nhi mil rha saath khane ko....??",
				                "Order now!! from zomato where you will get flat extra 20% discount for the person who is single");
	}

	@Test
	void sendEmailMultiple(){
		String emails[]  = {
				"muditchugh77@gmail.com",
		};
		emailSenderService.sendEmail(emails, "koi nhi mil rha saath khane ko....?",
				"Order now!! from zomato where you will get flat extra 20% discount for the person who is single");
	}
}
