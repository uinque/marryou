package com.marryou.metadata.enums;

/**
 * Created by linhy on 2018/6/2.
 */
public enum LogTypeEnum {

    USER(0, "用户"),
    COMPANY(1, "公司"),
    PRODUCT(2, "产品"),
    DELIVERY(3, "出库单"),
    MANUFACTURE(4, "生厂商"),
    STANDARD(5, "标准"),
    TENANT(6,"租户");

    private Integer value;
    private String text;

    private LogTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value){
        for(LogTypeEnum c : LogTypeEnum.values()){
            if(c.getValue()==value){
                return c.getText();
            }
        }
        return null;
    }

    public static LogTypeEnum getEnum(Integer value){
        for(LogTypeEnum c : LogTypeEnum.values()){
            if(c.getValue()==value){
                return c;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
