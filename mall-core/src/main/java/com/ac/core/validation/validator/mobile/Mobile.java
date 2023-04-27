package com.ac.core.validation.validator.mobile;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MobileValidator.class})
public @interface Mobile {

    boolean required() default true;

    String message() default "参数不正确";

    String regExp() default MobileRegExp.MOBILE_REG_EXP;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
