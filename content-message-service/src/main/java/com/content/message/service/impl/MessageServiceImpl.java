package com.content.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.message.entity.Message;
import com.content.message.mapper.MessageMapper;
import com.content.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息服务实现
 */
@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    
    /**
     * 根据接收者ID获取消息列表
     * 
     * @param receiverId 接收者ID
     * @return 消息列表
     */
    @Override
    public List<Message> getByReceiverId(Long receiverId) {
        log.debug("根据接收者ID获取消息列表，receiverId: {}", receiverId);
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, receiverId)
                    .orderByDesc(Message::getCreateTime);
        List<Message> messages = baseMapper.selectList(queryWrapper);
        log.debug("查询到消息数量: {}", messages != null ? messages.size() : 0);
        return messages;
    }
    
    /**
     * 根据接收者ID和状态获取消息列表
     * 
     * @param receiverId 接收者ID
     * @param status 消息状态（0-未读 1-已读）
     * @return 消息列表
     */
    @Override
    public List<Message> getByReceiverIdAndStatus(Long receiverId, Integer status) {
        log.debug("根据接收者ID和状态获取消息列表，receiverId: {}, status: {}", receiverId, status);
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, receiverId)
                    .eq(Message::getStatus, status)
                    .orderByDesc(Message::getCreateTime);
        List<Message> messages = baseMapper.selectList(queryWrapper);
        log.debug("查询到消息数量: {}", messages != null ? messages.size() : 0);
        return messages;
    }
    
    /**
     * 发送消息
     * 
     * @param message 消息对象
     * @return 是否发送成功
     */
    @Override
    public boolean sendMessage(Message message) {
        log.debug("发送消息，message: {}", message);
        message.setCreateTime(LocalDateTime.now());
        message.setStatus(0);
        boolean result = save(message);
        log.debug("发送消息结果: {}", result);
        return result;
    }
    
    /**
     * 标记消息为已读
     * 
     * @param id 消息ID
     * @return 是否标记成功
     */
    @Override
    public boolean markAsRead(Long id) {
        log.debug("标记消息为已读，id: {}", id);
        Message message = new Message();
        message.setId(id);
        message.setStatus(1);
        message.setReadTime(LocalDateTime.now());
        boolean result = updateById(message);
        log.debug("标记消息为已读结果: {}", result);
        return result;
    }
    
    /**
     * 批量标记消息为已读
     * 
     * @param ids 消息ID列表
     * @return 是否标记成功
     */
    @Override
    public boolean batchMarkAsRead(List<Long> ids) {
        log.debug("批量标记消息为已读，ids: {}", ids);
        Message message = new Message();
        message.setStatus(1);
        message.setReadTime(LocalDateTime.now());
        LambdaQueryWrapper<Message> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.in(Message::getId, ids);
        boolean result = update(message, updateWrapper);
        log.debug("批量标记消息为已读结果: {}", result);
        return result;
    }
    
    /**
     * 获取未读消息数量
     * 
     * @param receiverId 接收者ID
     * @return 未读消息数量
     */
    @Override
    public Integer getUnreadCount(Long receiverId) {
        log.debug("获取未读消息数量，receiverId: {}", receiverId);
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, receiverId)
                    .eq(Message::getStatus, 0);
        Integer count = Math.toIntExact(baseMapper.selectCount(queryWrapper));
        log.debug("未读消息数量: {}", count);
        return count;
    }
}