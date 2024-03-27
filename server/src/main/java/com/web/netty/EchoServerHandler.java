package com.web.netty;


import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.web.model.po.DeviceData;
import com.web.service.DeviceService;
import com.web.utils.BeanUtil;
import com.web.utils.DataUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@ChannelHandler.Sharable
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


    public static AtomicLong atomicLong = new AtomicLong();

    //public static AtomicInteger atomicInteger = new AtomicInteger();

    private static String check = "";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){

        String message = (String) msg;

        log.info("Server received: " + message);
        if(message.indexOf("{")!=0){
            return;
        }

        if(StringUtils.countMatches(message, "{") > 1){
            message = message.substring(0,message.indexOf("}")+1);
            log.info("Server received2: " + message);
        }
        if(StringUtils.countMatches(message, "}") == 0){
            log.info("Server received3: " + message);
            return;
        }
        try {
            if(StringUtils.countMatches(message, "{")!=1&&StringUtils.countMatches(message, "}")!=1){
                log.info("Server received4: " + message);
                return;
            }
            JSONObject jsonObject = JSON.parseObject(message);

            if(jsonObject.containsKey("link") && "999".equals(jsonObject.get("link")+"") ){
                atomicLong.set(new Date().getTime());
            }
            if(jsonObject.containsKey("type") && "send".equals(jsonObject.get("type")+"") && jsonObject.containsKey("iccid") && jsonObject.containsKey("wrapno")){

                String sis = jsonObject.get("wrapno") + "";

                if(!sis.equals(check)){
                    check = sis;
                    DeviceData deviceData = new DeviceData();
                    deviceData.setDeviceId(jsonObject.get("iccid")+"");
                    deviceData.setContent(message);
                    DataUtil.waring(deviceData);


                }
                ctx.write(Unpooled.copiedBuffer(message.replace("send","receive"), StandardCharsets.UTF_8));

            }
        }catch (Exception e){
            log.error("Server received6: " + message);
            log.error(e.getMessage(), "json转换错误");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.info(cause.getMessage());
        ctx.close();
    }



}