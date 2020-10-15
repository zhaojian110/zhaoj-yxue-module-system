package com.zhaoj.service.impl;

import com.zhaoj.entity.Group;
import com.zhaoj.dao.GroupDao;
import com.zhaoj.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupDao, Group> implements GroupService {

}
