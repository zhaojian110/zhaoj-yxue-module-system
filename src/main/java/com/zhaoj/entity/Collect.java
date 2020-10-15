package com.zhaoj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("yx_collect")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String sourceId;

    private Date collectTime;


}
