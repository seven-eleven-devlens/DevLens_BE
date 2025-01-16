package com.seveneleven.member.service;

import com.seveneleven.member.dto.CheckMailPostRequest;

public interface MailService {

    String sendEmail(String email);

    boolean checkMail(String email, CheckMailPostRequest request);
}
