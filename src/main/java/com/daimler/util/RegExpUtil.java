package com.daimler.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RegExpUtil {
    private RegExpUtil() {
    }

    public static boolean isMatchByRegExpression(String pattern, String content) {
        Pattern compiledPattern = Pattern.compile(pattern);

        return compiledPattern.matcher(content).find();
    }

    public static List<String> getAllValuesByRegExpressionAndGroupIndex(String pattern, String content, int index) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(content);
        List<String> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(matcher.group(index));
        }
        return result;
    }
}
