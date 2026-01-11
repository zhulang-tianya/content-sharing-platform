package com.content.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 内容实体类
 */
@Data
@TableName("content")
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容类型：0-小说，1-文章，2-视频，3-音频
     */
    private Integer type;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 简介
     */
    private String description;

    /**
     * 内容状态：0-草稿，1-发布，2-下架
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Long views;

    /**
     * 点赞数
     */
    private Long likes;

    /**
     * 评论数
     */
    private Long comments;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}