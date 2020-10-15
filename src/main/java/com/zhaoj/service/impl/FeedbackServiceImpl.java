package com.zhaoj.service.impl;

import com.zhaoj.entity.Feedback;
import com.zhaoj.dao.FeedbackDao;
import com.zhaoj.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackDao, Feedback> implements FeedbackService {

    @Autowired
    private FeedbackDao dao;
    @Override
    public List<Feedback> queryAll(Integer page, Integer rows) {
        Integer start = (page-1)*rows;
        return dao.queryAll(start,rows);
    }
}
