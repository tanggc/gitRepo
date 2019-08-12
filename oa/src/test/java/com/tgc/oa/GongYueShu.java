package com.tgc.oa;

/**
 * @author: Tgc
 * @date: 2019/8/12
 */
public class GongYueShu {
    public static void main(String[] args) {
        System.out.println(print(4,8));
    }

    public static String print(int a, int b){
        if( a == b){
            System.out.println("1");
        }

        for (int i = a; i >=2 ; i--) {
            if(a % i == 0 && b % i ==0){
                return a/i+" / "+ b/i;
            }
        }
        return a+" / "+b;
    }
}
