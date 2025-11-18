package com.example.mockmvc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDTO {
    private Long id;
    private String storeNo;
    private String name;
    private String address;
    private String email;
    private String phone;
    private Integer status;
}
