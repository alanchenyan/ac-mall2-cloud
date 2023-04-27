package com.ac.core.validation.validator.mobile;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private boolean require = false;

    private String regExp;

    @Override
    public void initialize(Mobile mobile) {
        require = mobile.required();
        regExp = mobile.regExp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (require == false) {
            return true;
        }
        return isMobile(value);
    }

    private boolean isMobile(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return Pattern.compile(regExp).matcher(value).matches();
    }
}
