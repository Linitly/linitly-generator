package org.mybatis.generator.linitly;

public class CommonUtil {

    public static String lowerFirst(String oldStr){
        char[]chars = oldStr.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
