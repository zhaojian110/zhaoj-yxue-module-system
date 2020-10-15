package com.zhaoj.dao;

import com.zhaoj.entity.Comment;
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
public interface CommentDao extends BaseMapper<Comment> {

    /**
     * 查询所有一级评论
     * @param start
     * @param end
     * @return
     */
    public List<Comment> queryAllFirst(@Param("start")Integer start,@Param("rows")Integer end);
    /**
     * 查询所有二级评论
     * @param start
     * @param end
     * @return
     */
    public List<Comment> queryAllDouble(@Param("start")Integer start,@Param("rows")Integer end,@Param("interact")String interAct);
}
