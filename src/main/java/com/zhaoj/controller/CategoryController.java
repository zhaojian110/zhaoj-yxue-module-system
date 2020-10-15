package com.zhaoj.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaoj.common.Result;
import com.zhaoj.dao.CategoryDao;
import com.zhaoj.entity.Category;
import com.zhaoj.entity.Category1;
import com.zhaoj.entity.User;
import com.zhaoj.entity.Video;
import com.zhaoj.log.entity.Log;
import com.zhaoj.service.CategoryService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;
    @Autowired
    private CategoryDao dao;

    @Autowired
    private HttpServletResponse response;
    /**
     * 报表导出
     * @throws Exception
     */
    //这部分二级类别有瑕疵
    @RequestMapping("export")
    @ResponseBody
    public void export()throws Exception{
        //获取所有一级类别
        QueryWrapper<Category> listOne = new QueryWrapper<>();
        listOne.eq("level","1");
        List<Category> list1 = service.list(listOne);
        for (Category category : list1) {
            QueryWrapper<Category> listTwo = new QueryWrapper<>();
            listTwo.eq("level","2");
            List<Category> list2 = service.list(listTwo);
            List<Category1> list3 = null;
            for (Category category1 : list2) {
                if(category1.getId().equals(category.getPid())){
                    Category1 cate1 = new Category1();
                    cate1.setId(category1.getId());
                    cate1.setName(category1.getName());
                    cate1.setLevel(category1.getLevel());
                    cate1.setPid(category1.getPid());
                    list3.add(cate1);
                }
            }
            category.setCategoryList(list3);
        }
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams("类别信息","起飞", "类别信息表"), Category.class, list1);
        /**
         * 设置下载的响应头
         */
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-type","application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=category.xls");
        OutputStream outputStream = response.getOutputStream();
        sheets.write(outputStream);
    }
    @ResponseBody
    @RequestMapping("queryAllOne")
    public Result queryAllOne(
            Integer page, //页码
            Integer rows, //条数
            String sidx , String sord,
            String _search,
            String searchOper,//表示发送的方式使用的是什么 = ！= 包含 不包含
            String searchField,//表示需要查找的字段是哪个？ name? id?
            String searchString //模糊搜索的东西是什么
    ){
        //new 一个结果集
        Result result = new Result();
        if(_search.equals("true")){
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            //创建需要的页码和条数
            IPage<Category> iPage = new Page<>(page,rows);
            queryWrapper.eq("level","1");
            queryWrapper.like(searchField,searchString);
            //查询的东西是
            IPage<Category> list = service.page(iPage, queryWrapper);
            //获取结果
            List<Category> categoryList = list.getRecords();
            //获取页码
            Long pages1 = list.getPages();
            //获取总条数
            Integer count= dao.selectCount(queryWrapper);
            //返回前端的条数
            result.setRows(categoryList);
            result.setPage(page);// 当前页
            result.setTotal(pages1.intValue());// 总页数
            result.setRecords(count);// 信息总条数
        }else {
            //查询的结果
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            //创建需要的页码和条数
            IPage<Category> iPage = new Page<>(page,rows);
            queryWrapper.eq("level","1");
            //查询的东西是
            IPage<Category> list = service.page(iPage, queryWrapper);
            //获取结果
            List<Category> categoryList = list.getRecords();
            //获取页码
            Long pages1 = list.getPages();
            //获取总条数
            Integer count= dao.selectCount(queryWrapper);
            //返回前端的条数
            result.setRows(categoryList);
            result.setPage(page);// 当前页
            result.setTotal(pages1.intValue());// 总页数
            result.setRecords(count);// 信息总条数
        }


        return result;
    }

    @ResponseBody
    @RequestMapping("queryAllTwo")
    public Result queryAllTwo(
            Integer page, //页码
            Integer rows, //条数
            String sidx , String sord,
            String _search,
            String searchOper,//表示发送的方式使用的是什么 = ！= 包含 不包含
            String searchField,//表示需要查找的字段是哪个？ name? id?
            String searchString, //模糊搜索的东西是什么
            String pid//传递 点击的哪个一级类别
    ){
        //new 一个结果集
        Result result = new Result();
        //查询的结果
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        //创建需要的页码和条数
        IPage<Category> iPage = new Page<>(page,rows);
        queryWrapper.eq("level","2");
        queryWrapper.eq("pid",pid);
        //查询的东西是
        IPage<Category> list = service.page(iPage, queryWrapper);
        //获取结果
        List<Category> categoryList = list.getRecords();
        System.out.println(categoryList);
        //获取页码
        Long pages1 = list.getPages();
        //获取总条数
        Integer count= dao.selectCount(queryWrapper);
        //返回前端的条数
        result.setRows(categoryList);
        result.setPage(page);// 当前页
        result.setTotal(pages1.intValue());// 总页数
        result.setRecords(count);// 信息总条数
        return result;
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Result<?> edit(String oper,Category category,String pId){
        Result result = new Result<>();
        if("add".equals(oper)){
            if(pId==null){
                category.setId(null);
                category.setLevel("1");
                service.save(category);
            }else {
                category.setId(null);
                category.setLevel("2");
                category.setPid(pId);
                service.save(category);
            }
        }
        if("edit".equals(oper)){
            service.updateById(category);
        }
        if("del".equals(oper)){
            /*通过得到的id查询级别*/
            Category id = service.getById(category.getId());
            if(category.getId().contains(",")){
                String[] split = category.getId().split(",");
                for (String s : split) {
                    QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
                    Category byId = service.getById(s);
                    if(byId.getLevel().equals("1")){
                        queryWrapper.eq("level","2");
                        queryWrapper.eq("p_id",byId.getId());
                        /*通过得到的id查询内部的id*/
                        List<Category> list = service.list(queryWrapper);
                        if(list.size()==0){
                            service.removeById(byId.getId());
                        }
                    }else {
                        service.removeById(byId.getId());
                    }
                }
            }else {
                if(id.getLevel().equals("1")){
                    QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("level","2");
                    queryWrapper.eq("p_id",id.getId());
                    /*通过得到的id查询内部的id*/
                    List<Category> list = service.list(queryWrapper);
                    if(list.size()==0){
                        service.removeById(category.getId());
                    }
                }else {
                    service.removeById(category.getId());
                }
            }
        }
        return result;
    }


}

