package com.content.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.content.content.entity.Content;
import java.util.List;

/**
 * 内容服务接口
 */
public interface ContentService extends IService<Content> {
    /**
     * 根据分类ID获取内容列表
     *
     * @param categoryId 分类ID
     * @return 内容列表
     */
    List<Content> getByCategoryId(Long categoryId);

    /**
     * 根据作者ID获取内容列表
     *
     * @param authorId 作者ID
     * @return 内容列表
     */
    List<Content> getByAuthorId(Long authorId);

    /**
     * 根据内容类型获取内容列表
     *
     * @param type 内容类型
     * @return 内容列表
     */
    List<Content> getByType(Integer type);

    /**
     * 增加浏览量
     *
     * @param id 内容ID
     * @return 更新结果
     */
    boolean incrementViews(Long id);

    /**
     * 增加点赞数
     *
     * @param id 内容ID
     * @return 更新结果
     */
    boolean incrementLikes(Long id);

    /**
     * 增加评论数
     *
     * @param id 内容ID
     * @return 更新结果
     */
    boolean incrementComments(Long id);
}