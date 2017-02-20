package com.example.beyondsys.ppv.tools;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhsht on 2017/1/12.验证服务
 */
public class ValidaService {

    public boolean isNull()
    {
        return true;
    }

    /**
     * 验证用户姓名且字符长度为1~50也就是2~25个汉字
     *
     * @param userName
     * @return
     */
    public static boolean isUserName(String userName) {
        Pattern pattern = Pattern.compile("^([\u4E00-\u9FA5]|[\uF900-\uFA2D]|[\u258C]|[\u2022]|[\u2E80-\uFE4F])+$");
        Matcher mc = pattern.matcher(userName);
        return mc.matches();
    }

    /**
     * 邮箱验证
     *
     * @param mail
     * @return
     */
    public static boolean isValidEmail(String mail) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})");
        Matcher mc = pattern.matcher(mail);
        return mc.matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通） 147
         * 177 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 判断输入是否为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断手机号是否合法
     */
    public static boolean isPhone(String mobiles) {
        // Pattern p =
        // Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Pattern p = Pattern.compile("1[0-9]{10}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证联系方式
     *
     * @param str
     *            座机或手机
     * @return
     */
    public static boolean isMobileOrPhone(String str) {
        String regex = "^((([0\\+]\\d{2,3}-)|(0\\d{2,3})-))(\\d{7,8})(-(\\d{3,}))?$|^1[0-9]{10}$";
        return match(regex, str);
    }

    /**
     * 验证金额有效性
     *
     * @param price
     * @return
     */
    public static boolean isPrice(String price) {
        String regex = "^([1-9][0-9]{0,7})(\\.\\d{1,2})?$";
        return match(regex, price);
    }

    /**
     * 判断是否含有中文
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为纯英文
     */
    public static boolean isLetter(String str) {
        Pattern p = Pattern.compile("^[A-Za-z]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否为纯中文
     */
    public static boolean isChiness(String str) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FFF]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断必须是否为密码类型
     */
    public static boolean isPassword(String str) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z.*]{6,20}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断邮件email是否正确格式
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email))
            return false;
        // Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 用于判断用户输入字符长度6到20位
     *
     * @param str
     * @return
     */
    public static boolean isLength(String str) {
        Pattern pattern = Pattern.compile(".{6,20}");
        return pattern.matcher(str).matches();
    }

    /**
     * 用于判断用户输入住址字符长度0到250位
     *
     * @param str
     * @return
     */
    public static boolean isAddressLength(String str) {
        Pattern pattern = Pattern.compile(".{0,250}");
        return pattern.matcher(str).matches();
    }

    /**
     * 用于判断用户输入描述字符长度0到50位
     *
     * @param str
     * @return
     */
    public static boolean isRemarksLength(String str) {
        Pattern pattern = Pattern.compile(".{0,50}");
        return pattern.matcher(str).matches();
    }
    /**
     * 用于判断用户输入标题字符长度0到50位
     *
     * @param str
     * @return
     */
    public static boolean isTitleLength(String str) {
        Pattern pattern = Pattern.compile(".{2,50}");
        return pattern.matcher(str).matches();
    }

    /**
     * 用于判断用户真名长度
     *
     * @param str
     * @return
     */
    public static boolean isNameLength(String str) {
        Pattern pattern = Pattern.compile(".{2,25}");
        return pattern.matcher(str).matches();
    }

    /**
     * 用于判断用户输入字符首字母是否英文
     *
     * @param str
     * @return
     */
    public static boolean isEnglish(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]+");
        return pattern.matcher(str).matches();
    }

