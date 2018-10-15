package com.marryou.metadata.enums;

/**
 * Created by linhy on 2018/6/2.
 */
public enum LevelEnum {

    ONE(0, "一级"),
    TWO(1, "二级"),
    THREE(2, "三级");

    private Integer value;
    private String text;

    private LevelEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value){
        for(LevelEnum c : LevelEnum.values()){
            if(c.getValue()==value){
                return c.getText();
            }
        }
        return null;
    }

    public static LevelEnum getEnum(Integer value){
        for(LevelEnum c : LevelEnum.values()){
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
