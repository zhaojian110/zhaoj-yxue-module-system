package com.zhaoj.log.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.annotation.Target;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaoj
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yx_log")
public class Log implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 日志编号
     */
    @TableId(type = IdType.UUID)
    @Excel(name = "日志编号")
    private String id;

    /**
     * 操作用户名
     */
    @Excel(name = "用户名称")
    private String username;

    /**
     * 操作时间
     */
    @Excel(name = "操作时间",format = "yyyy-MM-dd")
    private Date operactionAt;

    /**
     * 操作表名
     */
    @Excel(name = "操作表名")
    private String tableName;

    /**
     * 操作的业务类型
     */
    @Excel(name = "操作业务类型")
    private String operationMethod;

    /**
     * 操作的方法签名
     */
    @Excel(name = "操作方法签名")
    private String methodName;

    /**
     * 操作数据的ID
     */
    @Excel(name = "日志id")
    private String dataId;

    /**
     * 如果是删除，记录删除的数据以便于回复
     */
    @Excel(name = "日志信息")
    private String dataInfo;
    /*
        集合的话就是
        @ExcelCollection(name = "学生们")
    *
    * private List<?> Student
    *
    * */

}
