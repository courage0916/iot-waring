package com.web.model.po;

import jakarta.annotation.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class DeviceData {
    
    private Long id;

    
    private String deviceId;

    
    private Date createTime;

    private String content;


}