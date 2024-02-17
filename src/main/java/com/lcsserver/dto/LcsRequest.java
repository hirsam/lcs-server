package com.lcsserver.dto;

import java.util.List;

public class LcsRequest {
    private List<ValueDto> values;

    public List<ValueDto> getValues() {
        return values;
    }

    public void setValues(List<ValueDto> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "LcsRequest{" +
                "values=" + values +
                '}';
    }
}
