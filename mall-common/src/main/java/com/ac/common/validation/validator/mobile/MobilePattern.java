package com.ac.common.validation.validator.mobile;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class MobilePattern {

    /**
     * 中国大陆、澳门、香港和台湾：
     * ^ 表示匹配字符串的开始位置。
     * 1 表示手机号码开头必须是数字 1（适用于中国大陆）。
     * [3-9] 表示第二个数字必须是 3、4、5、6、7、8、9 中的任意一个（适用于中国大陆）。
     * [5689] 表示手机号码开头必须是数字 5、6、8、9 中的任意一个（适用于澳门和香港）。
     * 09 表示手机号码开头必须是数字 09（适用于台湾）。
     * \d 表示任意数字。
     * {7} 或 {8} 或 {9} 表示前面的数字必须重复出现 7 次（适用于澳门和香港）或 8 次（适用于台湾）或 9 次（适用于中国大陆）。
     * | 表示逻辑或。
     * () 表示分组，用于将三个表达式组合在一起。
     * $ 表示匹配字符串的结束位置。
     */
    private final static Pattern MOBILE_PATTERN = Pattern.compile("/^(1[3-9]\\d{9}|[5689]\\d{7}|09\\d{8})$/");


    /**
     * 中国-大陆：
     * ^ 表示匹配字符串的开始位置。
     * 1 表示手机号码开头必须是数字 1。
     * [3-9] 表示第二个数字必须是 3、4、5、6、7、8、9 中的任意一个。
     * \d 表示任意数字。
     * {9}表示前面的数字必须出现9次。
     * $ 表示匹配字符串的结束位置。
     */
    private final static Pattern MOBILE_PATTERN_ZH_CN = Pattern.compile("/^1[3-9]\\d{9}$/");

    /**
     * 中国-澳门：
     * 澳门手机号码格式为8位数字，以6开头
     * ^ 表示匹配字符串的开始位置。
     * 6 表示手机号码开头必须是数字 6。
     * \d 表示任意数字。
     * {7} 表示前面的数字必须重复出现 7 次。
     * $ 表示匹配字符串的结束位置。
     */
    private final static Pattern MOBILE_PATTERN_ZH_MO = Pattern.compile("/^6\\d{7}$/");

    /**
     * 中国-香港：
     * 香港手机号码格式为8位数字，以5、6、8、9开头
     * ^ 表示匹配字符串的开始位置。
     * [5689] 表示手机号码开头必须是数字 5、6、8、9 中的任意一个。
     * \d 表示任意数字。
     * {7} 表示前面的数字必须重复出现 7 次。
     * $ 表示匹配字符串的结束位置。
     */
    private final static Pattern MOBILE_PATTERN_ZH_HK = Pattern.compile("/^[5689]\\d{7}$/");

    /**
     * 中国-台湾：
     * 台湾地区的手机号码开头一般是09，接下来是八位数字
     * ^ 表示匹配字符串的开始位置。
     * 09 表示手机号码开头必须是数字 09。
     * \d 表示任意数字。
     * {8} 表示前面的数字必须重复出现 8 次。
     * $ 表示匹配字符串的结束位置。
     */
    private final static Pattern MOBILE_PATTERN_ZH_TW = Pattern.compile("/^09\\d{8}$/");

    /**
     * 手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        return matchMobile(mobile, MOBILE_PATTERN);
    }

    /**
     * 中国-大陆
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileZhCn(String mobile) {
        return matchMobile(mobile, MOBILE_PATTERN_ZH_CN);
    }

    /**
     * 中国-澳门
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileZhMo(String mobile) {
        return matchMobile(mobile, MOBILE_PATTERN_ZH_MO);
    }

    /**
     * 中国-香港
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileZhHk(String mobile) {
        return matchMobile(mobile, MOBILE_PATTERN_ZH_HK);
    }

    /**
     * 中国-台湾
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileZhTw(String mobile) {
        return matchMobile(mobile, MOBILE_PATTERN_ZH_TW);
    }

    private static boolean matchMobile(String mobile, Pattern pattern) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return pattern.matcher(mobile).matches();
    }
}
