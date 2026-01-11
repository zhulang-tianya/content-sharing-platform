package com.content.search.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 搜索历史实体
 */
@Data
@TableName("t_search")
public class Search implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（0表示匿名搜索）
     */
    private Long userId;

    /**
     * 搜索关键词
     */
    private String keyword;

    /**
     * 搜索类型：1-内容搜索 2-用户搜索 3-标签搜索
     */
    private Integer type;

    /**
     * 搜索结果数量
     */
    private Integer resultCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}