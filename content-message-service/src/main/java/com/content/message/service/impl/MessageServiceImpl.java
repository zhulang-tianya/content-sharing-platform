package com.content.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.content.message.entity.Message;
import com.content.message.mapper.MessageMapper;
import com.content.message.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息服务实现
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    
    @Override
    public List<Message> getByReceiverId(Long receiverId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, receiverId)
                    .orderByDesc(Message::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Message> getByReceiverIdAndStatus(Long receiverId, Integer status) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, receiverId)
                    .eq(Message::getStatus, status)
                    .orderByDesc(Message::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean sendMessage(Message message) {
        message.setCreateTime(LocalDateTime.now());
        message.setStatus(0);
        return save(message);
    }
    
    @Override
    public boolean markAsRead(Long id) {
        Message message = new Message();
        message.setId(id);
        message.setStatus(1);
        message.setReadTime(LocalDateTime.now());
        return updateById(message);
    }
    
    @Override
    public boolean batchMarkAsRead(List<Long> ids) {
        Message message = new Message();
        message.setStatus(1);
        message.setReadTime(LocalDateTime.now());
        LambdaQueryWrapper<Message> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.in(Message::getId, ids);
        return update(message, updateWrapper);
    }
    
    @Override
    public Integer getUnreadCount(Long receiverId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, receiverId)
                    .eq(Message::getStatus, 0);
        return Math.toIntExact(baseMapper.selectCount(queryWrapper));
    }
}