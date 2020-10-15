package com.zhaoj.service;

import com.zhaoj.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
public interface VideoService extends IService<Video> {
    /**
     * 查询视频表
     * @param page 开始的下标 0为最底
     * @param rows  查询的数量
     * @return
     */
    public List<Video> queryAll(Integer page, @Param("rows") Integer rows );

    public List<Video> queryAl();
    /**
     * 模糊查询
     * @param content 查询条件
     * @return  返回集合
     */
    public List<Video> queryByLikeVideoName(@Param("content") String content);

    public Video queryByVideoDetail(@Param("videoId") String videoId,
                                    @Param("cateId") String cateId,
                                    @Param("userId") String userId);

    List<Video> queryByES();
    List<Video> querySearchVideo(String content);
    public List<Video> querySearchVideos(String content);

}
