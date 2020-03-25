package com.ruoyi.mobileAPI.chat.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mobileAPI.chat.mapper.TbFriendReqMapper;
import com.ruoyi.mobileAPI.chat.domain.TbFriendReq;
import com.ruoyi.mobileAPI.chat.service.ITbFriendReqService;

/**
 * 好友请求Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
@Service()
public class TbFriendReqServiceImpl implements ITbFriendReqService 
{
    @Autowired
    private TbFriendReqMapper tbFriendReqMapper;

    /**
     * 查询好友请求
     * 
     * @param id 好友请求ID
     * @return 好友请求
     */
    @Override
    public TbFriendReq selectTbFriendReqById(String id)
    {
        return tbFriendReqMapper.selectTbFriendReqById(id);
    }

    /**
     * 查询好友请求列表
     * 
     * @param tbFriendReq 好友请求
     * @return 好友请求
     */
    @Override
    public List<TbFriendReq> selectTbFriendReqList(TbFriendReq tbFriendReq)
    {
        return tbFriendReqMapper.selectTbFriendReqList(tbFriendReq);
    }

    /**
     * 新增好友请求
     * 
     * @param tbFriendReq 好友请求
     * @return 结果
     */
    @Override
    public int insertTbFriendReq(TbFriendReq tbFriendReq)
    {
        return tbFriendReqMapper.insertTbFriendReq(tbFriendReq);
    }

    /**
     * 修改好友请求
     * 
     * @param tbFriendReq 好友请求
     * @return 结果
     */
    @Override
    public int updateTbFriendReq(TbFriendReq tbFriendReq)
    {
        return tbFriendReqMapper.updateTbFriendReq(tbFriendReq);
    }

    /**
     * 批量删除好友请求
     * 
     * @param ids 需要删除的好友请求ID
     * @return 结果
     */
    @Override
    public int deleteTbFriendReqByIds(String[] ids)
    {
        return tbFriendReqMapper.deleteTbFriendReqByIds(ids);
    }

    /**
     * 删除好友请求信息
     * 
     * @param id 好友请求ID
     * @return 结果
     */
    @Override
    public int deleteTbFriendReqById(String id)
    {
        return tbFriendReqMapper.deleteTbFriendReqById(id);
    }
}
