package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.CheckMailPostRequest;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import com.seveneleven.util.security.SHA256Util;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender mailSender;
    private final MemberReader memberReader;

    /**
     * 함수명 : sendEmail
     * 사용자의 이메일로 인증 키를 전송하는 메서드. 인증 키는 생성 후 암호화되어 반환됩니다.
     *
     * @return 암호화된 인증 키
     */
    public String sendEmail(CustomUserDetails userDetails){

        if(Objects.isNull(userDetails)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_TOKEN);
        }

        Member member = memberReader.getActiveMemberByEmail(userDetails.getEmail());

        // 랜덤한 UUID 생성
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().substring(0, 7);


        try {
            MimeMessage emailForm = createHtmlEmailForm(member.getEmail(), key);
            mailSender.send(emailForm);
        } catch (MessagingException e) {
            throw new BusinessException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }

        key = SHA256Util.getEncrypt(key, userDetails.getEmail());

        return key;
    }

    /**
     * 함수명 : checkMail
     * 사용자가 입력한 인증 키를 검증하는 메서드.
     *
     * @param request 인증 키 검증 요청 객체 (이메일, 입력된 키, 서버 저장 키 포함)
     * @return 입력된 키가 서버 저장 키와 일치하면 true, 그렇지 않으면 false
     */
    public boolean checkMail(CustomUserDetails userDetails, CheckMailPostRequest request) {

        if(Objects.isNull(userDetails)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_TOKEN);
        }

        Member member = memberReader.getActiveMemberByEmail(userDetails.getEmail());

        String insertKey = SHA256Util.getEncrypt(request.getInputKey(), member.getEmail());


        if (request.getKey().equals(insertKey)) {
            return true;
        }else {
            throw new BusinessException(ErrorCode.EMAIL_AUTH_FAILED);
        }
    }

    /**
     * 함수명 : createEmailForm
     * 이메일 발송을 위한 SimpleMailMessage 객체를 생성하는 메서드.
     *
     * @param toEmail 수신자 이메일 주소
     * @param key 인증번호
     * @return 생성된 MimeMessage 객체
     */
    private MimeMessage createHtmlEmailForm(String toEmail, String key) throws MessagingException {
        String title = "[DevLens] 이메일 인증키 발급";

        // HTML 콘텐츠
        String htmlContent = "<div style='font-family: Arial, sans-serif; text-align: center; padding: 20px;'>"
                + "<h1 style='color: #4CAF50;'>이메일 인증키 발급</h1>"
                + "<p>아래의 인증번호를 입력해 이메일 인증을 완료해주세요.</p>"
                + "<div style='margin: 20px auto; padding: 15px; border: 2px solid #4CAF50; display: inline-block; font-size: 24px; color: #333; font-weight: bold;'>"
                + key
                + "</div>"
                + "<p style='color: gray;'>인증키를 타인과 공유하지 마세요.</p>"
                + "</div>";

        try {
            // MimeMessage 생성
            MimeMessage message = mailSender.createMimeMessage();

            // MimeMessageHelper로 HTML 설정
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(title);
            helper.setText(htmlContent, true); // HTML 콘텐츠로 설정 (true)

            return message;

        }catch (MessagingException e){
            throw new BusinessException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }

    }
}
