package com.seveneleven.member.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.YN;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Getter
@Setter
public class MyPageGetMember {
    private Long memberId;
    private Long companyId;
    private String company;
    private YN companyStatus;
    private String loginId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String department;
    private String position;
    private String imageUrl;

    @Override
    public String toString() {
        return "MyPageGetMember{" +
                "companyId=" + companyId +
                '}';
    }

    // **Member로 변환하는 메서드**
    private static final ModelMapper modelMapper = new ModelMapper();

    public static MyPageGetMember fromEntity(Member member) {
        return modelMapper.map(member, MyPageGetMember.class);
    }
}
