package javaStudying;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-04-21.
 */
public class KMPAlogrithm {
    public static void main(String[] args) {
        String s = "abbabababcaab"; // 主串
        String t = "abababca"; // 模式串
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
        System.out.println(KMP_Index(ss, tt)); // KMP匹配字符串

        int[] next = new int[tt.length];
        System.out.println(Arrays.toString(makeNext(tt,next)));


    }

    /**
     * 获得字符串的next函数值
     *
     * @param t 字符串
     * @return next函数值
     * <p>
     * 1、返回的结果是第一个数字是-1 例如："abababca"的next表手工计算出来是：next:[ 0, 0, 1, 2, 3, 4, 0, 1]
     * 此方法计算出来的next值：next:[-1, 0, 0, 1, 2, 3, 4, 0, 1]
     * 2、注意将初始值的设置：j=-1，next[0]=-1,设计next数组要+1，否则会越界异常
     * 3、if体中有一段优化的，暂时没有过分考虑了，有时间再研究研究；
     */
    public static int[] getNext(char[] t) {
        int[] next = new int[t.length + 1];
        int i = 0;
        int j = -1;
        next[0] = -1;
        while (i < t.length) {
            if (j == -1 || t[i] == t[j]) {
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
     * KMP匹配字符串
     *
     * @param s 主串
     * @param p 模式串
     * @return 若匹配成功，返回下标，否则返回-1
     * 1、返回的是模式串（patterns）与目标串（sources），第一次发生完全匹配的下标
     */
    public static int KMP_Index(char[] s, char[] p) {
        int[] next = getNext(p);
        int i = 0;
        int j = 0;
        while (i <= s.length - 1 && j <= p.length - 1) {
            if (j == -1 || s[i] == p[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j < p.length) {
            return -1;
        } else
            return i - p.length; // 返回模式串在主串中的头下标
    }

    /**
     *
     * @param P pattern串
     * @param next  next数组
     * @return next数组
     *
     * 1、第二种方法获取next数组，返回的是手工计算的next数组 next:[0, 0, 1, 2, 3, 4, 0, 1]
     */
    public static int[] makeNext(char[] P, int[] next) {
        int q, k;//q:模版字符串下标；k:最大前后缀长度
        int m = P.length;//模版字符串长度
        next[0] = 0;//模版字符串的第一个字符的最大前后缀长度为0
        for (q = 1, k = 0; q < m; ++q)//for循环，从第二个字符开始，依次计算每一个字符对应的next值
        {
            while (k > 0 && P[q] != P[k]) {//递归的求出P[0]···P[q]的最大的相同的前后缀长度k
                k = next[k - 1];
            }//不理解没关系看下面的分析，这个while循环是整段代码的精髓所在，确实不好理解
            if (P[q] == P[k])//如果相等，那么最大相同前后缀长度加1
            {
                k++;
            }
            next[q] = k;
        }
        return next;
    }

}
