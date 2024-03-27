package com.web.utils;

import com.web.model.po.DeviceData;
import com.web.service.DeviceService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataUtil {
    private static DeviceService deviceService;


    @PostConstruct
    public void init() {
        deviceService = BeanUtil.getBean(DeviceService.class);
    }

    public static void insert(DeviceData deviceData){
        deviceData.setId(IdWorker.getId());
        deviceService.insert(deviceData);
    }

    public static void waring(DeviceData deviceData){
        deviceData.setId(IdWorker.getId());
        deviceService.insert(deviceData);

        deviceService.waring(deviceData);
    }
}
