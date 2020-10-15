package com.zhaoj.app;

import com.zhaoj.common.SendSmsUtil;
import com.zhaoj.dto.VideoDTO;
import com.zhaoj.entity.Video;
import com.zhaoj.service.VideoService;
import com.zhaoj.util.SecurityCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 作者:zhaoj
 * 创建时间:2020/10/10    17:53
 * 类的作用:
 */
@RestController
@RequestMapping("app")
public class AppAdminController {
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("getPhoneCode")
    public Map<String, Object> getPhoneCode(String phone) {
        //获取验证码随机数
        //1.获取验证码
        String code = SecurityCode.getSecurityCode();
        //将验证码随机数保存到session中|redis里
        /*
        HttpSession session = request.getSession();
        session.setAttribute("phoneCode",code);*/
        //发送手机验证码,以5分钟为限
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(phone,code,5, TimeUnit.MINUTES);
        System.out.println(code);
        //发送验证码到手机
//        String phoneCode = SendSmsUtil.sendPhoneCode(phone, code);
        return result(phone);
    }

    public Map<String, Object> result(Object obj) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", obj);
        result.put("message", "操作成功！~");
        result.put("status", 100);
        return result;
    }
}
