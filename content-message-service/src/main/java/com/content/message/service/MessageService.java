package com.content.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.content.message.entity.Message;

import java.util.List;

/**
 * 消息服务
 */
public interface MessageService extends IService<Message> {
    
    /**
     * 根据接收者ID获取消息列表
     * @param receiverId 接收者ID
     * @return 消息列表
     */
    List<Message> getByReceiverId(Long receiverId);
    
    /**
     * 根据接收者ID和消息状态获取消息列表
     * @param receiverId 接收者ID
     * @param status 消息状态
     * @return 消息列表
     */
    List<Message> getByReceiverIdAndStatus(Long receiverId, Integer status);
    
    /**
     * 发送消息
     * @param message 消息对象
     * @return 是否成功
     */
    boolean sendMessage(Message message);
    
    /**
     * 标记消息为已读
     * @param id 消息ID
     * @return 是否成功
     */
    boolean markAsRead(Long id);
    
    /**
     * 批量标记消息为已读
     * @param ids 消息ID列表
     * @return 是否成功
     */
    boolean batchMarkAsRead(List<Long> ids);
    
    /**
     * 获取未读消息数量
     * @param receiverId 接收者ID
     * @return 未读消息数量
     */
    Integer getUnreadCount(Long receiverId);
}