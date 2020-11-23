package com.johnny.cs;

import org.junit.jupiter.api.Test;

class Test1 {
    
    @Test
    void binaryString() {
        //given
        int n = 529;
        String s = Integer.toBinaryString(n);

        //when
        int count = 0;
        int length = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '0') count++;
            if (s.charAt(i) == '1') {
                length = Math.max(length, count);
                count = 0;
            }
        }

        //then
        System.out.println(s);
        System.out.println(length);
     }
}
