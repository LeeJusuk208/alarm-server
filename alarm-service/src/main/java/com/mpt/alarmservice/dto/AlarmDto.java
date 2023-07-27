package com.mpt.alarmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmDto {
    private String email;
    private String goodsId;
    private int targetPrice;

}
