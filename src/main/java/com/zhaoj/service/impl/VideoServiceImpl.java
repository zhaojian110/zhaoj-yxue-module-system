package com.zhaoj.service.impl;

import com.zhaoj.entity.Video;
import com.zhaoj.dao.VideoDao;
import com.zhaoj.log.anno.YxueLog;
import com.zhaoj.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class VideoServiceImpl extends ServiceImpl<VideoDao, Video> implements VideoService {

    @Autowired
    private VideoDao dao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Video> queryAll(Integer page, Integer rows) {
        //rows    page
        /*
        *  5     1      0
        *  5     2      5     (page-1)rows
        * */
        Integer start = (page-1)*rows;
        return dao.queryAll(start,rows);
    }

    @Override
    public List<Video> queryAl() {
        return dao.queryAl();
    }

    @Override
    public List<Video> queryByLikeVideoName(String content) {
        return dao.queryByLikeVideoName(content);
    }

    @Override
    public Video queryByVideoDetail(String videoId, String cateId, String userId) {
        return dao.queryByVideoDetail(videoId,cateId,userId);
    }

    @Override
    public List<Video> queryByES() {
        return dao.queryByES();
    }

    @Override
    @YxueLog(value = "添加视频",tableName = "yx_video",methodName = "save")
    public boolean save(Video entity) {
        return super.save(entity);
    }

    @Override
    @YxueLog(value = "修改视频",tableName = "yx_video",methodName = "updateById")
    public boolean updateById(Video entity) {
        return super.updateById(entity);
    }

    @Override
    @YxueLog(value = "删除视频",tableName = "yx_video",methodName = "removeById")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }




    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Video> querySearchVideo(String content) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx") //指定索引
                .withTypes("video") //自定类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("intro")) //设置查询条件
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Video.class);
    }

    @Override
    public List<Video> querySearchVideos(String content) {
        //设置高亮的参数
        HighlightBuilder.Field field = new HighlightBuilder.Field("*")
                .preTags("<span style='color:red'>")
                .postTags("</span>")
                .requireFieldMatch(false);//开启多行高亮
        //设置查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx") //指定索引
                .withTypes("video") //自定类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("intro")) //设置查询条件
                .withHighlightFields(field) //设置高亮
                //.withFields("id","title","intro","coverUrl")//设置要哪些属性
                .build();

        AggregatedPage<Video> videos = elasticsearchTemplate.queryForPage(searchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                ArrayList<Video> videos = new ArrayList<>();
                SearchHit[] hits = response.getHits().getHits();
                for (SearchHit hit : hits) {
                    //没有高亮的数据
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    String id =sourceAsMap.get("id")!=null?sourceAsMap.get("id").toString():null;
                    String title =sourceAsMap.get("title")!=null?sourceAsMap.get("title").toString():null;
                    String intro =sourceAsMap.get("intro")!=null?sourceAsMap.get("intro").toString():null;
                    String coverUrl =sourceAsMap.get("coverUrl")!=null?sourceAsMap.get("coverUrl").toString():null;
                    String videoUrl =sourceAsMap.get("videoUrl")!=null?sourceAsMap.get("videoUrl").toString():null;
                    String userId =sourceAsMap.get("userId")!=null?sourceAsMap.get("userId").toString():null;
                    String cid =sourceAsMap.get("cid")!=null?sourceAsMap.get("cid").toString():null;
                    String grpId =sourceAsMap.get("grpId")!=null?sourceAsMap.get("grpId").toString():null;
                    //日期处理
                    Date dates=null;
                    if(sourceAsMap.get("createAt")!=null){
                        //处理日期的操作
                        String createAt = sourceAsMap.get("createAt").toString();
                        Long aLong = Long.valueOf(createAt);
                        dates= new Date(aLong);
                    }
                    System.out.println("videoUrl===="+videoUrl);

                    //1.将未高亮的数据封装为Video对象
                    Video video = new Video(id,title,intro,coverUrl,videoUrl,dates,userId,cid, grpId);
                    //2.将原有未高亮的数据替换为高亮的结果
                    //高亮的数据
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (highlightFields.get("title") != null) {
                        String titles = highlightFields.get("title").fragments()[0].toString();
                        video.setTitle(titles);
                    }
                    if (highlightFields.get("intro") != null) {
                        String intros = highlightFields.get("intro").fragments()[0].toString();
                        video.setIntro(intros);
                    }
                    //将封装好的对象放入集合
                    videos.add(video);
                }
                //强转返回
                return new AggregatedPageImpl<T>((List<T>) videos);
            }
        });


        List<Video> videoList = videos.getContent();
        return videoList;
    }

}
