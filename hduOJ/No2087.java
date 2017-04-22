package hduOJ;
/**
 * NO2087 剪花布条，利用KMP算法
 */

import java.util.Scanner;

/**
 * Created by Administrator on 2017-04-22.
 */
public class No2087 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String sStr = sc.next();
            if (sStr.equals("#")) {
                System.exit(0);
            }
            String pStr = sc.next();
            if (sStr.equals("#")) {
                System.exit(0);
            }


            char[] s = sStr.toCharArray();
            char[] p = pStr.toCharArray();
            System.out.println(calCount(s, p));

        }
    }

    /**
     *
     * @param p 模式字符串pattern
     * @return next数组
     * KMP算法中的next数组求解
     */
    public static int[] getNext(char[] p) {
        int[] next = new int[p.length + 1];
        int i = 0;
        int j = -1;
        next[0] = -1;
        while (i < p.length) {
            if (j == -1 || p[i] == p[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    /**
     *
     * @param s 目标字符串sources
     * @param p 模式字符串pattern
     * @return count 匹配的次数
     */
    public static int calCount(char[] s, char[] p) {

        int[] next = getNext(p);
        int i = 0;
        int j = 0;
        int count = 0;
        while (i < s.length && j < p.length) {
            if (j == -1 || s[i] == p[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
            if (j == p.length) {
                count++;
                j = 0;//此处将j=0，否则只打印一次即停止

            }
        }
        return count;
    }
}
