package com.web.utils;

import com.alibaba.fastjson2.JSON;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaException;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class NoticeUtil {

    private static String key = "xxxx";
    private static String secret = "xxxx";

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }



    public static void sms(com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest) throws Exception {
        log.info("调用短信通知开始");
        log.info("参数："+ JSON.toJSONString(sendSmsRequest));
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        com.aliyun.dysmsapi20170525.Client client = createClient(key, secret);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();


        AtomicInteger integer = new AtomicInteger(1);
        while (integer.get() <= 3){

            try {
                // 复制代码运行请自行打印 API 的返回值
                client.sendSmsWithOptions(sendSmsRequest, runtime);
                //写到txt
                Date day=new Date();

                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log.info("调用短信通知结束：时间{}，手机号{}，标签{}，模版{}",sdf.format(day),sendSmsRequest.getPhoneNumbers(),
                        sendSmsRequest.getSignName(),sendSmsRequest.getTemplateCode());

                break;
            } catch (TeaException error) {
                // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
                // 错误 message
                log.error(error.getMessage());
                // 诊断地址
                log.error(error.getData().get("Recommend")+"");
                com.aliyun.teautil.Common.assertAsString(error.message);
                continue;
            } catch (Exception _error) {
                TeaException error = new TeaException(_error.getMessage(), _error);
                // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
                // 错误 message
                log.error(error.getMessage());
                // 诊断地址
                log.error(error.getData().get("Recommend")+"");
                com.aliyun.teautil.Common.assertAsString(error.message);
                continue;
            }
        }


    }



    public static com.aliyun.dyvmsapi20170525.Client createClient2(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dyvmsapi
        config.endpoint = "dyvmsapi.aliyuncs.com";
        return new com.aliyun.dyvmsapi20170525.Client(config);
    }

    public static void phone(com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest singleCallByTtsRequest) throws Exception{
        log.info("电话通知开始调用");
        log.info("参数："+ JSON.toJSONString(singleCallByTtsRequest));
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        com.aliyun.dyvmsapi20170525.Client client = createClient2(key, secret);

        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

        AtomicInteger integer = new AtomicInteger(1);
        while (integer.get() <= 3) {
            try {
                // 复制代码运行请自行打印 API 的返回值
                client.singleCallByTtsWithOptions(singleCallByTtsRequest, runtime);
                Date day=new Date();

                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log.info("调用电话通知结束：时间{}，手机号{}，模版{}",sdf.format(day),singleCallByTtsRequest.getCalledNumber(),
                        singleCallByTtsRequest.getTtsCode());
                break;
            } catch (TeaException error) {
                // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
                // 错误 message
                log.error(error.getMessage());
                // 诊断地址
                log.error(error.getData().get("Recommend") + "");
                com.aliyun.teautil.Common.assertAsString(error.message);
                continue;
            } catch (Exception _error) {
                TeaException error = new TeaException(_error.getMessage(), _error);
                // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
                // 错误 message
                log.error(error.getMessage());
                // 诊断地址
                log.error((String) error.getData().get("Recommend"));
                com.aliyun.teautil.Common.assertAsString(error.message);
                continue;
            }

        }
    }
}
