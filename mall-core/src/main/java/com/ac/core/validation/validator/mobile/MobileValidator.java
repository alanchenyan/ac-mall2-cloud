package com.ac.core.validation.validator.mobile;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Alan Chen
 * @description 手机号码校验器
 * @date 2023/04/27
 */
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
        return regExpMatch(value);
    }

    private boolean regExpMatch(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return Pattern.compile(regExp).matcher(value).matches();
    }
}
