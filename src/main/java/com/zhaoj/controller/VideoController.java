package com.zhaoj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.zhaoj.common.Result;
import com.zhaoj.dao.CategoryDao;
import com.zhaoj.dao.VideoDao;
import com.zhaoj.entity.Category;
import com.zhaoj.entity.User;
import com.zhaoj.entity.Video;
import com.zhaoj.respository.VideoRepository;
import com.zhaoj.service.CategoryService;
import com.zhaoj.service.VideoService;
import com.zhaoj.util.QiniuYunUtil;
import com.zhaoj.util.VideoImgTools;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService service;
    @ResponseBody
    @RequestMapping("queryAll")
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
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            //创建需要的页码和条数
            IPage<Video> iPage = new Page<>(page,rows);
            //查询的东西是
            IPage<Video> list = service.page(iPage, queryWrapper);
            //获取结果
            List<Video> videos = service.queryAll(page, rows);
            //获取页码
            Long pages1 = list.getPages();
            //获取总条数
            Long size = iPage.getSize();
            //返回前端的条数
            result.setRows(videos);
            result.setPage(page);// 当前页
            result.setTotal(pages1.intValue());// 总页数
            result.setRecords(size.intValue());// 信息总条数
        return result;
    }

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("findCategory")
    //直接将拼接好的下拉框返回
    @ResponseBody
    public  List<Category> findAll(String dharma){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        QueryWrapper<Category> level = wrapper.eq("level", "2");
        List<Category> list = categoryService.list(level);
       /* StringBuilder sbu = new StringBuilder("<select class='form-control' name='guruId' >");*/
        //添加了默认选中
        /*for (Guru guru : gurus) {
            if(guru.getDharma().equals(dharma)){
                String option = "<option value='" + guru.getId() + "' selected>" + guru.getDharma() + "</option>";
                sbu.append(option);
            }else{
                String option = "<option value='" + guru.getId() + "'>" + guru.getDharma() + "</option>";
                sbu.append(option);
            }
        }
        sbu.append("</select>");
        System.out.println(sbu.toString());*/
        return list;
    }
    @Autowired
    VideoRepository videoRepository;

    @ResponseBody
    @RequestMapping("/edit")
    public Result<?> edit(String oper,Video video) {
        Result result = new Result<>();
        if ("add".equals(oper)) {
            video.setId(null);
            System.out.println(video);
            service.save(video);
            Video video1 = service.getById(video.getId());
            System.out.println("video1="+video1);
            videoRepository.save(video1);
            result.setMessage(video.getId());
        }
        if ("edit".equals(oper)) {
            QueryWrapper<Video> wrapper = new QueryWrapper<>();
            QueryWrapper<Video> id = wrapper.eq("id", video.getId());
            service.update(video, id);
            result.setMessage(video.getId());
        }
        if ("del".equals(oper)) {
            if (video.getId().contains(",")) {
                String[] split = video.getId().split(",");
                for (String s : split) {
                    service.removeById(s);
                }
            } else {
                System.out.println(video.getId());
                service.removeById(video.getId());
            }
        }
        return result;
    }
    @RequestMapping("upload")
    public void upload(String id, MultipartFile videoUrl, HttpServletRequest request) throws Exception {
        //进行文件上传
        //1.获取绝对路径
        //获取相对路径
//      String contextPath = request.getSession().getServletContext().getContextPath();
        String realPath = request.getSession().getServletContext().getRealPath("/upload_file/video/");
        //2.文件上传 上传文件的文件名
        String Real =  videoUrl.getOriginalFilename();
        String filename = new Date().getTime() + "-" + videoUrl.getOriginalFilename();
        String upload = null;
        //上传到七牛云
        try {//将路径和文件传入文件
            videoUrl.transferTo(new File(realPath + filename));

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("我上传的东西地址在"+realPath + filename);
        upload = QiniuYunUtil.upload(realPath + filename);
        //获取图片截图  需要导入 依赖 和 工具类
        String imgCover = VideoImgTools.mai(realPath + filename,"D:\\backdemotest\\backdemotest1\\zhaoj-yxue-module-system\\src\\main\\webapp\\upload_file\\videoImg/");
        Video video = new Video();
        video.setId(id);
        video.setCoverUrl(imgCover);
        video.setVideoUrl(upload);
        service.updateById(video);
    }


    @RequestMapping("queryCate")
    @ResponseBody
    public StringBuilder queryCate(){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        QueryWrapper<Category> level = wrapper.eq("level", "2");
        List<Category> categories = categoryService.list(level);
        StringBuilder builder = new StringBuilder();
        builder.append("<select>");
        //下拉框
        for (Category category : categories) {
            builder.append("<option value='").append(category.getId()).append("'>").append(category.getName()).append("</option>");
        }
        builder.append("</select>");
        return builder;
    }



    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    VideoService videoService;

    @ResponseBody
    @RequestMapping("querySearchVideo")
    public List<Video> queryVideoss(String content){
        List<Video> videos = videoService.querySearchVideos(content);
        return videos;
    }
}

