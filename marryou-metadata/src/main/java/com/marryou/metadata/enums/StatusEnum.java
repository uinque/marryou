package com.marryou.metadata.enums;

/**
 * Created by linhy on 2018/6/2.
 */
public enum StatusEnum {

    EFFECTIVE(0, "有效"),
    INVALID(1, "失效");

    private Integer value;
    private String text;

    private StatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value){
        for(StatusEnum c : StatusEnum.values()){
            if(c.getValue()==value){
                return c.getText();
            }
        }
        return null;
    }

    public static StatusEnum getEnum(Integer value){
        for(StatusEnum c : StatusEnum.values()){
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
