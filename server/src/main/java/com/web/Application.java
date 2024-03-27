package com.web;

import com.web.utils.MessageQueue;
import com.web.utils.NoticeUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(Application.class, args);
    }

    /**
     * 设置时区
     */
    @PostConstruct
    void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    @PostConstruct
    void init(){
        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Object message = MessageQueue.dequeue();
                    System.out.println("Consumer: Dequeue " + message);
                    //Thread.sleep(500); // 模拟耗时操作
                    if(message instanceof com.aliyun.dysmsapi20170525.models.SendSmsRequest){
                        NoticeUtil.sms((com.aliyun.dysmsapi20170525.models.SendSmsRequest)message);
                    }
                    if(message instanceof com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest){
                        NoticeUtil.phone((com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest)message);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        consumer.start();
    }
}
