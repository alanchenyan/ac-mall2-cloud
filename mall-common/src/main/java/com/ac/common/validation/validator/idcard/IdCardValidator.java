package com.ac.common.validation.validator.idcard;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardValidator implements ConstraintValidator<IdCard, String> {

    /**
     * ^ 表示匹配字符串的开始位置。
     * 1 表示手机号码开头必须是数字 1。
     * [3-9] 表示第二个数字必须是 3、4、5、6、7、8、9 中的任意一个。
     * \d 表示任意数字。
     * {9}表示前面的数字必须出现9次。
     * $ 表示匹配字符串的结束位置。
     */
    private final static Pattern MOBILE_PATTERN = Pattern.compile("/^1[3-9]\\d{9}$/");

    private boolean require = false;

    @Override
    public void initialize(IdCard constraintAnnotation) {
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
        Matcher m = MOBILE_PATTERN.matcher(src);
        return m.matches();
    }
}
