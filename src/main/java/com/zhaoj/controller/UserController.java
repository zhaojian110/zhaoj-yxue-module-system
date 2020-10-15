package com.zhaoj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaoj.annotation.AddCache;
import com.zhaoj.common.Result;
import com.zhaoj.dao.UserDao;
import com.zhaoj.entity.User;
import com.zhaoj.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao dao;
    @Autowired
    private UserService service;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("querryAll")
    @ResponseBody
    @AddCache
    public Result querryAll(Integer page,
                            Integer rows,
                            String sidx , String sord,
                            String _search,
                            String searchOper,//表示发送的方式使用的是什么 = ！= 包含 不包含
                            String searchField,//表示需要查找的字段是哪个？ name? id?
                            String searchString){//模糊搜索的东西是什么
        Result result = new Result();
        if(_search.equals("true")){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchField, searchString);
            //创建了页码 和条数
            IPage<User> iPage = new Page<>(page,rows);
            //通过页码条数搜索
            IPage<User> pages = service.page(iPage, queryWrapper);
            //获取结果
            List<User> userList = pages.getRecords();
            //获取总页数
            Long total=pages.getTotal();
            Long pages1 = pages.getPages();
//        System.out.println(userList);
            //查询总数-result
            Integer re= dao.selectCount(queryWrapper);
//        System.out.println("总记录数："+re);
            result.setRows(userList);
            result.setPage(page);// 当前页
            result.setTotal(pages1.intValue());// 总页数
            result.setRecords(re);// 信息总条数
        }
        if(_search.equals("false")){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //创建了页码 和条数
            IPage<User> iPage = new Page<>(page,rows);
            //通过页码条数搜索
            IPage<User> pages = service.page(iPage, null);
            //获取结果
            List<User> userList = pages.getRecords();
            //获取总页数
            Long total=pages.getTotal();
            Long pages1 = pages.getPages();
//        System.out.println(userList);
            //查询总数-result
            Integer re= dao.selectCount(queryWrapper);
//        System.out.println("总记录数："+re);
            result.setRows(userList);
            result.setPage(page);// 当前页
            result.setTotal(pages1.intValue());// 总页数
            result.setRecords(re);// 信息总条数
        }
        return result;
    }
    @Autowired
    private EchartsController controller;
    @ResponseBody
    @RequestMapping("/edit")
    @AddCache
    public Result<?> edit(String oper,User user){
        Result result = new Result<>();
        if("add".equals(oper)){
            user.setId(null);
            /*
                user.setStatus("Y");
                user.setRegTime(new Date());
            */
            service.save(user);
            controller.queryUser();
            result.setMessage(user.getId());
        }
        if("edit".equals(oper)){
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            QueryWrapper<User> id = wrapper.eq("id", user.getId());
            service.update(user,id);
            result.setMessage(user.getId());
        }
        if("del".equals(oper)){
            if(user.getId().contains(",")){
                String[] split = user.getId().split(",");
                for (String s : split) {

                    service.removeById(s);
                    controller.queryUser();
                }
            }else {

                service.removeById(user.getId());
                controller.queryUser();
            }
        }
        return result;
    }


    @RequestMapping("upload")
    public void upload(String id, MultipartFile headShow) throws Exception {
        //进行文件上传
        //1.获取绝对路径
        //获取相对路径
//        String contextPath = request.getSession().getServletContext().getContextPath();
        String realPath = request.getSession().getServletContext().getRealPath("/upload_file/img");
        //2.文件上传 上传文件的文件名
        String Real =  headShow.getOriginalFilename();
        System.out.println("文件上传的文件名是"+Real);
        String filename = new Date().getTime() + "-" + headShow.getOriginalFilename();
        try {//将路径和文件传入文件
            /*System.out.println("我想添加到本地文件的地址是"+realPath);
            System.out.println("我想添加的本地文件是"+wenjianlujing);
            File file = new File(realPath+wenjianlujing);
            System.out.println(file.getPath());*/
            headShow.transferTo(new File("D:\\backdemotest\\backdemotest1\\zhaoj-yxue-module-system\\src\\main\\webapp\\upload_file\\img/"+filename));
        }catch (Exception e){
            e.printStackTrace();
        }
        User user = new User();
        user.setId(id);
        user.setHeadShow(filename);
        service.updateById(user);
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    public void updateStatus(String id){
        User byId = service.getById(id);
        System.out.println(id);
        if(byId.getStatus().equals("Y")){
            byId.setStatus("N");
        }else {
            byId.setStatus("Y");
        }
        service.updateById(byId);
    }
}