//    /**
//     * 用于判断银行卡位数13到19位
//     *
//     * @param str
//     * @return
//     */
//    public static boolean isbank(String str) {
//        Pattern pattern = Pattern.compile(".{13,19}");
//        return pattern.matcher(str).matches();
//    }

    /**
     * 中文占两个字符，英文占一个
     */
    public static int String_length(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 过滤特殊字符
     */
    public static String stringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 验证输入密码长度 (6-20位除空格回车tab外的字符)
     */
    public static boolean isPasswLength(String str) {
        String regex = "^\\S{6,20}$";
        return match(regex, str);
    }

    /**
     * @param regex
     *            正则表达式字符串
     * @param str
     *            要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：根据身份证号获取用户生日
     *
     * @return
     */
    public static String getUserBrithdayByCardId(String ids) {
        String brithday = "";
        if (ids.length() == 18) {
            brithday = ids.substring(6, 14); // 18位
            String years = brithday.substring(0, 4);
            String moths = brithday.substring(4, 6);
            String days = brithday.substring(6, 8);
            brithday = years + "-" + moths + "-" + days;

        } else if (ids.length() == 15) {
            brithday = ids.substring(6, 12); // 15位
            String years = brithday.substring(0, 2);
            String moths = brithday.substring(2, 4);
            String days = brithday.substring(4, 6);
            brithday = "19" + years + "-" + moths + "-" + days;
        }

        return brithday;
    }

    /**
     * 功能：根据身份证号获取用户性别
     *
     * @return
     */
    public static String getUserSexByCardId(String ids) {
        String sexshow = "";
        if (ids.length() == 18) {
            String sexstring = ids.trim().substring(ids.length() - 2, ids.length() - 1); // 取得身份证倒数第二位
            int sexnum = Integer.parseInt(sexstring); // 转换成数字
            if (sexnum % 2 != 0) {
                sexshow = "男";
            } else {
                sexshow = "女";
            }
        } else if (ids.length() == 15) {
            String sexstring = ids.trim().substring(ids.length() - 1, ids.length()); // 取得身份证最后一位
            int sexnum = Integer.parseInt(sexstring); // 转换成数字
            if (sexnum % 2 != 0) {
                sexshow = "男";
            } else {
                sexshow = "女";
            }
        }

        return sexshow;
    }

    // 判断身份证号码是否有效
    public static boolean isValidIdCard(String idCard) {
        return IdcardValidator.isValidateIdcard(idCard);
    }

    // 校验身份证的基本组成
    public boolean isIdcard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    // 校验15身份证的基本组成
    public boolean is15Idcard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    // 校验18身份证的基本组成
    public boolean is18Idcard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    /**
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     *
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     *
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     *
     * 第十八位数字(校验码)的计算方法为：
     *      1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     *      2.将这17位数字和系数相乘的结果相加。
     *      3.用加出来和除以11，看余数是多少？
     *      4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为: 1 0 X 9 8 7 6 5 4 3 2;
     *      5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     */
    private static class IdcardValidator {

        // 省,直辖市代码表
        private static final String codeAndCity[][] = { { "11", "北京" }, { "12", "天津" },
                { "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" }, { "21", "辽宁" },
                { "22", "吉林" }, { "23", "黑龙江" }, { "31", "上海" }, { "32", "江苏" },
                { "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" },
                { "37", "山东" }, { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" },
                { "44", "广东" }, { "45", "广西" }, { "46", "海南" }, { "50", "重庆" },
                { "51", "四川" }, { "52", "贵州" }, { "53", "云南" }, { "54", "西藏" },
                { "61", "陕西" }, { "62", "甘肃" }, { "63", "青海" }, { "64", "宁夏" },
                { "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" },
                { "91", "国外" } };

        // 每位加权因子
        private static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

        // 判断18位身份证号码是否有效
        private static boolean isValidate18Idcard(String idcard) {
            if (idcard.length() != 18) {
                return false;
            }
            String idcard17 = idcard.substring(0, 17);
            String idcard18Code = idcard.substring(17, 18);
            char c[];
            String checkCode;
            if (isDigital(idcard17)) {
                c = idcard17.toCharArray();
            } else {
                return false;
            }

            if (null != c) {
                int bit[] = converCharToInt(c);
                int sum17 = getPowerSum(bit);

                // 将和值与11取模得到余数进行校验码判断
                checkCode = getCheckCodeBySum(sum17);
                if (null == checkCode) {
                    return false;
                }
                // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
                if (!idcard18Code.equalsIgnoreCase(checkCode)) {
                    return false;
                }
            }

            return true;
        }

        // 将15位的身份证转成18位身份证
        public static String convertIdcarBy15bit(String idcard) {
            String idcard18 = null;
            if (idcard.length() != 15) {
                return null;
            }

            if (isDigital(idcard)) {
                // 获取出生年月日
                String birthday = idcard.substring(6, 12);
                Date birthdate = null;
                try {
                    birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cday = Calendar.getInstance();
                cday.setTime(birthdate);
                String year = String.valueOf(cday.get(Calendar.YEAR));

                idcard18 = idcard.substring(0, 6) + year + idcard.substring(8);

                char c[] = idcard18.toCharArray();
                String checkCode = "";

                if (null != c) {
                    int bit[] = converCharToInt(c);
                    int sum17;
                    sum17 = getPowerSum(bit);
                    // 获取和值与11取模得到余数进行校验码
                    checkCode = getCheckCodeBySum(sum17);
                    // 获取不到校验位
                    if (null == checkCode) {
                        return null;
                    }

                    // 将前17位与第18位校验码拼接
                    idcard18 += checkCode;
                }
            } else {
                return null;
            }

            return idcard18;
        }

        // 是否全部由数字组成
        public static boolean isDigital(String str) {
            return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
        }

        // 将身份证的每位和对应位的加权因子相乘之后，再得到和值
        public static int getPowerSum(int[] bit) {
            int sum = 0;
            if (power.length != bit.length) {
                return sum;
            }

            for (int i = 0; i < bit.length; i++) {
                for (int j = 0; j < power.length; j++) {
                    if (i == j) {
                        sum = sum + bit[i] * power[j];
                    }
                }
            }

            return sum;
        }

        // 将和值与11取模得到余数进行校验码判断
        private static String getCheckCodeBySum(int sum17) {
            String checkCode = null;
            switch (sum17 % 11) {
                case 10:
                    checkCode = "2";
                    break;
                case 9:
                    checkCode = "3";
                    break;
                case 8:
                    checkCode = "4";
                    break;
                case 7:
                    checkCode = "5";
                    break;
                case 6:
                    checkCode = "6";
                    break;
                case 5:
                    checkCode = "7";
                    break;
                case 4:
                    checkCode = "8";
                    break;
                case 3:
                    checkCode = "9";
                    break;
                case 2:
                    checkCode = "x";
                    break;
                case 1:
                    checkCode = "0";
                    break;
                case 0:
                    checkCode = "1";
                    break;
            }

            return checkCode;
        }

        // 将字符数组转为整型数组
        private static int[] converCharToInt(char[] c) throws NumberFormatException {
            int[] a = new int[c.length];
            int k = 0;
            for (char temp : c) {
                a[k++] = Integer.parseInt(String.valueOf(temp));
            }

            return a;
        }

        // 验证身份证号码是否有效
        public static boolean isValidateIdcard(String idcard) {
            if (!TextUtils.isEmpty(idcard)) {
                if (idcard.length() == 15) {
                    return isValidate18Idcard(convertIdcarBy15bit(idcard));
                } else if (idcard.length() == 18) {
                    return isValidate18Idcard(idcard);
                }
            }

            return false;
        }
    }
}
