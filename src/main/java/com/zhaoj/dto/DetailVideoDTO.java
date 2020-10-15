package com.zhaoj.dto;

import com.zhaoj.dto.Detail.VideoListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 作者:zhaoj
 * 创建时间:2020/10/11    13:23
 * 类的作用:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVideoDTO {
    //视频id
    private String id;
    //视频标题
    private String videoTitle;
    //视频封面
    private String cover;
    //视频路径
    private String path;
    //视频上传时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;
    //视频描述
    private String description;
    //视频点赞数
    private String likeCount;
    //所属类别名
    private String cateName;
    //所属类别id
    private String categoryId;
    //所属用户ID
    private String userId;
    //用户头像
    private String userPicImg;
    //用户名
    private String userName;
    //播放次数
    private Integer playCount;
    //是否关注该用户
    private boolean isAttention;
    //视频集合
    private List<VideoListDTO> videoList;

}
