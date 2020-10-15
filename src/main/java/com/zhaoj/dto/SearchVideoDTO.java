package com.zhaoj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 作者:zhaoj
 * 创建时间:2020/10/10    19:51
 * 类的作用:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVideoDTO {

    //视频id
    private String id;
    //视频标题
    private String videoTitle;
    //视频封面
    private String cover;
    //视频路径
    private String path;
    //视频上传时间
    private Date uploadTime;
    //视频描述
    private String description;
    //视频点赞数
    private String likeCount;
    //所属类别名
    private String cateName;
    //所属类别id
    private String categoryId;
    //所属用户id
    private String userId;
    //所属用户名称
    private String userName;


}
