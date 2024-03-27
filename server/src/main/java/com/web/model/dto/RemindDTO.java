package com.web.model.dto;


import java.lang.Integer;
import java.util.Date;
import com.web.model.po.Remind;

import org.springframework.beans.BeanUtils;

public class RemindDTO {


    
    private Integer id;


    
    private Date time;


    public Remind build() {
        Remind remind = new Remind();
        BeanUtils.copyProperties(this, remind);
        return remind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }



}
