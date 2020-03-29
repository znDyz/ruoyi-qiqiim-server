package com.ruoyi.mobileAPI.chat.service;

import java.util.List;
import com.ruoyi.mobileAPI.chat.domain.UserMessage;

/**
 * 聊天功能Service接口
 * 
 * @author ruoyi
 * @date 2020-03-29
 */
public interface IUserMessageService 
{
    /**
     * 查询聊天功能
     * 
     * @param id 聊天功能ID
     * @return 聊天功能
     */
    public UserMessage selectUserMessageById(Long id);

    /**
     * 查询聊天功能列表
     * 
     * @param userMessage 聊天功能
     * @return 聊天功能集合
     */
    public List<UserMessage> selectUserMessageList(UserMessage userMessage);

    /**
     * 新增聊天功能
     * 
     * @param userMessage 聊天功能
     * @return 结果
     */
    public int insertUserMessage(UserMessage userMessage);

    /**
     * 修改聊天功能
     * 
     * @param userMessage 聊天功能
     * @return 结果
     */
    public int updateUserMessage(UserMessage userMessage);

    /**
     * 批量删除聊天功能
     * 
     * @param ids 需要删除的聊天功能ID
     * @return 结果
     */
    public int deleteUserMessageByIds(Long[] ids);

    /**
     * 删除聊天功能信息
     * 
     * @param id 聊天功能ID
     * @return 结果
     */
    public int deleteUserMessageById(Long id);
}
