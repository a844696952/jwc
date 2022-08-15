package com.yice.edu.cn.common.util.crypto;

public class SimpleCryptoKit {
    private static final char[] chars=new char[]{
            '0','O','S','n','1','f','w','i','r','7','e','4','P','G','s','Y','9','q','c','2','v','L','R','W','Z','U','p','V','u','l','D','5','B','g','A','I','C','j','x','H','T','y','6','N','o','3','z','F','8','Q','K','m','J','h','d','X','b','E','M','a','k','t'
    };
    public static String encrypt(String txt){
        char[] chars = txt.toCharArray();
        char[] result=new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i]=nextChar(chars[i],txt.length());
        }
        return new String(result);
    }
    public static String decrypt(String txt){
        char[] chars = txt.toCharArray();
        char[] result=new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i]=prevChar(chars[i],txt.length());
        }
        return new String(result);
    }

    private static char nextChar(char c,int step){
        for (int i = 0; i < chars.length; i++) {
            char e=chars[i];
            if(e==c){
                int index=i+step;
                return chars[index%chars.length];
            }
        }
        return c;
    }
    private static char prevChar(char c,int step){
        for (int i = 0; i < chars.length; i++) {
            char e=chars[i];
            if(e==c){
                int index=i-step;
                if(index<0){
                    return chars[chars.length - 1 - (Math.abs(index) % chars.length - 1)];
                }
                return chars[index%chars.length];
            }
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("9879804892546235867"));
        System.out.println(decrypt("KKQ95EWNPPP9"));
       /* System.out.println(nextChar('m',10));
        System.out.println(prevChar('t',10));*/
    }
}
