package cn.cary.codebase.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName SensitiveUtil.java
 * @Description TODO
 * @createTime 2021年08月06日 09:56:00
 */
public class SensitiveUtil {
    public static final String COMMON = "******";
    private static final String CLASSIFIED = "保密";
    private static final String SIX_STAR = "******";

    public SensitiveUtil() {
    }

    public static String mobile(String mobile) {
        return StringUtils.isEmpty(mobile) ? "" : StringUtils.left(mobile, 2).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(mobile, 4), StringUtils.length(mobile), "*"), "***"));
    }

    public static String idCard(String idCard) {
        return StringUtils.isEmpty(idCard) ? "" : StringUtils.left(idCard, 2).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idCard, 4), StringUtils.length(idCard), "*"), "***"));
    }

    public static String email(String email) {
        return "保密";
    }

    public static String password() {
        return "******";
    }

    public static String chineseName(String chineseName) {
        return StringUtils.isEmpty(chineseName) ? "" : StringUtils.left(chineseName, 2).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(chineseName, 4), StringUtils.length(chineseName), "*"), "***"));
    }
}
