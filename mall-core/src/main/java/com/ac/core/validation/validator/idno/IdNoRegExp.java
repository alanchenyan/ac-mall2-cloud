package com.ac.core.validation.validator.idno;

/**
 * @author Alan Chen
 * @description 证件号码校验正则表达式
 * @date 2023/04/27
 */
public class IdNoRegExp {

    /**
     * 身份证号码
     */
    public final static String ID_CARD_REG_EXP = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)$";

    /**
     * 港澳台证
     */
    public final static String HK_MT_REG_EXP = "([A-Z]{1,2}[0-9]{6}([0-9A]))|(^[1|5|7][0-9]{6}\\([0-9Aa]\\))|([A-Z][0-9]{9})";

    /**
     * 护照(含港澳)
     */
    public final static String PASSPORT_REG_EXP = "(^[EeKkGgDdSsPpHh]\\d{8}$)|(^(([Ee][a-fA-F])|([DdSsPp][Ee])|([Kk][Jj])|([Mm][Aa])|(1[45]))\\d{7}$)";
}
