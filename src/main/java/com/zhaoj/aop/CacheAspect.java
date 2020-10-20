package com.zhaoj.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * 作者:zhaoj
 * 创建时间:2020/10/13    17:40
 * 类的作用:
 */
@Aspect
@Configuration
public class CacheAspect {

    @Resource
    private RedisTemplate redisTemplate;

    //环绕通知,添加缓存
    @Around("@annotation(com.zhaoj.annotation.AddCache)")
//    @Around("execution(* com.zhaoj.service.impl.*.page(..))")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        //String String
        //String Hash
        //KEY(类名)  key(方法名+实参),value（数据）
        //序列化解决乱码
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        //设置key value
        //key=类名+方法名+实参
        StringBuilder sb = new StringBuilder();
        //获取类的全限定名   KEY
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);

        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }

        //获取拼接好的key   key
        String key = sb.toString();

        System.out.println("className="+className);
        System.out.println("key="+key);
        //判断是否有缓存
        HashOperations hash = redisTemplate.opsForHash();
        Boolean b = hash.hasKey(className, key);

        Object result =null;
        //判断key是否存在
        if(b){
            //取出结果返回数据
            result = hash.get(className, key);
        }else{
            //放行方法
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //加入缓存
            hash.put(className,key,result);
        }
        return result;
    }
    //清除缓存
    @After("@annotation(com.zhaoj.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){
        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        //com.baizhi.service.impl.UserServiceImpl
        redisTemplate.delete(className);
    }
}
