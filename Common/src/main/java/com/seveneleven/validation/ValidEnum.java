package com.seveneleven.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "유효하지 않은 값입니다.";
    Class<?>[] groups() default {}; // Validation 그룹 (사용하지 않으면 빈 배열)
    Class<? extends Payload>[] payload() default {}; // 추가 메타데이터 전달 용도 (일반적으로 사용 X)
}
