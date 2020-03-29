package com.ruoyi.mobileAPI.chat.service.impl;

import java.util.List;

import com.ruoyi.project.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mobileAPI.chat.mapper.TbFriendMapper;
import com.ruoyi.mobileAPI.chat.domain.TbFriend;
import com.ruoyi.mobileAPI.chat.service.ITbFriendService;

/**
 * 聊天功能Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
@Service()
public class TbFriendServiceImpl implements ITbFriendService 
{
    @Autowired
    private TbFriendMapper tbFriendMapper;

    //根据条件关联获取好友信息
    public List<SysUser> getFriends(TbFriend tbFriend){return tbFriendMapper.getFriends(tbFriend); }

    //
    @Override
    public TbFriend selectTbFriendById(String id)
    {
        return tbFriendMapper.selectTbFriendById(id);
    }

    //
    @Override
    public List<TbFriend> selectTbFriendList(TbFriend tbFriend)
    {
        return tbFriendMapper.selectTbFriendList(tbFriend);
    }

    //
    @Override
    public int insertTbFriend(TbFriend tbFriend)
    {
        return tbFriendMapper.insertTbFriend(tbFriend);
    }

    //
    @Override
    public int updateTbFriend(TbFriend tbFriend)
    {
        return tbFriendMapper.updateTbFriend(tbFriend);
    }

    //
    @Override
    public int deleteTbFriendByIds(String[] ids)
    {
        return tbFriendMapper.deleteTbFriendByIds(ids);
    }

    //
    @Override
    public int deleteTbFriendById(String id)
    {
        return tbFriendMapper.deleteTbFriendById(id);
    }
}
