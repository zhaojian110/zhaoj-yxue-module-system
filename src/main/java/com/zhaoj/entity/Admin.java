package com.zhaoj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.UUID;

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
@TableName("yx_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 自动生成UUID
     */
    @TableId(type = IdType.UUID)
    private String id;

    private String username;

    private String password;


}
