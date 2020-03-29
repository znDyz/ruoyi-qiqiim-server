package com.ruoyi.mobileAPI.chat.controller;

import java.util.List;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.project.system.domain.SysUser;
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
import com.ruoyi.mobileAPI.chat.domain.TbFriend;
import com.ruoyi.mobileAPI.chat.service.ITbFriendService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 聊天功能Controller
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
@RestController
@RequestMapping("/chat/friend")
public class TbFriendController extends BaseController
{
    @Autowired
    private ITbFriendService tbFriendService;

    /**
     * 获取用户好友列表
     */
    @GetMapping("/getFriends")
    public AjaxResult getFriends(TbFriend tbFriend)
    {
       List<SysUser> list = tbFriendService.getFriends(tbFriend);
       return new AjaxResult(HttpStatus.SUCCESS,"操作成功",list);
    }

    /**
     * 查询聊天功能列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TbFriend tbFriend)
    {
        startPage();
        List<TbFriend> list = tbFriendService.selectTbFriendList(tbFriend);
        return getDataTable(list);
    }

    /**
     * 导出聊天功能列表
     */
    @Log(title = "聊天功能", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbFriend tbFriend)
    {
        List<TbFriend> list = tbFriendService.selectTbFriendList(tbFriend);
        ExcelUtil<TbFriend> util = new ExcelUtil<TbFriend>(TbFriend.class);
        return util.exportExcel(list, "friend");
    }

    /**
     * 获取聊天功能详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tbFriendService.selectTbFriendById(id));
    }

    /**
     * 新增聊天功能
     */
    @Log(title = "聊天功能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbFriend tbFriend)
    {
        return toAjax(tbFriendService.insertTbFriend(tbFriend));
    }

    /**
     * 修改聊天功能
     */
    @Log(title = "聊天功能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbFriend tbFriend)
    {
        return toAjax(tbFriendService.updateTbFriend(tbFriend));
    }

    /**
     * 删除聊天功能
     */
    @Log(title = "聊天功能", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tbFriendService.deleteTbFriendByIds(ids));
    }
}
