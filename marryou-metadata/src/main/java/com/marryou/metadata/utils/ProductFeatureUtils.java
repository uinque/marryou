package com.marryou.metadata.utils;

import com.marryou.metadata.dto.ProductDto;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Converts product template style fields between the flat DTO and feature storage.
 */
public final class ProductFeatureUtils {

    private ProductFeatureUtils() {
    }

    public static String build(ProductDto product) {
        Map<String, Object> feature = new LinkedHashMap<>();
        putIfNotBlank(feature, FeatureUtils.STYLE_NUM, product.getStyleNum());
        putIfNotBlank(feature, FeatureUtils.STYLE_TITLE_LEFT, product.getStyleTitleLeft());
        putIfNotBlank(feature, FeatureUtils.STYLE_TITLE_RIGHT, product.getStyleTitleRight());
        return FeatureUtils.serialize(feature);
    }

    public static void apply(String rawFeature, ProductDto product) {
        Map<String, Object> feature = FeatureUtils.parse(rawFeature);
        product.setStyleNum(FeatureUtils.getString(feature, FeatureUtils.STYLE_NUM));
        product.setStyleTitleLeft(FeatureUtils.getString(feature, FeatureUtils.STYLE_TITLE_LEFT));
        product.setStyleTitleRight(FeatureUtils.getString(feature, FeatureUtils.STYLE_TITLE_RIGHT));
    }

    private static void putIfNotBlank(Map<String, Object> feature, String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            feature.put(key, value);
        }
    }
}
