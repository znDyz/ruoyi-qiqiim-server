package com.ruoyi.mobileAPI.chat.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mobileAPI.chat.mapper.TbChatRecordMapper;
import com.ruoyi.mobileAPI.chat.domain.TbChatRecord;
import com.ruoyi.mobileAPI.chat.service.ITbChatRecordService;

/**
 * 聊天记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
@Service()
public class TbChatRecordServiceImpl implements ITbChatRecordService 
{
    @Autowired
    private TbChatRecordMapper tbChatRecordMapper;

    /**
     * 查询聊天记录
     * 
     * @param id 聊天记录ID
     * @return 聊天记录
     */
    @Override
    public TbChatRecord selectTbChatRecordById(String id)
    {
        return tbChatRecordMapper.selectTbChatRecordById(id);
    }

    /**
     * 查询聊天记录列表
     * 
     * @param tbChatRecord 聊天记录
     * @return 聊天记录
     */
    @Override
    public List<TbChatRecord> selectTbChatRecordList(TbChatRecord tbChatRecord)
    {
        return tbChatRecordMapper.selectTbChatRecordList(tbChatRecord);
    }

    /**
     * 新增聊天记录
     * 
     * @param tbChatRecord 聊天记录
     * @return 结果
     */
    @Override
    public int insertTbChatRecord(TbChatRecord tbChatRecord)
    {
        return tbChatRecordMapper.insertTbChatRecord(tbChatRecord);
    }

    /**
     * 修改聊天记录
     * 
     * @param tbChatRecord 聊天记录
     * @return 结果
     */
    @Override
    public int updateTbChatRecord(TbChatRecord tbChatRecord)
    {
        return tbChatRecordMapper.updateTbChatRecord(tbChatRecord);
    }

    /**
     * 批量删除聊天记录
     * 
     * @param ids 需要删除的聊天记录ID
     * @return 结果
     */
    @Override
    public int deleteTbChatRecordByIds(String[] ids)
    {
        return tbChatRecordMapper.deleteTbChatRecordByIds(ids);
    }

    /**
     * 删除聊天记录信息
     * 
     * @param id 聊天记录ID
     * @return 结果
     */
    @Override
    public int deleteTbChatRecordById(String id)
    {
        return tbChatRecordMapper.deleteTbChatRecordById(id);
    }
}
