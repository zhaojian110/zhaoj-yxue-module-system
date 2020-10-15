package com.zhaoj.dao;

import com.zhaoj.entity.Feedback;
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
public interface FeedbackDao extends BaseMapper<Feedback> {

    /**
     * 分页查询
     * @param start
     * @param rows
     * @return
     */
    public List<Feedback> queryAll(@Param("start")Integer start,@Param("rows") Integer rows);
}
