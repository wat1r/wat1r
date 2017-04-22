package javaStudying;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-04-21.
 */
public class KMPTestDemo {
    public static void main(String[] args) {
        String s = "abbabababcaab"; // 主串
        String t = "abababca"; // 模式串
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
          System.out.println(KMP_Index(ss, tt)); // KMP匹配字符串
//
//        System.out.println(Arrays.toString(getNext(tt)));

        //   System.out.println(Arrays.toString(getNextMiorror(t)));

    }

    /**
     * 获得字符串的next函数值
     *
     * @param t 字符串
     * @return next函数值
     */
    public static int[] getNext(char[] t) {
        int[] next = new int[t.length+1];
        int i = 0;
        int j = -1;
        next[0] =-1;
        while (i < t.length) {
//            System.out.println("original:i:" + i + "   j:" + j);
            if (j == -1 || t[i] == t[j]) {
                i++;
                j++;
//                System.out.println("second i:" + i + "   j:" + j);
                /*if (t[i] != t[j]) {*/
//                    System.out.println("t[" + i + "]:" + t[i] + "   t[" + j + "]:" + t[j]);
                next[i] = j;
//                System.out.println("======next[" + i + "]:" + next[i] + "  j:" + j);
               /* } else {
                    next[i] = next[j];
                    System.out.println("-----next[" + i + "]:" + next[i] + "   next[" + j + "]:" + next[j]);
                }*/
            } else {
                //  System.out.println("j:"+j+"   next["+j+"]:"+next[j]);
                j = next[j];
//                System.out.println("single");
                // System.out.println("single next["+j+"]"+next[j]);

            }
//            System.out.println("----------" + i + "---------");
        }
        return next;
    }

    /**
     * KMP匹配字符串
     *
     * @param s 主串
     * @param t 模式串
     * @return 若匹配成功，返回下标，否则返回-1
     */
    public static int KMP_Index(char[] s, char[] t) {
        int[] next = getNext(t);
        System.out.println("next:"+Arrays.toString(next));
        System.out.println("-----------end next()------");
        int i = 0;
        int j = 0;
        while (i <= s.length - 1 && j <= t.length - 1) {
            System.out.println("original:i:" + i + "   j:" + j);
            if (j == -1 || s[i] == t[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        System.out.println("after while j:" + j + "   i:" + i);
        if (j < t.length) {
            System.out.println("return -1");
            return -1;
        } else
            return i - t.length; // 返回模式串在主串中的头下标
    }


    public static int[] getNextMiorror(String ps) {
        char[] strKey = ps.toCharArray();
        int[] next = new int[ps.toCharArray().length];

        // 初始条件
        int j = 0;
        int k = -1;
        next[0] = -1;

        // 根据已知的前j位推测第j+1位
        while (j < strKey.length - 1) {
            if (k == -1 || strKey[j] == strKey[k]) {
                // 如果str[j + 1] == str[k + 1]，回退后仍然失配，所以要继续回退
                if (strKey[j + 1] == strKey[k + 1]) {
                    next[++j] = next[++k];
                } else {
                    next[++j] = ++k;
                }
            } else {
                k = next[k];
            }
        }

        return next;
    }
}
