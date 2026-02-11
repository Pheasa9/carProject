package com.sa.leanning.CarProject.spe;

import java.util.Map;

import lombok.Data;

@Data
public class ModelFilter {

    private Long brandId;
    private String name;
    private int page = 0;
    private int size = 10;

    public ModelFilter(Map<String, String> params) {
        if (params.containsKey("brandId")) {
            this.brandId = Long.valueOf(params.get("brandId"));
        }
        if (params.containsKey("name")) {
            this.name = params.get("name");
        }
        if (params.containsKey("page")) {
            this.page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")) {
            this.size = Integer.parseInt(params.get("size"));
        }
    }

    // getters
}
