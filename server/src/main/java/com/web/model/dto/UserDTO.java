package com.web.model.dto;


import java.lang.Long;
import java.lang.String;

import com.web.model.po.User;

import org.springframework.beans.BeanUtils;

public class UserDTO {


    
    private Long id;


    
    private String username;


    
    private String password;


    
    private String type;


    
    private String phone;


    public User build() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
