package com.content.message.controller;

import com.content.common.result.Result;
import com.content.message.entity.Message;
import com.content.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息控制器
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    
    /**
     * 根据接收者ID获取消息列表
     * @param receiverId 接收者ID
     * @return 消息列表
     */
    @GetMapping("/receiver/{receiverId}")
    public Result<List<Message>> getByReceiverId(@PathVariable Long receiverId) {
        List<Message> list = messageService.getByReceiverId(receiverId);
        return Result.success(list);
    }
    
    /**
     * 根据接收者ID和消息状态获取消息列表
     * @param receiverId 接收者ID
     * @param status 消息状态
     * @return 消息列表
     */
    @GetMapping("/receiver/{receiverId}/status/{status}")
    public Result<List<Message>> getByReceiverIdAndStatus(@PathVariable Long receiverId, @PathVariable Integer status) {
        List<Message> list = messageService.getByReceiverIdAndStatus(receiverId, status);
        return Result.success(list);
    }
    
    /**
     * 发送消息
     * @param message 消息对象
     * @return 操作结果
     */
    @PostMapping
    public Result<Boolean> sendMessage(@RequestBody Message message) {
        boolean success = messageService.sendMessage(message);
        return Result.success(success);
    }
    
    /**
     * 标记消息为已读
     * @param id 消息ID
     * @return 操作结果
     */
    @PutMapping("/read/{id}")
    public Result<Boolean> markAsRead(@PathVariable Long id) {
        boolean success = messageService.markAsRead(id);
        return Result.success(success);
    }
    
    /**
     * 批量标记消息为已读
     * @param ids 消息ID列表
     * @return 操作结果
     */
    @PutMapping("/read/batch")
    public Result<Boolean> batchMarkAsRead(@RequestBody List<Long> ids) {
        boolean success = messageService.batchMarkAsRead(ids);
        return Result.success(success);
    }
    
    /**
     * 获取未读消息数量
     * @param receiverId 接收者ID
     * @return 未读消息数量
     */
    @GetMapping("/unread/count/{receiverId}")
    public Result<Integer> getUnreadCount(@PathVariable Long receiverId) {
        Integer count = messageService.getUnreadCount(receiverId);
        return Result.success(count);
    }
    
    /**
     * 删除消息
     * @param id 消息ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteMessage(@PathVariable Long id) {
        boolean success = messageService.removeById(id);
        return Result.success(success);
    }
}