package com.lcsserver.dto;

import java.util.List;

public class LcsResponse {
    private List<ValueDto> lcs;

    public List<ValueDto> getLcs() {
        return lcs;
    }

    public LcsResponse setLcs(List<ValueDto> lcs) {
        this.lcs = lcs;
        return this;
    }

    @Override
    public String toString() {
        return "LcsResponse{" +
                "lcs=" + lcs +
                '}';
    }
}
