package com.marryou.metadata.persistence;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

/**
 * InExpression.java
 *
 * @auther linhy 2016/3/12
 */
public class InExpression implements SearchFilter {
    private String fileName;//名称
    private Collection<Object> value;//值

    public InExpression(String fileName, Collection<Object> value) {
        this.fileName = fileName;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path expression = null;
        if (fileName.contains(".")) {
            String[] names = StringUtils.split(fileName, ".");
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        } else {
            expression = root.get(fileName);
        }
        return expression.in(getValue());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Collection<Object> getValue() {
        return value;
    }

    public void setValue(Collection<Object> value) {
        this.value = value;
    }
}
