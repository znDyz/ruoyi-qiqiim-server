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
import com.ruoyi.mobileAPI.chat.domain.UserMessage;
import com.ruoyi.mobileAPI.chat.service.IUserMessageService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 聊天功能Controller
 * 
 * @author ruoyi
 * @date 2020-03-29
 */
@RestController
@RequestMapping("/chat/message")
public class UserMessageController extends BaseController
{
    @Autowired
    private IUserMessageService userMessageService;

    //查询聊天信息列表
    @GetMapping("/history")
    public TableDataInfo history(UserMessage userMessage)
    {
        startPage();
        List<UserMessage> list = userMessageService.selectUserMessagehistory(userMessage);
        return getDataTable(list);
    }

    //查询聊天功能列表
    @GetMapping("/list")
    public TableDataInfo list(UserMessage userMessage)
    {
        startPage();
        List<UserMessage> list = userMessageService.selectUserMessageList(userMessage);
        return getDataTable(list);
    }

    //导出聊天功能列表
    @Log(title = "聊天功能", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UserMessage userMessage)
    {
        List<UserMessage> list = userMessageService.selectUserMessageList(userMessage);
        ExcelUtil<UserMessage> util = new ExcelUtil<UserMessage>(UserMessage.class);
        return util.exportExcel(list, "message");
    }

    //获取聊天功能详细信息
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userMessageService.selectUserMessageById(id));
    }

    //新增聊天功能
    @Log(title = "聊天功能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserMessage userMessage)
    {
        return toAjax(userMessageService.insertUserMessage(userMessage));
    }

    //修改聊天功能
    @Log(title = "聊天功能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserMessage userMessage)
    {
        return toAjax(userMessageService.updateUserMessage(userMessage));
    }

    //删除聊天功能
    @Log(title = "聊天功能", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userMessageService.deleteUserMessageByIds(ids));
    }
}
