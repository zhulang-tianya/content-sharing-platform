package com.content.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息实体
 */
@Data
@TableName("t_message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 消息类型：1-系统消息 2-私信 3-评论回复 4-点赞通知
     */
    private Integer type;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息状态：0-未读 1-已读
     */
    private Integer status;

    /**
     * 关联ID（如评论ID、点赞ID等）
     */
    private Long relationId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;
}