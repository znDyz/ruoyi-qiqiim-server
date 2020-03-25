package com.ruoyi.mobileAPI.chat.service.impl;

import java.util.List;
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

    /**
     * 查询聊天功能
     * 
     * @param id 聊天功能ID
     * @return 聊天功能
     */
    @Override
    public TbFriend selectTbFriendById(String id)
    {
        return tbFriendMapper.selectTbFriendById(id);
    }

    /**
     * 查询聊天功能列表
     * 
     * @param tbFriend 聊天功能
     * @return 聊天功能
     */
    @Override
    public List<TbFriend> selectTbFriendList(TbFriend tbFriend)
    {
        return tbFriendMapper.selectTbFriendList(tbFriend);
    }

    /**
     * 新增聊天功能
     * 
     * @param tbFriend 聊天功能
     * @return 结果
     */
    @Override
    public int insertTbFriend(TbFriend tbFriend)
    {
        return tbFriendMapper.insertTbFriend(tbFriend);
    }

    /**
     * 修改聊天功能
     * 
     * @param tbFriend 聊天功能
     * @return 结果
     */
    @Override
    public int updateTbFriend(TbFriend tbFriend)
    {
        return tbFriendMapper.updateTbFriend(tbFriend);
    }

    /**
     * 批量删除聊天功能
     * 
     * @param ids 需要删除的聊天功能ID
     * @return 结果
     */
    @Override
    public int deleteTbFriendByIds(String[] ids)
    {
        return tbFriendMapper.deleteTbFriendByIds(ids);
    }

    /**
     * 删除聊天功能信息
     * 
     * @param id 聊天功能ID
     * @return 结果
     */
    @Override
    public int deleteTbFriendById(String id)
    {
        return tbFriendMapper.deleteTbFriendById(id);
    }
}
