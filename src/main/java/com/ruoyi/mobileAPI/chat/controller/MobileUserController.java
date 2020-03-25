package com.ruoyi.mobileAPI.chat.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.mobileAPI.chat.domain.TbChatRecord;
import com.ruoyi.mobileAPI.chat.service.ITbChatRecordService;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysPostService;
import com.ruoyi.project.system.service.ISysRoleService;
import com.ruoyi.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天记录Controller
 */
@RestController
@RequestMapping("/chat/user")
public class MobileUserController extends BaseController
{

    @Autowired
    private ISysUserService userService;

    /**
     * 查询聊天记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }
}
