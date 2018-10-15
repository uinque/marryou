package com.marryou.metadata.enums;

/**
 * Created by linhy on 2018/6/2.
 */
public enum OperateTypeEnum {

    CREATE(0, "新增"),
    DELETE(1, "删除"),
    UPDATE(2, "更新"),
    OTHER(3, "其它");

    private Integer value;
    private String text;

    private OperateTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value){
        for(OperateTypeEnum c : OperateTypeEnum.values()){
            if(c.getValue()==value){
                return c.getText();
            }
        }
        return null;
    }

    public static OperateTypeEnum getEnum(Integer value){
        for(OperateTypeEnum c : OperateTypeEnum.values()){
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
