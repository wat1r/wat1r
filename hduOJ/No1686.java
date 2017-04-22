package hduOJ;

/**
 * Created by Administrator on 2017-04-22.
 */

//import java.util.Arrays;

import java.util.Scanner;

/**
 * Created by Administrator on 2017-04-22.
 */
public class No1686 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String pStr = sc.next();
            String sStr = sc.next();
            char[] s = sStr.toCharArray();
            char[] p = pStr.toCharArray();
            System.out.println(calCount(s, p));
        }

    }

    /**
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
//        System.out.println("next:" + Arrays.toString(next));
        return next;

    }

    /**
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
//            System.out.println("original----i=" + i + "  j=" + j);
            if (j == -1 || s[i] == p[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
            if (j == p.length) {
//                System.out.println("j:" + j + "==" + "p.length:" + p.length);
                count++;
                j = next[j];//此处将j=0，否则只打印一次即停止
            }
//            System.out.println("count=" + count);
//            System.out.println("--------" + i + "---------");
        }
        return count;
    }
}
