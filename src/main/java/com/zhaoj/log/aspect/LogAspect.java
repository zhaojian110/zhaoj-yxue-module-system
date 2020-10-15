package com.zhaoj.log.aspect;

import com.google.gson.Gson;
import com.zhaoj.entity.Category;
import com.zhaoj.entity.User;
import com.zhaoj.entity.Video;
import com.zhaoj.log.anno.YxueLog;
import com.zhaoj.log.entity.Log;
import com.zhaoj.log.service.LogService;
import com.zhaoj.service.CategoryService;
import com.zhaoj.service.UserService;
import com.zhaoj.service.VideoService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 作者:zhaoj
 * 创建时间:2020/9/28    17:48
 * 类的作用:
 */
@Configuration
@Aspect
public class LogAspect {

    @Autowired
    private HttpSession session;

    @Autowired
    private LogService service;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoryService categoryService;


    @Around("@annotation(com.zhaoj.log.anno.YxueLog)")
    //around
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Log log = new Log();
        /**
         * 获取操作的用户名
         */
        String username = (String) session.getAttribute("loginAdmin");
        //获取操作时间
        Date currentDate = new Date();
        //操作表名和操作类型
        //通过对方法上的注解属性值解析得到
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法对象
        Method method = signature.getMethod();
        //获取方法上的注解然后解析
        YxueLog annotation = method.getAnnotation(YxueLog.class);
        //获取表名
        String tableName = annotation.tableName();
        //获取操作名称
        String oprate = annotation.value();
        String methodName = annotation.methodName();
        System.out.println("-------------------------↓删除分割线↓--------------------------------------");
        //执行原始方法,并传参数
        Object[] args = joinPoint.getArgs();
        if(oprate.contains("删除")){
            Object arg =  args[0];
            System.out.println("============================================");
            Category category = null;
            if(arg.getClass().equals(Category.class)){
                Category cate = (Category) args[0];
                category = categoryService.getById(cate.getId());
            }
            User user = null;
            if(arg.getClass().equals(User.class)){
                User us = (User) args[0];
                user = userService.getById(us.getId());
            }
            Video video = null;
            if(arg.getClass().equals(Video.class)){
                Video vi = (Video) args[0];
                video = videoService.getById(vi.getId());
            }
            System.out.println("============================================");
            System.out.println("这是一个用户"+user);
            Gson gson = new Gson();
            if(user!=null){
                String userGson = gson.toJson(user);
                System.out.println(userGson);
                log.setDataId(user.getId());
                log.setDataInfo(userGson);
            }
            System.out.println("============================================");
            System.out.println("这是一个视频"+video);
            if(video!=null){
                String videoGson = gson.toJson(video);
                System.out.println(videoGson);
                log.setDataId(video.getId());
                log.setDataInfo(videoGson);
            }
            System.out.println("============================================");
            System.out.println("这是一个类别"+category);
            if(category!=null){
                String categoryGson = gson.toJson(category);
                System.out.println(categoryGson);
                log.setDataId(category.getId());
                log.setDataInfo(categoryGson);
            }
            System.out.println("============================================");
        }
        //执行人
        log.setUsername(username);
        //创建时间
        log.setOperactionAt(currentDate);
        //修改的表名
        log.setTableName(tableName);
        //操作的名称
        log.setOperationMethod(oprate);
        //操作方法
        log.setMethodName(methodName);
        //打印一波完整的方法签名
        System.out.println("完整的签名："+signature.toString());
        System.out.println("===============执行日志记录操作===============");
        System.out.println("-------------------------↑删除分割线↑--------------------------------------");
        //执行后续操作
        System.out.println("==========================================================================");
        Object returnValue = joinPoint.proceed(args);
        //true
        System.out.println(returnValue);
        System.out.println("==========================================================================");
        System.out.println("=============================添加分割线====================================");
        if(oprate.contains("添加")){
            Object arg =  args[0];
            System.out.println("============================================");
            Category category = null;
            if(arg.getClass().equals(Category.class)){
                Category cate = (Category) args[0];
                category = categoryService.getById(cate.getId());
            }
            User user = null;
            if(arg.getClass().equals(User.class)){
                User us = (User) args[0];
                user = userService.getById(us.getId());
            }
            Video video = null;
            if(arg.getClass().equals(Video.class)){
                Video vi = (Video) args[0];
                video = videoService.getById(vi.getId());
            }
            System.out.println("============================================");
            System.out.println("这是一个用户"+user);
            Gson gson = new Gson();
            if(user!=null){
                String userGson = gson.toJson(user);
                System.out.println(userGson);
                log.setDataId(user.getId());
                log.setDataInfo(userGson);
            }
            System.out.println("============================================");
            System.out.println("这是一个视频"+video);
            if(video!=null){
                String videoGson = gson.toJson(video);
                System.out.println(videoGson);
                log.setDataId(video.getId());
                log.setDataInfo(videoGson);
            }
            System.out.println("============================================");
            System.out.println("这是一个类别"+category);
            if(category!=null){
                String categoryGson = gson.toJson(category);
                System.out.println(categoryGson);
                log.setDataId(category.getId());
                log.setDataInfo(categoryGson);
            }
            System.out.println("============================================");
        }
        System.out.println("=============================修改分割线====================================");

        if(oprate.contains("修改")){
            Object arg =  args[0];
            System.out.println("============================================");
            Category category = null;
            if(arg.getClass().equals(Category.class)){
                Category cate = (Category) args[0];
                category = categoryService.getById(cate.getId());
            }
            User user = null;
            if(arg.getClass().equals(User.class)){
                User us = (User) args[0];
                user = userService.getById(us.getId());
            }
            Video video = null;
            if(arg.getClass().equals(Video.class)){
                Video vi = (Video) args[0];
                video = videoService.getById(vi.getId());
            }
            System.out.println("============================================");
            System.out.println("这是一个用户"+user);
            Gson gson = new Gson();
            if(user!=null){
                String userGson = gson.toJson(user);
                System.out.println(userGson);
                log.setDataId(user.getId());
                log.setDataInfo(userGson);
            }
            System.out.println("============================================");
            System.out.println("这是一个视频"+video);
            if(video!=null){
                String videoGson = gson.toJson(video);
                System.out.println(videoGson);
                log.setDataId(video.getId());
                log.setDataInfo(videoGson);
            }
            System.out.println("============================================");
            System.out.println("这是一个类别"+category);
            if(category!=null){
                String categoryGson = gson.toJson(category);
                System.out.println(categoryGson);
                log.setDataId(category.getId());
                log.setDataInfo(categoryGson);
            }
            System.out.println("============================================");
        }
        System.out.println("=============================修改分割线====================================");
        System.out.println("===============执行日志记录操作===============");
        service.save(log);
        return returnValue;
    }
}
