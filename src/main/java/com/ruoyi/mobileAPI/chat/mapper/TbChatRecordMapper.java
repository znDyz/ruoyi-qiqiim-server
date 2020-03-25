package com.ruoyi.mobileAPI.chat.mapper;

import java.util.List;
import com.ruoyi.mobileAPI.chat.domain.TbChatRecord;

/**
 * 聊天记录Mapper接口
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
public interface TbChatRecordMapper 
{
    /**
     * 查询聊天记录
     * 
     * @param id 聊天记录ID
     * @return 聊天记录
     */
    public TbChatRecord selectTbChatRecordById(String id);

    /**
     * 查询聊天记录列表
     * 
     * @param tbChatRecord 聊天记录
     * @return 聊天记录集合
     */
    public List<TbChatRecord> selectTbChatRecordList(TbChatRecord tbChatRecord);

    /**
     * 新增聊天记录
     * 
     * @param tbChatRecord 聊天记录
     * @return 结果
     */
    public int insertTbChatRecord(TbChatRecord tbChatRecord);

    /**
     * 修改聊天记录
     * 
     * @param tbChatRecord 聊天记录
     * @return 结果
     */
    public int updateTbChatRecord(TbChatRecord tbChatRecord);

    /**
     * 删除聊天记录
     * 
     * @param id 聊天记录ID
     * @return 结果
     */
    public int deleteTbChatRecordById(String id);

    /**
     * 批量删除聊天记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbChatRecordByIds(String[] ids);
}
