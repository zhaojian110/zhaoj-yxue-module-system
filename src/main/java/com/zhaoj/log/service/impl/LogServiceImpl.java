package com.zhaoj.log.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoj.log.dao.LogDao;
import com.zhaoj.log.entity.Log;
import com.zhaoj.log.service.LogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-28
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

}
