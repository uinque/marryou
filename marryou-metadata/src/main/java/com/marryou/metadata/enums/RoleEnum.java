package com.marryou.metadata.enums;

/**
 * Created by linhy on 2018/6/2.
 */
public enum RoleEnum {

    SUPER_ADMIN(0, "超级管理员"),
    ADMIN(1, "管理员"),
    MEMBER(2, "成员");

    private Integer value;
    private String text;

    private RoleEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value){
        for(RoleEnum c : RoleEnum.values()){
            if(c.getValue()==value){
                return c.getText();
            }
        }
        return null;
    }

    public static RoleEnum getEnum(Integer value){
        for(RoleEnum c : RoleEnum.values()){
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
