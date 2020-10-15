package com.zhaoj.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@TableName("yx_comment")
@ExcelTarget("comment")
public class Comment1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    @Excel(name = "编号")
    private String id;
    @Excel(name = "用户Id")
    private String userId;
    @Excel(name = "资源Id")
    private String sourceId;
    @Excel(name = "内容")
    private String content;
    @Excel(name = "评论时间")
    private Date createAt;
    @Excel(name = "互动Id")
    private String interactId;

    @TableField(exist = false)
    @Excel(name = "互动者")
    private User user;
    @Excel(name = "相关视频")
    @TableField(exist = false)
    private Video video;
    @Excel(name = "互动相关图片")
    @TableField(exist = false)
    private Img img;
}
