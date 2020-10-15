package com.zhaoj.controller;

import com.alibaba.fastjson.JSON;
import com.qiniu.util.Json;
import com.zhaoj.dao.UserDao;
import com.zhaoj.entity.User;
import com.zhaoj.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 作者:zhaoj
 * 创建时间:2020/10/12    17:46
 * 类的作用:
 */
@RestController
@RequestMapping("echarts")
public class EchartsController {
    @Autowired
    private UserDao userService;

    /**
     * 查询用户注册时间
     */
    @RequestMapping("queryUserRegist")
    public HashMap<String, ArrayList> queryUserRegist(){
        HashMap<String, ArrayList> map = new HashMap<>();
        //注册时间
        List<User> users = userService.queryByRegist();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        for (User user : users) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            String createMonth = sdf.format(user.getRegTime());
            list.add(createMonth+"月");
            counts.add(user.getSum());
        }
        map.put("month",list);
        map.put("time",counts);
           /* SimpleDateFormat sdf = new SimpleDateFormat("MM");
            String createdate = sdf.format(user.getRegTime());*/
           //根据用户状态
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> statusCounts = new ArrayList<>();
        List<User> userss = userService.queryByStatus();
        for (User user : userss) {
            if(user.getStatus()=="Y"){
                String sta = new String();
                sta="正常";
                status.add(sta);
                statusCounts.add(user.getSum());
            }else {
                String sta = new String();
                sta="冻结";
                status.add(sta);
                statusCounts.add(user.getSum());
            }
        }
        map.put("status",statusCounts);
        return map;
    }

    /**
     * 查询用户注册时间
     */
    @RequestMapping("queryUser")
    public void queryUser(){
        HashMap<String, ArrayList> map = new HashMap<>();
        //注册时间
        System.out.println("我进行了更细系统操作");
        List<User> users = userService.queryByRegist();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        for (User user : users) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            String createMonth = sdf.format(user.getRegTime());
            list.add(createMonth+"月");
            counts.add(user.getSum());
        }
        map.put("month",list);
        map.put("time",counts);
           /* SimpleDateFormat sdf = new SimpleDateFormat("MM");
            String createdate = sdf.format(user.getRegTime());*/
        //根据用户状态
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> statusCounts = new ArrayList<>();
        List<User> userss = userService.queryByStatus();
        for (User user : userss) {
            if(user.getStatus()=="Y"){
                String sta = new String();
                sta="正常";
                status.add(sta);
                statusCounts.add(user.getSum());
            }else {
                String sta = new String();
                sta="冻结";
                status.add(sta);
                statusCounts.add(user.getSum());
            }
        }
        map.put("status",statusCounts);
        //初始化goEasy 参数：地区地址,appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-802a1ac5b8124929b19dfed4d54c8650");
        //发送数据
        String maps = JSON.toJSONString(map);
        System.out.println(maps);
        goEasy.publish("11111", maps);
    }
}
