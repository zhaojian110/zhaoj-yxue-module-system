package com.zhaoj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
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
@TableName("yx_video")
@Document(indexName = "yingx",type = "video")
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;
    //ES搜索所用
    @Id
    @TableId(type = IdType.UUID)
    private String id;

    //创建索引的时候用analyzer  搜索索引的时候用的是searchAnalyzer
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String intro;

    @Field(type = FieldType.Keyword)
    private String coverUrl;

    @Field(type = FieldType.Keyword)
    private String videoUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String userId;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String cid;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String grpId;
    /*
    *@TableField(exist = false)
    * 注解加载bean属性上，
    * 表示当前属性不是数据库的字段，
    * 但在项目中必须使用，
    * 这样在新增等使用bean的时候，
    * mybatis-plus就会忽略这个，不会报错
    * */

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Category category;

    @TableField(exist = false)
    private Group group;

    @TableField(exist = false)
    private Like like;

    @TableField(exist = false)
    private Play play;
    //视频点赞量是多少 Mapper文件中对应

    @TableField(exist = false)
    private String cou;

    public Video(String id,String title,String intro,String coverUrl,String videoUrl,Date dates,String userId,String cid,String grpId){
        this.id=id;
        this.title=title;
        this.intro=intro;
        this.coverUrl=coverUrl;
        this.videoUrl=videoUrl;
        this.createAt=dates;
        this.userId=userId;
        this.cid=cid;
        this.grpId=grpId;
    }
}
