package com.zhaoj.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@TableName("yx_category")
public class Category1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    @Excel(name = "编号")
    private String id;
    @Excel(name = "类别名称")
    private String name;
    @Excel(name = "日志级别")
    private String level;
    @Excel(name = "父类别ID")
    private String pid;

}
