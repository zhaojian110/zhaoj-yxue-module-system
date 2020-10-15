package com.zhaoj.dao;

import com.zhaoj.entity.Like;
import com.zhaoj.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
public interface VideoDao extends BaseMapper<Video> {
    /**
     * 查询视频表
     * @param start 开始的下标 0为最底
     * @param rows  查询的数量
     * @return
     */
    public List<Video> queryAll(@Param("start")Integer start,@Param("rows") Integer rows );

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
}
