package com.marryou.metadata.utils;

import com.marryou.metadata.dto.DeliveryDto;
import com.marryou.metadata.dto.DeliveryInfoDto;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Converts delivery order flat fields between the API and feature storage.
 */
public final class DeliveryFeatureUtils {

    private DeliveryFeatureUtils() {
    }

    public static String buildOnCreate(DeliveryDto delivery, String productFeature) {
        Map<String, Object> feature = new LinkedHashMap<>();
        putIfNotBlank(feature, FeatureUtils.SEAL_NO, delivery.getSealNo());
        putIfNotBlank(feature, FeatureUtils.MONTHLY_BATCH, delivery.getMonthlyBatch());
        putIfNotBlank(feature, FeatureUtils.ORDER_PHONE, delivery.getOrderPhone());
        putIfNotBlank(feature, FeatureUtils.UNIT_NUM, delivery.getUnitNum());
        putIfNotBlank(feature, FeatureUtils.STYLE_IMPLE_STANDARD, delivery.getStyleImpleStandard());
        putIfNotBlank(feature, FeatureUtils.PRODUCT_BATCH, delivery.getProductBatch());

        Map<String, Object> product = FeatureUtils.parse(productFeature);
        copyIfPresent(product, feature, FeatureUtils.STYLE_NUM);
        copyIfPresent(product, feature, FeatureUtils.STYLE_TITLE_LEFT);
        copyIfPresent(product, feature, FeatureUtils.STYLE_TITLE_RIGHT);
        return FeatureUtils.serialize(feature);
    }

    public static String updateBusinessFields(String currentFeature, DeliveryDto delivery) {
        Map<String, Object> feature = FeatureUtils.parse(currentFeature);
        updateIfPresent(feature, FeatureUtils.SEAL_NO, delivery.getSealNo());
        updateIfPresent(feature, FeatureUtils.MONTHLY_BATCH, delivery.getMonthlyBatch());
        updateIfPresent(feature, FeatureUtils.ORDER_PHONE, delivery.getOrderPhone());
        updateIfPresent(feature, FeatureUtils.UNIT_NUM, delivery.getUnitNum());
        updateIfPresent(feature, FeatureUtils.STYLE_IMPLE_STANDARD, delivery.getStyleImpleStandard());
        updateIfPresent(feature, FeatureUtils.PRODUCT_BATCH, delivery.getProductBatch());
        return FeatureUtils.serialize(feature);
    }

    public static void applyToInfoDto(String rawFeature, DeliveryInfoDto delivery) {
        Map<String, Object> feature = FeatureUtils.parse(rawFeature);
        delivery.setSealNo(FeatureUtils.getString(feature, FeatureUtils.SEAL_NO));
        delivery.setMonthlyBatch(FeatureUtils.getString(feature, FeatureUtils.MONTHLY_BATCH));
        delivery.setOrderPhone(FeatureUtils.getString(feature, FeatureUtils.ORDER_PHONE));
        delivery.setUnitNum(FeatureUtils.getString(feature, FeatureUtils.UNIT_NUM));
        delivery.setStyleNum(FeatureUtils.getString(feature, FeatureUtils.STYLE_NUM));
        delivery.setStyleTitleLeft(FeatureUtils.getString(feature, FeatureUtils.STYLE_TITLE_LEFT));
        delivery.setStyleTitleRight(FeatureUtils.getString(feature, FeatureUtils.STYLE_TITLE_RIGHT));
        delivery.setStyleImpleStandard(FeatureUtils.getString(feature, FeatureUtils.STYLE_IMPLE_STANDARD));
        delivery.setProductBatch(FeatureUtils.getString(feature, FeatureUtils.PRODUCT_BATCH));
    }

    private static void copyIfPresent(Map<String, Object> source, Map<String, Object> target, String key) {
        String value = FeatureUtils.getString(source, key);
        if (value != null) {
            target.put(key, value);
        }
    }

    private static void putIfNotBlank(Map<String, Object> feature, String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            feature.put(key, value);
        }
    }

    private static void updateIfPresent(Map<String, Object> feature, String key, String value) {
        if (value == null) {
            return;
        }
        if (StringUtils.isEmpty(value)) {
            feature.remove(key);
        } else {
            feature.put(key, value);
        }
    }
}
