package com.zhaoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhaoj.annotation.AddCache;
import com.zhaoj.annotation.DelCache;
import com.zhaoj.entity.User;
import com.zhaoj.dao.UserDao;
import com.zhaoj.log.anno.YxueLog;
import com.zhaoj.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @DelCache
    @Override
    @YxueLog(value = "删除用户",tableName = "yx_user",methodName = "removeById")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
    @DelCache
    @Override
    @YxueLog(value = "添加用户",tableName = "yx_user",methodName = "save")
    public boolean save(User entity) {
        return super.save(entity);
    }
    @DelCache
    @Override
    @YxueLog(value = "修改用户",tableName = "yx_user",methodName = "updateById")
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public IPage<User> page(IPage<User> page, Wrapper<User> queryWrapper) {
        return super.page(page, queryWrapper);
    }
}
