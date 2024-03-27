package com.web.model.dto;


import java.lang.Long;
import java.lang.String;
import java.lang.Byte;

import com.web.model.po.Device;
import org.springframework.beans.BeanUtils;

public class DeviceDTO {


    private Long id;


    
    private Long userId;

    private String deviceId;
    
    private String phone;

    private String phone2;
    private String phone3;
    private String remark;

    private String phone4;
    private String phone5;

    private String phone6;
    private String phone7;

    private String phone8;
    private String phone9;
    private String phone10;
    
    private Boolean smsNotice;


    
    private Boolean phoneNotice;


    public Device build() {
        Device device = new Device();
        BeanUtils.copyProperties(this, device);
        device.setSmsNotice((byte) (this.smsNotice ? 1 : 0));
        device.setPhoneNotice((byte) (this.phoneNotice ? 1 : 0));
        return device;
    }

    public String getPhone4() {
        return phone4;
    }

    public void setPhone4(String phone4) {
        this.phone4 = phone4;
    }

    public String getPhone5() {
        return phone5;
    }

    public void setPhone5(String phone5) {
        this.phone5 = phone5;
    }

    public String getPhone6() {
        return phone6;
    }

    public void setPhone6(String phone6) {
        this.phone6 = phone6;
    }

    public String getPhone7() {
        return phone7;
    }

    public void setPhone7(String phone7) {
        this.phone7 = phone7;
    }

    public String getPhone8() {
        return phone8;
    }

    public void setPhone8(String phone8) {
        this.phone8 = phone8;
    }

    public String getPhone9() {
        return phone9;
    }

    public void setPhone9(String phone9) {
        this.phone9 = phone9;
    }

    public String getPhone10() {
        return phone10;
    }

    public void setPhone10(String phone10) {
        this.phone10 = phone10;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }






    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Boolean getSmsNotice() {
        return smsNotice;
    }

    public void setSmsNotice(Boolean smsNotice) {
        this.smsNotice = smsNotice;
    }

    public Boolean getPhoneNotice() {
        return phoneNotice;
    }

    public void setPhoneNotice(Boolean phoneNotice) {
        this.phoneNotice = phoneNotice;
    }
}
