package com.frame.framelibrary.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangdehao on 2018/9/13.
 */

public class PatternUtils {

    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<([^>]*)>";

    /**
     * 是否存在HTML标签
     * @param text
     * @return
     */
    public static boolean matcherHtml(String text) {
        Pattern pattern = Pattern.compile(REGEX_HTML);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}
