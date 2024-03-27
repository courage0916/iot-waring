package com.web.service;

import com.github.pagehelper.PageInfo;
import com.web.model.dto.DeviceDTO;
import com.web.model.po.DeviceData;
import com.web.model.query.DeviceQuery;
import com.web.model.vo.DeviceDataVO;
import com.web.model.vo.DeviceVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface DeviceService {

    void create(DeviceDTO data);

    void update(DeviceDTO data);

    void delete( Long id );

    DeviceVO get( Long id );

    List<DeviceVO> list(DeviceQuery query);

    int total(DeviceQuery query);

    PageInfo<DeviceVO> page(DeviceQuery query, int curr, int size, @PathVariable int navigatePages);

    List<DeviceDataVO> heartbeat(String id);

    void insert(DeviceData deviceData);

    void waring(DeviceData deviceData);
}
