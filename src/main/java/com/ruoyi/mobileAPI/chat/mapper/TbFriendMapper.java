package com.ruoyi.mobileAPI.chat.mapper;

import java.util.List;
import com.ruoyi.mobileAPI.chat.domain.TbFriend;
import com.ruoyi.project.system.domain.SysUser;

/**
 * 聊天功能Mapper接口
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
public interface TbFriendMapper 
{

    //
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
    public int deleteTbFriendById(String id);

    //
    public int deleteTbFriendByIds(String[] ids);
}
