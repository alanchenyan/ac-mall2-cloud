package com.ac.core.validation.validator.idno;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Alan Chen
 * @description 证件号码校验注解
 * @date 2023/04/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IdNoValidator.class})
public @interface IdNo {

    boolean required() default true;

    String message() default "参数不正确";

    String regExp() default IdNoRegExp.ID_CARD_REG_EXP;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
