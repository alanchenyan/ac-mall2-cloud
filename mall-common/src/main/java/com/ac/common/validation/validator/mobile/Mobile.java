package com.ac.common.validation.validator.mobile;

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

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
