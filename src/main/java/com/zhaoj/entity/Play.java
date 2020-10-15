package com.zhaoj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

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
@TableName("yx_play")
@NoArgsConstructor
@AllArgsConstructor
public class Play implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String videoId;

    private Integer playNum;


}
