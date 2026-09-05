package com.marryou.metadata.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility for the key=value;key=value feature format stored in the database.
 */
public final class FeatureUtils {

    public static final String STYLE_NUM = "styleNum";
    public static final String STYLE_TITLE_LEFT = "styleTitleLeft";
    public static final String STYLE_TITLE_RIGHT = "styleTitleRight";
    public static final String SEAL_NO = "sealNo";
    public static final String MONTHLY_BATCH = "monthlyBatch";
    public static final String ORDER_PHONE = "orderPhone";
    public static final String UNIT_NUM = "unitNum";
    public static final String STYLE_IMPLE_STANDARD = "styleImpleStandard";
    public static final String PRODUCT_BATCH = "productBatch";

    private FeatureUtils() {
    }

    public static Map<String, Object> parse(String feature) {
        Map<String, Object> result = new LinkedHashMap<>();
        if (StringUtils.isBlank(feature)) {
            return result;
        }
        for (String item : feature.split(";", -1)) {
            String segment = StringUtils.trim(item);
            int separator = segment.indexOf('=');
            if (separator <= 0) {
                continue;
            }
            String key = StringUtils.trim(segment.substring(0, separator));
            if (StringUtils.isBlank(key)) {
                continue;
            }
            result.put(key, segment.substring(separator + 1));
        }
        return result;
    }

    public static String serialize(Map<String, Object> feature) {
        if (feature == null || feature.isEmpty()) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Object> entry : feature.entrySet()) {
            if (StringUtils.isBlank(entry.getKey()) || entry.getValue() == null) {
                continue;
            }
            String key = entry.getKey().trim();
            String value = String.valueOf(entry.getValue());
            if (key.indexOf(';') >= 0 || key.indexOf('=') >= 0
                    || value.indexOf(';') >= 0) {
                throw new IllegalArgumentException("feature key cannot contain ';' or '='; value cannot contain ';'");
            }
            if (result.length() > 0) {
                result.append(';');
            }
            result.append(key).append('=').append(value);
        }
        return result.length() == 0 ? null : result.toString();
    }

    public static String getString(Map<String, Object> feature, String key) {
        if (feature == null) {
            return null;
        }
        Object value = feature.get(key);
        return value == null ? null : String.valueOf(value);
    }
}
