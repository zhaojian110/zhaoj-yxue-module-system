package com.zhaoj.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.zhaoj.common.Result;
import com.zhaoj.entity.Category;
import com.zhaoj.entity.Comment;
import com.zhaoj.entity.Video;
import com.zhaoj.service.CommentService;
import com.zhaoj.service.VideoService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;
    @Autowired
    private HttpServletResponse response;
    /**
     * 报表导出
     * @throws Exception
     */
    @RequestMapping("export")
    @ResponseBody
    public void export()throws Exception{
        List<Comment> comments = service.list();
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams("类别信息", "类别信息表"), Comment.class, comments);
        /**
         * 设置下载的响应头
         */
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-type","application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=pinglun.xls");
        OutputStream outputStream = response.getOutputStream();
        sheets.write(outputStream);
    }
    @ResponseBody
    @RequestMapping("queryAllFirst")
    public Result queryAll(
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
        //查询的结果
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        /*queryWrapper.eq("interactId",null);*/
        //创建需要的页码和条数
        IPage<Comment> iPage = new Page<>(page,rows);
        //查询的东西是
        IPage<Comment> list = service.page(iPage, queryWrapper);
        //获取结果
//        List<Comment> comments = list.getRecords();
        List<Comment> comments = service.queryAllFirst(page, rows);
        //获取页码
        Long pages1 = list.getPages();
        //获取总条数
        Long size = iPage.getSize();
        //返回前端的条数
        result.setRows(comments);
        result.setPage(page);// 当前页
        result.setTotal(pages1.intValue());// 总页数
        result.setRecords(size.intValue());// 信息总条数
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
            String pId//
    ){
        //new 一个结果集
        Result result = new Result();
        //查询的结果
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        //创建需要的页码和条数
        IPage<Comment> iPage = new Page<>(page,rows);
        //查询的东西是
        IPage<Comment> list = service.page(iPage, queryWrapper);
        //获取结果
        List<Comment> comments = service.queryAllDouble(page, rows, pId);

        //获取页码
        Long pages1 = list.getPages();
        //获取总条数
        Integer count= service.count();
        //返回前端的条数
        result.setRows(comments);
        result.setPage(page);// 当前页
        result.setTotal(pages1.intValue());// 总页数
        result.setRecords(count);// 信息总条数
        return result;
    }
    @ResponseBody
    @RequestMapping("/edit")
    //pid 表示父类的Id
    public Result<?> edit(String oper,Comment comment,String pId){
        Result result = new Result<>();
        if("add".equals(oper)){
            if(pId==null){
                comment.setId(null);
                comment.setCreateAt(new Date());
                service.save(comment);
            }else {
                comment.setId(null);
                comment.setCreateAt(new Date());
                comment.setInteractId(pId);
                service.save(comment);
            }
        }
        if("edit".equals(oper)){
            service.updateById(comment);
        }
        if("del".equals(oper)){
            /*通过得到的id查询级别*/
            Comment id = service.getById(comment.getId());
            if(comment.getId().contains(",")){
                String[] split = comment.getId().split(",");
                for (String s : split) {
                    Comment byId = service.getById(s);
                            service.removeById(byId.getId());
                }
            }else {
                    service.removeById(comment.getId());
            }

        }
        return result;
    }

    @Autowired
    private VideoService videoService;

    @RequestMapping("queryAllInter")
    @ResponseBody
    public StringBuilder queryAllInter(){
        List<Video> list = videoService.list();
        StringBuilder builder = new StringBuilder();
        builder.append("<select>");
        //下拉框
        for (Video comment : list) {
            builder.append("<option value='").append(comment.getId()).append("'>").append(comment.getTitle()).append("</option>");
        }
        builder.append("</select>");
        return builder;
    }

}

