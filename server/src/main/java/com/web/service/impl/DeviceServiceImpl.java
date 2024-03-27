package com.web.service.impl;

import com.alibaba.fastjson2.JSON;
import com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.mapper.DeviceDataDynamicSqlSupport;
import com.web.mapper.DeviceDataMapper;
import com.web.mapper.DeviceMapper;
import com.web.model.dto.DeviceDTO;
import com.web.model.po.Device;
import com.web.model.po.DeviceData;
import com.web.model.query.DeviceQuery;
import com.web.model.vo.DeviceDataVO;
import com.web.model.vo.DeviceVO;
import com.web.netty.Waring;
import com.web.service.DeviceService;
import com.web.utils.IdWorker;
import com.web.utils.MessageQueue;
import com.web.utils.UserUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;

import static com.web.mapper.DeviceDynamicSqlSupport.*;
import static com.web.mapper.DeviceDynamicSqlSupport.id;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;


@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {


    @Resource
    DeviceMapper deviceMapper;


    @Resource
    DeviceDataMapper deviceDataMapper;

    @Override
    public void create(DeviceDTO data) {
        check(data);
        data.setId(IdWorker.getId());
        data.setUserId(UserUtil.getCurrUser().getId());

        Device device = data.build();

        //device.setStatus("待激活");
        Assert.isTrue(deviceMapper.insertSelective(device) == 1, "新增失败");
    }

    @Override
    public void update(DeviceDTO data) {
        check(data);
        Device device = data.build();
        Assert.isTrue(deviceMapper.updateByPrimaryKeySelective(device) == 1, "修改失败");
    }

    public void check(DeviceDTO data){

        List<DeviceVO> list = deviceMapper.select(data_ -> data_.
                where(deviceId, isEqualTo(data.getDeviceId()))
        ).stream().map(device -> {
            DeviceVO deviceVO = new DeviceVO();
            BeanUtils.copyProperties(device, deviceVO);
            deviceVO.setSmsNotice(device.getSmsNotice()==1);
            deviceVO.setPhoneNotice(device.getPhoneNotice()==1);
            return deviceVO;
        }).toList();

        Assert.isTrue(list.isEmpty() || (Objects.equals(data.getId(), list.get(0).getId())),"卡号重复了");
    }



    @Override
    public void delete( Long id ) {
        get( id );

        Assert.isTrue(deviceMapper.deleteByPrimaryKey(id) == 1, "删除失败");
    }

    @Override
    public DeviceVO get( Long id ) {
        DeviceQuery device = new DeviceQuery();
        device.setId(id);
        device.setUserId(UserUtil.getCurrUser().getId());
        List<DeviceVO> list = list(device);
        Assert.isTrue( list.size() == 1 , "数据有误："+id);
        return list.get(0);
    }

    @Override
    public List<DeviceVO> list(DeviceQuery query) {

        return deviceMapper.select(data -> data.
                where(id, isEqualToWhenPresent(query.getId()), and(userId,isEqualToWhenPresent(query.getUserId())))
                        .and(phone,isLikeWhenPresent(StringUtils.isEmpty(query.getPhone())?null:"%"+query.getPhone()+"%"),or(deviceId,isLikeWhenPresent(StringUtils.isEmpty(query.getDeviceId())?null:"%"+query.getDeviceId()+"%")))
                .orderBy(id.descending())
                .configureStatement(c -> c.setNonRenderingWhereClauseAllowed(true))
        ).stream().map(device -> {
                    DeviceVO deviceVO = new DeviceVO();
                    BeanUtils.copyProperties(device, deviceVO);
                    deviceVO.setSmsNotice(device.getSmsNotice()==1);
                    deviceVO.setPhoneNotice(device.getPhoneNotice()==1);
                    return deviceVO;
                }).collect(Collectors.toList());
    }

    @Override
    public int total(DeviceQuery query) {
        return (int) deviceMapper.count(data ->
                data.    where(id, isEqualToWhenPresent(query.getId()), and(userId,isEqualToWhenPresent(query.getUserId())))
                        .and(phone,isLikeWhenPresent(StringUtils.isEmpty(query.getPhone())?null:"%"+query.getPhone()+"%"),or(deviceId,isLikeWhenPresent(StringUtils.isEmpty(query.getDeviceId())?null:"%"+query.getDeviceId()+"%")))
                        .configureStatement(c -> c.setNonRenderingWhereClauseAllowed(true)));
    }

    @Override
    public PageInfo<DeviceVO> page(DeviceQuery query, int curr, int size, @PathVariable int navigatePages) {
        if(!UserUtil.getCurrUser().getUsername().equals("admin")){
            query.setUserId(UserUtil.getCurrUser().getId());
        }

        PageHelper.startPage(curr, size,false);
        List<DeviceVO> list = list(query);
        PageInfo<DeviceVO> pageInfo = new PageInfo<>(list, navigatePages);
        pageInfo.setTotal(total(query));
        return pageInfo;
    }

    @Override
    public List<DeviceDataVO> heartbeat(String id_) {
        return deviceDataMapper.select(data -> data.
                where(DeviceDataDynamicSqlSupport.deviceId, isEqualTo(id_))
                .orderBy(DeviceDataDynamicSqlSupport.id.descending())
                .limit(3)
        ).stream().map(item->{
            DeviceDataVO deviceDataVO = new DeviceDataVO();
            BeanUtils.copyProperties(item,deviceDataVO);
            long l1 = item.getCreateTime().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date d1 = new Date(l1);
            deviceDataVO.setCreateTime(simpleDateFormat.format(d1));
            return deviceDataVO;
        }).collect(Collectors.toList());
    }

    @Override
    public void insert(DeviceData deviceData) {
        deviceDataMapper.insertSelective(deviceData);
    }

    @Override
    public void waring(DeviceData deviceData) {
        log.info("告警参数："+JSON.toJSONString(deviceData));

        Device device1 = deviceMapper.selectOne(data -> data.
                where(deviceId, isEqualTo(deviceData.getDeviceId()))).orElse(null);


        if(device1 == null){
            return;
        }

        String typeStr = "";
        if(JSON.parseObject(deviceData.getContent()).containsKey("alarmtype") ){
            if("1".equals(JSON.parseObject(deviceData.getContent()).get("alarmtype")+"")){
                typeStr = "烟感报警";
            }else if("2".equals(JSON.parseObject(deviceData.getContent()).get("alarmtype")+"")){
                typeStr = "火焰探测";
            }else if("3".equals(JSON.parseObject(deviceData.getContent()).get("alarmtype")+"")){
                typeStr = "火灾抑制";
            }
        }

        if(device1.getSmsNotice() == 1){
            if(!StringUtils.isEmpty(device1.getPhone())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone())
                        .setSignName("xxxxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone2())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone2())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone3())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone3())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }


            if(!StringUtils.isEmpty(device1.getPhone4())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone4())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone5())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone5())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone6())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone6())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone7())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone7())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone8())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone8())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone9())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone9())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone10())){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(device1.getPhone10())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_xxxx")
                        .setTemplateParam(String.format("{\"deviceId\":\"%S\",\"remark\":\"%s\",\"type\":\"%s\"}",deviceData.getDeviceId(),device1.getRemark(), typeStr));

                MessageQueue.enqueue(sendSmsRequest);
            }
        }

        if(device1.getPhoneNotice() == 1){
            if(!StringUtils.isEmpty(device1.getPhone())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone2())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone2())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone3())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone3())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }

            if(!StringUtils.isEmpty(device1.getPhone4())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone4())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone5())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone5())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone6())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone6())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone7())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone7())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone8())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone8())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone9())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone9())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            if(!StringUtils.isEmpty(device1.getPhone10())) {
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(device1.getPhone10())
                        .setTtsCode("TTS_295xxxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"content\":\"%S\",\"type\":\"%s\"}", "卡号为：" + deviceData.getDeviceId() + "的设备，在" + device1.getRemark(), typeStr));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
        }

        //device1.setStatus("已发送告警通知");
        //deviceMapper.updateByPrimaryKeySelective(device1);
    }


}
