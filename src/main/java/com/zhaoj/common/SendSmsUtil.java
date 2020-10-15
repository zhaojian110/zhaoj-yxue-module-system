package com.zhaoj.common;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.5.3</version>
</dependency>
*/
public class SendSmsUtil {
    public static String sendPhoneCode(String phone,String code) {
        // 需要提供你阿里云账号的accessKeyId / accessSecret
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI4G8cxfnSW8bsY8MwNf2o", "R5gRsiILVcBIkRUdNZ2lTbE6uC2JmD");
//        创建一个指定的用户客户端对象
        IAcsClient client = new DefaultAcsClient(profile);
        // 发送短信的请求对象
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");// 不要修改
        request.setSysVersion("2017-05-25");// 不要修改
        request.setSysAction("SendSms"); // 不要修改
        request.putQueryParameter("RegionId", "cn-hangzhou");// 不需要修改
        // 设置接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phone);//这里手机号可以填写别人的手机号phone
        // 设置使用哪个签名
        request.putQueryParameter("SignName", "我的六点空间");
        // 设置模板
        request.putQueryParameter("TemplateCode", "SMS_203187340");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        String responseDate = null;
        try {
            CommonResponse response = client.getCommonResponse(request);
            // 获取响应结果状态
            responseDate = response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return responseDate;
    }
}
