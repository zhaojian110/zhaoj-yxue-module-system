package com.zhaoj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.zhaoj.common.Result;
import com.zhaoj.common.SendSmsUtil;
import com.zhaoj.entity.Admin;
import com.zhaoj.service.AdminService;
import com.zhaoj.util.SecurityCode;
import com.zhaoj.util.SecurityImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AdminService service;
    /**
     * 注册用户
     * @param admin 注册用户的注册信息
     * @param code  验证码信息
     * @return
     */
    @RequestMapping("regist")
    public String regist(Admin admin,String code,HttpSession session){
        try{
            if (admin.getUsername()==null|admin.getUsername()=="") throw new RuntimeException("用户名不允许为空！");
            if (admin.getPassword()==null|admin.getPassword()=="") throw new RuntimeException("密码不允许为空！");
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.eq("username",admin.getUsername());
            Admin one = service.getOne(wrapper);
            if(one!=null) throw new RuntimeException("此用户名已存在，请重新编辑！");
            service.save(admin);
            return "login1";
        }catch (Exception e){
            session.removeAttribute("message");
            session.setAttribute("message",e.getMessage());
            return "regist1";
        }
    }

    /**
     * 登录
     * @param session
     * @param admin
     * @param code
     *  @param phone 手机
     *  @param phoneCode 手机验证码
     * @return
     */
    @RequestMapping("login")
    public String login(HttpSession session,Admin admin,String code,String phone,String phoneCode){
        try{
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.eq("username",admin.getUsername());
            Admin login = service.getOne(wrapper);
            if(login==null) throw new RuntimeException("登录失败,用户名输入错误,请重新输入用户名！");
            if(!login.getPassword().equals(admin.getPassword())) throw new RuntimeException("登录失败,密码输入错误,请重新输入！");
            String saveCode = (String) session.getAttribute("code");
            /*这里需要搞一个手机验证码登录*/
            if(!saveCode.equals(code)) throw  new RuntimeException("登录失败,验证码输入有误,请重新输入！");
            ValueOperations ops = redisTemplate.opsForValue();
            String phoneNum = (String) ops.get(phone);
            if(!phoneNum.equals(phoneCode))throw new RuntimeException("手机验证码输入有误,滚回去重新输入！");
            session.setAttribute("loginAdmin",login);
            return "back/main";
        }catch (Exception e){
            session.removeAttribute("loginMessage");
            session.setAttribute("loginMessage",e.getMessage());
            return "login1";
        }
    }

    /**
     * 安全退出
     * @return
     */
    @RequestMapping("exit")
    public String exit(HttpSession session){
        session.removeAttribute("loginAdmin");
        return "login1";
    }

    /**
     * 获取验证码信息
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("getCode")
    public String getCode(HttpSession session, HttpServletResponse response){
        //1.获取验证码
        String code = SecurityCode.getSecurityCode();
        //2.存储验证码
        session.setAttribute("code", code);
        //3.绘制验证码图片
        BufferedImage image = SecurityImage.createImage(code);
        //4.将图片返回页面
        try {
            ServletOutputStream o = response.getOutputStream();
            ImageIO.write(image, "png", o);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("sendPhoneCode")
    @ResponseBody
    public Result sendPhoneCode(String phone, HttpServletRequest request){
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
        //发送验证码到手机
        String phoneCode = SendSmsUtil.sendPhoneCode(phone, code);
        return Result.ok(phoneCode);
    }

}

