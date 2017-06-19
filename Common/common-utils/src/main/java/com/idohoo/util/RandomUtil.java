package com.idohoo.util;

import java.util.Random;

/**
 * Created by henser on 17-5-11.
 */
public class RandomUtil {

    /**
     * 生成随机文件名：五位随机数
     *
     * @return
     */
    public static String getRandom(int strLength) {

        Random random = new Random();

        // 获得随机数
        double pross = (1 + random.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);

    }

    public static void main(String[] args) {
        System.out.println("i:"+getRandom(5));
    }

}
