package com.ruoyi.mobileAPI.chat.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.mobileAPI.chat.domain.TbChatRecord;
import com.ruoyi.mobileAPI.chat.service.ITbChatRecordService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 聊天记录Controller
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
@RestController
@RequestMapping("/chat/record")
public class TbChatRecordController extends BaseController
{
    @Autowired
    private ITbChatRecordService tbChatRecordService;

    /**
     * 查询聊天记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TbChatRecord tbChatRecord)
    {
        startPage();
        List<TbChatRecord> list = tbChatRecordService.selectTbChatRecordList(tbChatRecord);
        return getDataTable(list);
    }

    /**
     * 导出聊天记录列表
     */
    @Log(title = "聊天记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbChatRecord tbChatRecord)
    {
        List<TbChatRecord> list = tbChatRecordService.selectTbChatRecordList(tbChatRecord);
        ExcelUtil<TbChatRecord> util = new ExcelUtil<TbChatRecord>(TbChatRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 获取聊天记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tbChatRecordService.selectTbChatRecordById(id));
    }

    /**
     * 新增聊天记录
     */
    @Log(title = "聊天记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbChatRecord tbChatRecord)
    {
        return toAjax(tbChatRecordService.insertTbChatRecord(tbChatRecord));
    }

    /**
     * 修改聊天记录
     */
    @Log(title = "聊天记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbChatRecord tbChatRecord)
    {
        return toAjax(tbChatRecordService.updateTbChatRecord(tbChatRecord));
    }

    /**
     * 删除聊天记录
     */
    @Log(title = "聊天记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tbChatRecordService.deleteTbChatRecordByIds(ids));
    }
}
