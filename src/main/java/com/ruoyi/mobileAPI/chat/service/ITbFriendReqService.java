package com.ruoyi.mobileAPI.chat.service;

import java.util.List;
import com.ruoyi.mobileAPI.chat.domain.TbFriendReq;

/**
 * 好友请求Service接口
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
public interface ITbFriendReqService 
{
    /**
     * 查询好友请求
     * 
     * @param id 好友请求ID
     * @return 好友请求
     */
    public TbFriendReq selectTbFriendReqById(String id);

    /**
     * 查询好友请求列表
     * 
     * @param tbFriendReq 好友请求
     * @return 好友请求集合
     */
    public List<TbFriendReq> selectTbFriendReqList(TbFriendReq tbFriendReq);

    /**
     * 新增好友请求
     * 
     * @param tbFriendReq 好友请求
     * @return 结果
     */
    public int insertTbFriendReq(TbFriendReq tbFriendReq);

    /**
     * 修改好友请求
     * 
     * @param tbFriendReq 好友请求
     * @return 结果
     */
    public int updateTbFriendReq(TbFriendReq tbFriendReq);

    /**
     * 批量删除好友请求
     * 
     * @param ids 需要删除的好友请求ID
     * @return 结果
     */
    public int deleteTbFriendReqByIds(String[] ids);

    /**
     * 删除好友请求信息
     * 
     * @param id 好友请求ID
     * @return 结果
     */
    public int deleteTbFriendReqById(String id);
}
