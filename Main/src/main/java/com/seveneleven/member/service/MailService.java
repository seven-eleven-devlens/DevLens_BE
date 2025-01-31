package com.seveneleven.member.service;

import com.seveneleven.member.dto.CheckMailPostRequest;
import com.seveneleven.util.security.dto.CustomUserDetails;

public interface MailService {

    String sendEmail(CustomUserDetails userDetails);

    boolean checkMail(CustomUserDetails userDetails, CheckMailPostRequest request);
}
