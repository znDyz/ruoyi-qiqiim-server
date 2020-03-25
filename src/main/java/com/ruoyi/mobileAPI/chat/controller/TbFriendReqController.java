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
import com.ruoyi.mobileAPI.chat.domain.TbFriendReq;
import com.ruoyi.mobileAPI.chat.service.ITbFriendReqService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 好友请求Controller
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
@RestController
@RequestMapping("/system/friendReq")
public class TbFriendReqController extends BaseController
{
    @Autowired
    private ITbFriendReqService tbFriendReqService;

    /**
     * 查询好友请求列表
     */
    @PreAuthorize("@ss.hasPermi('system:friendReq:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbFriendReq tbFriendReq)
    {
        startPage();
        List<TbFriendReq> list = tbFriendReqService.selectTbFriendReqList(tbFriendReq);
        return getDataTable(list);
    }

    /**
     * 导出好友请求列表
     */
    @PreAuthorize("@ss.hasPermi('system:friendReq:export')")
    @Log(title = "好友请求", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbFriendReq tbFriendReq)
    {
        List<TbFriendReq> list = tbFriendReqService.selectTbFriendReqList(tbFriendReq);
        ExcelUtil<TbFriendReq> util = new ExcelUtil<TbFriendReq>(TbFriendReq.class);
        return util.exportExcel(list, "friendReq");
    }

    /**
     * 获取好友请求详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:friendReq:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tbFriendReqService.selectTbFriendReqById(id));
    }

    /**
     * 新增好友请求
     */
    @PreAuthorize("@ss.hasPermi('system:friendReq:add')")
    @Log(title = "好友请求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbFriendReq tbFriendReq)
    {
        return toAjax(tbFriendReqService.insertTbFriendReq(tbFriendReq));
    }

    /**
     * 修改好友请求
     */
    @PreAuthorize("@ss.hasPermi('system:friendReq:edit')")
    @Log(title = "好友请求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbFriendReq tbFriendReq)
    {
        return toAjax(tbFriendReqService.updateTbFriendReq(tbFriendReq));
    }

    /**
     * 删除好友请求
     */
    @PreAuthorize("@ss.hasPermi('system:friendReq:remove')")
    @Log(title = "好友请求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tbFriendReqService.deleteTbFriendReqByIds(ids));
    }
}
