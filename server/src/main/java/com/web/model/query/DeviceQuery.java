package com.web.model.query;



import lombok.Getter;
import lombok.Setter;

import java.lang.Long;
import java.lang.String;
import java.lang.Byte;


@Getter
@Setter
public class DeviceQuery {


    
    private Long id;


    
    private Long userId;

    private String deviceId;
    
    private String phone;


    
    private String remark;


    
    private Boolean smsNotice;


    
    private Boolean phoneNotice;


}
