package com.web.model.vo;



import lombok.Getter;
import lombok.Setter;

import java.lang.Long;
import java.lang.String;
import java.util.List;

@Getter
@Setter
public class UserVO {


    private Long id;


    private String username;

    
    private String password;

    
    private String type;

    
    private String phone;
    private Boolean smsNotice;
    private Boolean phoneNotice;


}
