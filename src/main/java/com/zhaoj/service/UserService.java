package com.zhaoj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhaoj.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
public interface UserService extends IService<User> {

    @Override
    IPage<User> page(IPage<User> page, Wrapper<User> queryWrapper);
}
