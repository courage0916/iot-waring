package com.web.times;

import com.alibaba.fastjson2.JSON;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.web.mapper.DeviceDataMapper;
import com.web.mapper.DeviceMapper;
import com.web.mapper.RemindMapper;
import com.web.model.po.Device;
import com.web.model.po.DeviceData;
import com.web.model.po.Remind;
import com.web.model.po.User;
import com.web.model.vo.UserVO;
import com.web.netty.EchoServerHandler;
import com.web.service.DeviceService;
import com.web.service.UserService;
import com.web.utils.MessageQueue;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.web.mapper.DeviceDataDynamicSqlSupport.deviceId;
import static com.web.mapper.DeviceDataDynamicSqlSupport.id;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Component
@Slf4j
public class Timing {


    @Resource
    RemindMapper remindMapper;


    @Resource
    UserService userService;

    @Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void tips(){
        List<String> list = remindMapper.select(data -> data.configureStatement(c -> c.setNonRenderingWhereClauseAllowed(true))).stream().distinct().map(Remind::getTime).toList();
        Date date = new Date();
        if(list.contains(String.format("%02d", date.getHours())+":"+String.format("%02d", date.getMinutes())+":"+String.format("%02d", date.getSeconds()))){
            log.info(date.getHours()+":"+date.getMinutes()+":"+date.getSeconds() + "发送日常通知开始");
            UserVO user = userService.queryByName("admin");


            String serverStatus1 = getSockStatus() ? "正常" : "异常";

            String serverStatus2 = new Date().getTime() < EchoServerHandler.atomicLong.get() + 60 * 5 * 1000 ? "有" : "没有";

            if(user.getSmsNotice()){
                com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(user.getPhone())
                        .setSignName("xxxx")
                        .setTemplateCode("SMS_4653xxxx")
                        .setTemplateParam(String.format("{\"serverStatus1\":\"%S\",\"serverStatus2\":\"%s\"}",serverStatus1,serverStatus2));

                MessageQueue.enqueue(sendSmsRequest);
            }

            if(user.getPhoneNotice()){
                com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest = new com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest()
                        .setCalledShowNumber("059233xxxxx")
                        .setCalledNumber(user.getPhone())
                        .setTtsCode("TTS_2871xxxx")
                        .setPlayTimes(3)
                        .setTtsParam(String.format("{\"serverStatus1\":\"%S\",\"serverStatus2\":\"%s\"}",serverStatus1,serverStatus2));

                MessageQueue.enqueue(singleCallByTtsRequest);
            }
            log.info(date.getHours()+":"+date.getMinutes()+":"+date.getSeconds() + "发送日常通知结束");

        };
    }

    public boolean getSockStatus() {
        try(Socket socket = new Socket("localhost", 4563);) {
            return true;
        }catch (IOException e) {
            return false;
        }
    }


}
