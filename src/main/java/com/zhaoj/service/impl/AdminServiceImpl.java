package com.zhaoj.service.impl;

import com.zhaoj.entity.Admin;
import com.zhaoj.dao.AdminDao;
import com.zhaoj.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {

}
