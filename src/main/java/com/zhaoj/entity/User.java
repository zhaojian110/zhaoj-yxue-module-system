package com.zhaoj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("yx_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    private String username;

    private String mobile;

    private String sign;

    private String headShow;

    private String status;
//    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regTime;

    private Double score;

    private String wechat;
    @TableField(exist = false)
    private Integer sum;

}
