package com.web.controller;

import com.github.pagehelper.PageInfo;
import com.web.model.dto.DeviceDTO;
import com.web.model.po.DeviceData;
import com.web.model.query.DeviceQuery;
import com.web.model.vo.DeviceDataVO;
import com.web.model.vo.DeviceVO;
import com.web.service.DeviceService;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {


    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    
    @PostMapping
    public void create(@Valid @RequestBody DeviceDTO data) {
        deviceService.create(data);
    }


    
    @PutMapping
    public void update(@Valid @RequestBody DeviceDTO data) {
        deviceService.update(data);
    }


    
    @DeleteMapping("{id}")
    public void delete( @PathVariable Long id ) {
        deviceService.delete(id );
    }


    
    @GetMapping("{id}")
    public DeviceVO get( @PathVariable Long id ) {
        return deviceService.get(id );
    }


   
    @GetMapping
    public List<DeviceVO> list(@RequestBody DeviceQuery query) {
        return deviceService.list(query);
    }

    
    @GetMapping("/total")
    public int total(@RequestBody  DeviceQuery query) {
        return deviceService.total(query);
    }

    
    @PostMapping("{curr}/{size}/{navigatePages}")
    public PageInfo<DeviceVO> page(@RequestBody DeviceQuery query, @PathVariable int curr, @PathVariable int size, @PathVariable int navigatePages) {
        return deviceService.page(query, curr, size, navigatePages);
    }

    @PostMapping("/heartbeat/list")
    public List<DeviceDataVO> heartbeat(String deviceId) {
        return deviceService.heartbeat(deviceId);
    }
}
