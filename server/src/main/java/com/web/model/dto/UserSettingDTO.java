package com.web.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class UserSettingDTO {

    private String phone;
    private List<String> times;
    private Boolean smsNotice;
    private Boolean phoneNotice;
}
