package com.marryou.metadata.enums;

/**
 * Created by linhy on 2018/6/2.
 */
public enum TechnoEnum {

    SORTING(0,"分选"),
    PROCESS_ONE(1,"加工灰(Ⅰ级)"),
    PROCESS_TWO(2,"加工灰(Ⅱ级)"),
    ORIGINAL(3,"原灰"),
    OTHER(4,"其它");

    private Integer value;
    private String text;

    private TechnoEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value){
        for(TechnoEnum c : TechnoEnum.values()){
            if(c.getValue()==value){
                return c.getText();
            }
        }
        return null;
    }

    public static TechnoEnum getEnum(Integer value){
        for(TechnoEnum c : TechnoEnum.values()){
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
