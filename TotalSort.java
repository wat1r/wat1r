package com.sort;

import java.util.Arrays;

public class TotalSort {


    public static void main(String[] args) {
//        int[] numbers = {4, 2, -6, 9, 1};// 角标0-4,length=5;

//        selectSort(numbers);

        int numbers[] = {38, 65, 97, 76, 13, 27, 49};
        insertSort(numbers);

    }

    /**
     int numbers[] = {38, 65, 97, 76, 13, 27, 49};
     i:1 temp:65 numbers[0]:38
     **********
     i:2 temp:97 numbers[1]:65
     **********
     i:3 temp:76 numbers[2]:97
     ----j:3 numbers[2]:97
     **********
     i:4 temp:13 numbers[3]:97
     ----j:4 numbers[3]:97
     ----j:3 numbers[2]:76
     ----j:2 numbers[1]:65
     ----j:1 numbers[0]:38
     **********
     i:5 temp:27 numbers[4]:97
     ----j:5 numbers[4]:97
     ----j:4 numbers[3]:76
     ----j:3 numbers[2]:65
     ----j:2 numbers[1]:38
     **********
     i:6 temp:49 numbers[5]:97
     ----j:6 numbers[5]:97
     ----j:5 numbers[4]:76
     ----j:4 numbers[3]:65
     **********
     insertSort后：[13, 27, 38, 49, 65, 76, 97]
     * @param numbers
     */
    public static void insertSort(int[] numbers) {
        int size = numbers.length, temp, j;

        for (int i = 1; i < size; i++) {
            j = i;
            temp = numbers[i];
            System.out.println("i:" + i + " temp:" + temp + " numbers[" + (j - 1) + "]:" + +numbers[j - 1]);
            while (j > 0 && temp < numbers[j - 1]) {
                System.out.println("----j:" + j + " numbers[" + (j - 1) + "]:" + numbers[j - 1]);
                numbers[j] = numbers[j - 1];
                j--;
            }
//            System.out.println("j:" + j + " numbers[" + (j) + "]:" + numbers[j] + " temp:" + temp);
            System.out.println("**********");
            numbers[j] = temp;
        }

        System.out.println("insertSort后：" + Arrays.toString(numbers));
    }


    /**
     * @param numbers int[] numbers = {4, 2, -6, 9, 1};// 角标0-4,length=5;
     *                i:0 j:1 numbers[0]:4 numbers[1]:2
     *                i:0 j:2 numbers[0]:2 numbers[2]:-6
     *                i:0 j:3 numbers[0]:-6 numbers[3]:9
     *                i:0 j:4 numbers[0]:-6 numbers[4]:1
     *                i:1 j:2 numbers[1]:4 numbers[2]:2
     *                i:1 j:3 numbers[1]:2 numbers[3]:9
     *                i:1 j:4 numbers[1]:2 numbers[4]:1
     *                i:2 j:3 numbers[2]:4 numbers[3]:9
     *                i:2 j:4 numbers[2]:4 numbers[4]:2
     *                i:3 j:4 numbers[3]:9 numbers[4]:4
     *                排序后：[-6, 1, 2, 4, 9]
     */
    public static void selectSort(int[] numbers) {
        int size = numbers.length;

        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                System.out.println("i:" + i + " j:" + j + " numbers[" + i + "]:" + numbers[i] + " numbers[" + j + "]:" + numbers[j]);
                if (numbers[i] > numbers[j]) {
                    swap(numbers, i, j); // 替换
                }
            }
        }
        System.out.println("排序后：" + Arrays.toString(numbers));


    }

    private static void swap(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;

    }


    /**
     * 快排
     *
     * @param numbers
     * @param start
     * @param end
     */
    public static void quickSort(int[] numbers, int start, int end) {


        if (start < end) {

            int base = numbers[start];
            int temp;
            int i = start, j = end;

            do {

                while (numbers[i] < base && (i < end)) {
                    i++;
                }
                while (numbers[j] > base && (j > start)) {
                    j--;
                }

                if (i <= j) {
                    swap(numbers, i, j);
                    i++;
                    j--;
                }


            } while (i <= j);
            if (start < j) {
                quickSort(numbers, start, j);
            }
            if (end > i) {
                quickSort(numbers, i, end);
            }

        }


    }


}
