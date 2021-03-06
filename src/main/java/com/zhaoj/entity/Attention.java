package com.zhaoj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("yx_attention")
public class Attention implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String fromUserId;

    private String toUserId;


}
