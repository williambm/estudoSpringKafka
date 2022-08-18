package com.wbm.springKafka.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {

    private String id;
    private String model;
    private String color;
}
