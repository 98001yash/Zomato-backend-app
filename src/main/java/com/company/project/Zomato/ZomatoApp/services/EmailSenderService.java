package com.company.project.Zomato.ZomatoApp.services;

public interface  EmailSenderService {
     void sendEmail(String toEmail,String subject, String body);

     void sendEmail(String toEmail[], String subject, String body);


}
