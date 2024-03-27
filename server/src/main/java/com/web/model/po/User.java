package com.web.model.po;

import jakarta.annotation.Generated;

import java.util.Date;

public class User {
    
    private Long id;

    
    private String username;

    
    private String password;

    
    private String type;

    
    private String phone;

    
    private Byte lock;

    
    private Byte smsNotice;

    
    private Byte phoneNotice;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    
    public Byte getLock() {
        return lock;
    }

    
    public void setLock(Byte lock) {
        this.lock = lock;
    }

    
    public Byte getSmsNotice() {
        return smsNotice;
    }

    
    public void setSmsNotice(Byte smsNotice) {
        this.smsNotice = smsNotice;
    }

    
    public Byte getPhoneNotice() {
        return phoneNotice;
    }

    
    public void setPhoneNotice(Byte phoneNotice) {
        this.phoneNotice = phoneNotice;
    }
}