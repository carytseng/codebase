package cn.cary.codebase.util;


import org.apache.logging.log4j.util.Strings;

/**
 * @program: codebase
 * @description: 个人信息脱敏工具
 * @author: 郑剑锋
 * @create: 2021-04-15 11:35
 **/
public class WordsDesensitizationUtil {

    private static String REPLACE_CHAR = "*";

    /**
     * 脱敏
     *
     * @param str   待脱敏字符串
     * @param left  左边保留多少位
     * @param right 右边保留多少位
     * @return 脱敏结果，除左右外，其余字符将被替换为*
     */
    public static String around(String str, int left, int right) {
        if (str == null || (str.length() < left + right + 1)) {
            return str;
        }
        String regex = String.format("(?<=\\w{%d})\\w(?=\\w{%d})", left, right);
        return str.replaceAll(regex, REPLACE_CHAR);
    }

    /**
     * 身份证号脱敏
     *
     * @param cardNum 身份证号码
     * @return 脱敏后身份证号码，形如510**********1232
     */
    public static String desensitizateIdCard(String cardNum) {
        return around(cardNum, 3, 4);
    }

    /**
     * 电话号码脱敏
     *
     * @param phone
     * @return 脱敏后电话号码
     */
    public static String desensitizatePhone(String phone) {
        return around(phone, 3, 4);
    }

    /**
     * 姓名脱敏
     *
     * @param name
     * @return
     */
    public static String desensitizeName(String name) {
        if (Strings.isNotBlank(name)) {
            char[] chars = name.toCharArray();
            for (int i = 1; i < chars.length; i++) {
                chars[i] = '*';
            }
            return String.valueOf(chars);
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(desensitizeName("张蓓蓓"));
    }
}
