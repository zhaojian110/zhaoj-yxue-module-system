package com.zhaoj.service;

import com.zhaoj.entity.Comment;
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
public interface CommentService extends IService<Comment> {
    /**
     * 查询所有一级评论
     * @param start
     * @param end
     * @return
     */
    public List<Comment> queryAllFirst(Integer start, Integer end); /**
     * 查询所有二级评论
     * @param page
     * @param rows
     * @return
     */
    public List<Comment> queryAllDouble(Integer page,Integer rows,String interAct);
}
