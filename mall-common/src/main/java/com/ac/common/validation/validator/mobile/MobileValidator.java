package com.ac.common.validation.validator.mobile;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    private boolean require = false;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        require = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (require == false) {
            return true;
        }
        return isMobile(s);
    }

    private boolean isMobile(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
}
