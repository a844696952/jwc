package com.yice.edu.cn.common.pojo.ts.jpush;

public enum JpushApp {
    TAP(0,"6bf85ce9fc89d3387386ce32","cc5d3f603ec1e5cec6fb4e22"),
    ECC(1,"678a83be82a780246bcefde4","967ab875ce5e4b8344a45305"),
    BMP(2,"def8f14e68c7d81aaf000446","d1bb74a53f1351a4be112b52"),
    EWB(3,"9d2d0f64784ade15afa6db66","18426d6d9c965fb7c2f22a17"),
    ECC1(4,"da2bbf742f4fab8ff03b061e","d4e814084aa36103ffe48e1a");
    private final int id;
    private final String key;
    private final String secret;
    JpushApp(int id,String key, String secret) {
        this.id=id;
        this.key=key;
        this.secret=secret;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public static JpushApp getValueById(int id){
        final JpushApp[] values = JpushApp.values();
        for (JpushApp value : values) {
            if(value.id==id){
                return value;
            }
        }
        return null;
    }




}
