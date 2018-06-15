package com.lly.test.algol;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {80, 30, 40, 50, 10, 20, 70};
        System.out.println(Arrays.toString(arr));

        quickSort(arr, 0, arr.length-1);

        System.out.println(Arrays.toString(arr));
    }


    private static void quickSort(int[] arr, int left, int right){
        if(left < right){
            int low = left;
            int high = right;
            int temp = arr[low];
            while (low < high){
                //1、 从最后寻找比参照数小的值
                // 1.1 先找比参照数小的数的下标
                while (low < high && arr[high] > temp){
                    high --;
                }
                // 1.2 判断小的数是不是在参照数的右侧，如果是，则将大的数移到参照数的位置
                if(low < high)
                    arr[low] = arr[high];
                // 2、再从左侧开始寻找比参照数小的数
                // 2.1 先找到下标
                while (low < high && arr[low] < temp)
                    low ++;
                // 2.2 判断下标是不是比高位下标小，如果小，则将找到的数移到高位上
                if(low < high)
                    arr[high--] = arr[low];
            }
            arr[low] = temp;
            //循环找大数
            quickSort(arr, left, high - 1);
            //循环找小数
            quickSort(arr, low + 1  , right);
        }
    }

}
