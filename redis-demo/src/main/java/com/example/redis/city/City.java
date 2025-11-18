package com.example.redis.city;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {
    private String name;
    private Double longitude;
    private Double latitude;
}
