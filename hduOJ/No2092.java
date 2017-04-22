package hduOJ;

import java.util.Scanner;

/**
 * Created by Administrator on 2017-04-15.
 */
public class No2092 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            if (m == 0 && n == 0) {
                System.exit(0);
            }
            double del = Math.sqrt(m * m - 4 * n);
            if ((int) del == del && (-m + del) % 2 == 0) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }



        }


    }
}
