package com.android.framework.jc.widget;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/4/26 15:53
 * @describe 数字输入框过滤器
 * @update
 */
public class NumberInputFilter implements InputFilter {

    /**
     * 小数限制长度
     */
    private final int decimalDigits;
    /**
     * 整体数字长度
     */
    private final int numberLength;

    /**
     * Constructor.
     *
     * @param decimalDigits
     *         maximum decimal digits
     */
    public NumberInputFilter(int numberLength, int decimalDigits) {
        this.decimalDigits = decimalDigits;
        this.numberLength = numberLength;
    }

    public NumberInputFilter(int decimalDigits) {
        this(Integer.MAX_VALUE, decimalDigits);
    }

    @Override
    public CharSequence filter(CharSequence source,
                               int start,
                               int end,
                               Spanned dest,
                               int dstart,
                               int dend) {

        //小数点的位置
        int pointPosition = -1;
        int length = dest.length();
        //禁止小数位(如果小数长度小于1禁止输入.)
        String point = ".";
        if (point.contentEquals(source)) {
            if (decimalDigits < 1) {
                return "";
            }
            //光标在第一位
            else if (length < 1) {
                return "0" + point;
            }
        }

        //获取小数点位置
        for (int i = 0; i < length; i++) {
            char c = dest.charAt(i);
            if (c == '.' || c == ',') {
                pointPosition = i;
                break;
            }
        }
        //光标在首位且已经输入过当前不能再输入0
        if (dend == 0 && length > 0 && "0".contentEquals(source)) {
            return "";
        }

        //限制输入0之后只能输入小数点
        if (length == 1) {
            char c = dest.charAt(0);
            if (c == '0' && !point.contentEquals(source)) {
                return "";
            }
        }
        //已经输入过小数点
        if (pointPosition >= 0) {
            //已经输入过小数点禁止再输入小数点
            if (point.contentEquals(source)) {
                return "";
            }
            int decimalLength = length - pointPosition;
            //光标在小数点前面,当前输入整数的长度大于限制的整数长度
            if (dend <= pointPosition && pointPosition > numberLength) {
                return "";
            }
            //光标在小数点后面,当前输入小数的长度大于限制的小数长度
            if (dend > pointPosition && decimalLength > decimalDigits) {
                return "";
            }
        } else {
            //当前输入的不是小数点
            if (!point.contentEquals(source) && length >= numberLength) {
                return "";
            }
            //当前输入的是小数点
            if (point.contentEquals(source)) {
                //光标后面的长度
                int cursorRearLength = length - dend;
                if (cursorRearLength > decimalDigits) {
                    return "";
                }
                //光标在首位输入点前面补充0
                if (dend == 0) {
                    return "0" + point;
                }

            }


        }

        return null;
    }

}