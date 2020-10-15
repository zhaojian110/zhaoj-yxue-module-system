package com.zhaoj.service;

import com.zhaoj.entity.Feedback;
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
public interface FeedbackService extends IService<Feedback> {

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    public List<Feedback> queryAll(Integer page, Integer rows);
}
