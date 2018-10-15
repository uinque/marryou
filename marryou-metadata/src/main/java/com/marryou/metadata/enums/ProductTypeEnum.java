package com.marryou.metadata.enums;

import javax.persistence.Column;

/**
 * Created by linhy on 2018/6/2.
 */
public enum ProductTypeEnum {

    FLYASH(0, "粉煤灰"),
    RAILWAY(1, "铁路运输");

    private Integer value;
    private String text;

    private ProductTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value){
        for(ProductTypeEnum c : ProductTypeEnum.values()){
            if(c.getValue()==value){
                return c.getText();
            }
        }
        return null;
    }

    public static ProductTypeEnum getEnum(Integer value){
        for(ProductTypeEnum c : ProductTypeEnum.values()){
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
