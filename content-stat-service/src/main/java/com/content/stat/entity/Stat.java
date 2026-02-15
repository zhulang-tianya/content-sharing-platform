package com.content.stat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 统计数据实体
 */
@Data
@TableName("t_stat")
public class Stat implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 统计日期
     */
    private LocalDate statDate;

    /**
     * 统计类型：1-日活用户数 2-新增用户数 3-内容浏览量 4-内容发布量 5-评论数 6-点赞数
     */
    private Integer type;

    /**
     * 统计数值
     */
    private Long value;

    /**
     * 关联ID（如内容ID、用户ID等）
     */
    private Long relationId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}