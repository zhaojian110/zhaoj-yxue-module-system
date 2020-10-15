package com.zhaoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhaoj.annotation.AddCache;
import com.zhaoj.entity.Category;
import com.zhaoj.dao.CategoryDao;
import com.zhaoj.log.anno.YxueLog;
import com.zhaoj.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Override
    @YxueLog(value = "删除类别",tableName = "yx_category",methodName = "removeById")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @YxueLog(value = "添加类别",tableName = "yx_category",methodName = "save")
    public boolean save(Category entity) {
        return super.save(entity);
    }

    @Override
    @YxueLog(value = "修改类别",tableName = "yx_category",methodName = "updateById")
    public boolean updateById(Category entity) {
        return super.updateById(entity);
    }

    @AddCache
    @Override
    public IPage<Category> page(IPage<Category> page, Wrapper<Category> queryWrapper) {
        return super.page(page, queryWrapper);
    }
}
