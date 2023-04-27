package com.ac.core.validation.validator.idno;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Alan Chen
 * @description 证件号码校验器
 * @date 2023/04/27
 */
public class IdNoValidator implements ConstraintValidator<IdNo, String> {

    private boolean require = false;

    private String regExp;

    @Override
    public void initialize(IdNo idNo) {
        require = idNo.required();
        regExp = idNo.regExp();
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
