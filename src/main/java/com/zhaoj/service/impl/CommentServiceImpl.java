package com.zhaoj.service.impl;

import com.zhaoj.entity.Comment;
import com.zhaoj.dao.CommentDao;
import com.zhaoj.log.anno.YxueLog;
import com.zhaoj.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao dao;
    @Override
    public List<Comment> queryAllFirst(Integer page, Integer rows) {
        Integer start = (page-1)*rows;
        return dao.queryAllFirst(start,rows);
    }

    @Override
    public List<Comment> queryAllDouble(Integer page, Integer rows, String interAct) {
        Integer start = (page-1)*rows;
        return dao.queryAllDouble(start,rows,interAct);
    }

    @Override
    @YxueLog(value = "添加评论",tableName = "yx_comment",methodName = "save")
    public boolean save(Comment entity) {
        return super.save(entity);
    }

    @Override
    @YxueLog(value = "修改评论",tableName = "yx_comment",methodName = "updateById")
    public boolean updateById(Comment entity) {
        return super.updateById(entity);
    }

    @Override
    @YxueLog(value = "删除评论",tableName = "yx_comment",methodName = "removeById")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
