package com.zhaoj.dao;

import com.zhaoj.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
public interface UserDao extends BaseMapper<User> {

    public List<User> queryByRegist();
    public List<User> queryByStatus();
    public void insertA0(User user);
}
