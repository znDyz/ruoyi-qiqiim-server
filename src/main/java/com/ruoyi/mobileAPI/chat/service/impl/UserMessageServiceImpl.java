package com.ruoyi.mobileAPI.chat.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mobileAPI.chat.mapper.UserMessageMapper;
import com.ruoyi.mobileAPI.chat.domain.UserMessage;
import com.ruoyi.mobileAPI.chat.service.IUserMessageService;

/**
 * 聊天功能Service业务层处理
 *
 * @author ruoyi
 * @date 2020-03-29
 */
@Service
public class UserMessageServiceImpl implements IUserMessageService
{
    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public List<UserMessage> selectUserMessagehistory(UserMessage userMessage) {
        return userMessageMapper.selectUserMessagehistory(userMessage);
    }

    //查询聊天功能
    @Override
    public UserMessage selectUserMessageById(Long id)
    {
        return userMessageMapper.selectUserMessageById(id);
    }

    //查询聊天功能列表
    @Override
    public List<UserMessage> selectUserMessageList(UserMessage userMessage)
    {
        return userMessageMapper.selectUserMessageList(userMessage);
    }

    //新增聊天功能
    @Override
    public int insertUserMessage(UserMessage userMessage)
    {
        return userMessageMapper.insertUserMessage(userMessage);
    }

    //修改聊天功能
    @Override
    public int updateUserMessage(UserMessage userMessage)
    {
        return userMessageMapper.updateUserMessage(userMessage);
    }

    //批量删除聊天功能
    @Override
    public int deleteUserMessageByIds(Long[] ids)
    {
        return userMessageMapper.deleteUserMessageByIds(ids);
    }

    //删除聊天功能信息
    @Override
    public int deleteUserMessageById(Long id)
    {
        return userMessageMapper.deleteUserMessageById(id);
    }
}
