package com.zhaoj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestMapping;

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
@TableName("yx_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    private String name;

    private String userId;

    private Integer videoNum;

    private Date createAt;

    @TableField(exist = false)
    private User user;

}
