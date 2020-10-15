package com.zhaoj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaoj.common.Result;
import com.zhaoj.entity.Feedback;
import com.zhaoj.entity.User;
import com.zhaoj.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService service;

    @RequestMapping("queryAll")
    @ResponseBody
    public Result queryAll(Integer page,
                           Integer rows,
                           String sidx , String sord,
                           String _search,
                           String searchOper,//表示发送的方式使用的是什么 = ！= 包含 不包含
                           String searchField,//表示需要查找的字段是哪个？ name? id?
                           String searchString){
        Result result = new Result();
        if(_search.equals("true")){
            QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchField, searchString);
            //创建了页码 和条数
            IPage<Feedback> iPage = new Page<>(page,rows);
            //通过页码条数搜索
            IPage<Feedback> pages = service.page(iPage, queryWrapper);
            //获取结果
            List<Feedback> list = pages.getRecords();
            //获取总页数
            Long total=pages.getTotal();
            Long pages1 = pages.getPages();
//        System.out.println(userList);
            //查询总数-result
            Integer re = service.count(queryWrapper);
//        System.out.println("总记录数："+re);
            result.setRows(list);
            result.setPage(page);// 当前页
            result.setTotal(pages1.intValue());// 总页数
            result.setRecords(re);// 信息总条数
        }
        if(_search.equals("false")){
            QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
            //创建了页码 和条数
            IPage<Feedback> iPage = new Page<>(page,rows);
            //通过页码条数搜索
            IPage<Feedback> pages = service.page(iPage, queryWrapper);
            //获取结果
            List<Feedback> list = service.queryAll(page, rows);
            //获取总页数
            Long pages1 = pages.getPages();
            //查询总数-result
            Integer re = service.count(queryWrapper);
//        System.out.println("总记录数："+re);
            result.setRows(list);
            result.setPage(page);// 当前页
            result.setTotal(pages1.intValue());// 总页数
            result.setRecords(re);// 信息总条数
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/edit")
    public Result<?> edit(String oper,Feedback feedback){
        Result result = new Result<>();
        if("add".equals(oper)){
            feedback.setId(null);
            feedback.setCreateAt(new Date());
            service.save(feedback);
            result.setMessage(feedback.getId());
        }
        if("edit".equals(oper)){
            QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
            QueryWrapper<Feedback> id = wrapper.eq("id", feedback.getId());
            service.update(feedback,id);
            result.setMessage(feedback.getId());
        }
        if("del".equals(oper)){
            if(feedback.getId().contains(",")){
                String[] split = feedback.getId().split(",");
                for (String s : split) {
                    service.removeById(s);
                }
            }else {
                service.removeById(feedback.getId());
            }
        }
        return result;
    }
}

