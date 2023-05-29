package com.liuxz.request.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class WikiParamKeyValue implements Serializable {
    private String key;
    private Object value;
    private String type;

    public WikiParamKeyValue(String key, Object value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

}
