package com.ruoyi.mobileAPI.chat.service;

import java.util.List;
import com.ruoyi.mobileAPI.chat.domain.TbFriend;
import com.ruoyi.project.system.domain.SysUser;

/**
 * 聊天功能Service接口
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
public interface ITbFriendService 
{

    //根据条件关联获取好友信息
    public List<SysUser> getFriends(TbFriend tbFriend);

    //
    public TbFriend selectTbFriendById(String id);

    //
    public List<TbFriend> selectTbFriendList(TbFriend tbFriend);

    //
    public int insertTbFriend(TbFriend tbFriend);

    //
    public int updateTbFriend(TbFriend tbFriend);

    //
    public int deleteTbFriendByIds(String[] ids);

    //
    public int deleteTbFriendById(String id);
}
