package com.ac.common.validation.validator.idcard;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IdCardValidator.class})
public @interface IdCard {

    boolean required() default true;

    String message() default "参数不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
