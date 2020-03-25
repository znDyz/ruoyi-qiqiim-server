package com.ruoyi.project.system.controller;

import com.ruoyi.common.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysPostService;
import com.ruoyi.project.system.service.ISysRoleService;
import com.ruoyi.project.system.service.ISysUserService;

/**
 * 用户注册
 *
 * @author ruoyi
 */
@Api("用户申请注册")
@RestController
public class SysRegisterController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;

    @ApiOperation("注册方法")
    @PostMapping("/register")
    public AjaxResult register(/*@RequestBody SysUser user*/
            @RequestParam(value="userName",required=true)String userName,
            @RequestParam(value="phonenumber",required=true)String phonenumber,
            @RequestParam(value="email",required=true)String email,
            @RequestParam(value="nickName",required=true)String nickName,
            @RequestParam(value="sex",required=true)String sex,
            @RequestParam(value="status",required=true,defaultValue="0")String status,
            @RequestParam(value="password",required=true,defaultValue="111111")String password,
            @RequestParam(value="deptId",required=false,defaultValue="101")Long deptId,
            @RequestParam(value="postIds",required=false)Long postIds,
            @RequestParam(value="roleIds",required=false)Long roleIds,
            @RequestParam(value="remark",required=false)String remark)
    {


        SysUser user =  new SysUser();
        Long postids[] = {postIds};
        Long roleids[] = {postIds};
        user.setUserName(userName);
        user.setPhonenumber(phonenumber);
        user.setEmail(email);
        user.setNickName(nickName);
        user.setSex(sex);
        user.setStatus(status);
        user.setPassword(password);
        user.setDeptId(deptId);
        user.setPostIds(postids);
        user.setRoleIds(roleids);
        user.setRemark(remark);

        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(userName)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }

        user.setCreateBy("移动端注册");
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        int rows = userService.insertUser(user);
        if(rows>0){
            return AjaxResult.success("用户注册成功,注册id："+rows);
        }else{
            return AjaxResult.error("用户注册失败,请联系管理人员");
        }

    }

}
