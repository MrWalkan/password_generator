package com.bigc.gui;

import java.util.ArrayList;

public class Test {
    public void test() {
        ArrayList<Character> sh = new ArrayList<>();
//        sh.add('L');
//        sh.add('U');
//        sh.add('S');
        sh.add('N');
        for(int i = 0 ; i < 10; i++) {
            Password p = new Password(20, sh, false);
            System.out.println(p.toString());
        }
        System.out.println(Password.history);
        
    }
    public static void main(String[] args) {
        Test ts = new Test();
        ts.test();
    }
}

