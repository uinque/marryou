package com.marryou.commons.utils.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by linhy on 2018/6/19.
 */
public class BUtils extends BeanUtils {

    public static String[] getNullPropertyNames (Object source,String... ignoreStrs) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }else{
                if(srcValue instanceof String){
                    if(StringUtils.isBlank((String)srcValue)){
                        emptyNames.add(pd.getName());
                    }
                }
            }
        }
        if(null!=ignoreStrs){
            for(String s : ignoreStrs){
                if(StringUtils.isNotBlank(s)){
                    emptyNames.add(s);
                }
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target,String... ignoreStrs){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src,ignoreStrs));
    }

}
