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
    public List <UserMessage> selectUserMessagehistory(UserMessage userMessage);

    public UserMessage selectUserMessageById(Long id);

    public List<UserMessage> selectUserMessageList(UserMessage userMessage);

    public int insertUserMessage(UserMessage userMessage);

    public int updateUserMessage(UserMessage userMessage);

    public int deleteUserMessageByIds(Long[] ids);

    public int deleteUserMessageById(Long id);
}
