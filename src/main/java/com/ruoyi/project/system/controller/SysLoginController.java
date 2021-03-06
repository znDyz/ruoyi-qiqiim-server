package com.ruoyi.project.system.controller;

import java.util.List;
import java.util.Set;

import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.SysLoginService;
import com.ruoyi.framework.security.service.SysPermissionService;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysMenuService;

import javax.annotation.Resource;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Api("登录验证")
@RestController
public class SysLoginController
{
    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     * @param username 用户名, password 密码,captcha 验证码, uuid 唯一标识
     */
    @ApiOperation("登录方法")
    @PostMapping("/login")
    public AjaxResult login(String username, String password, String code, String uuid)
    {
        AjaxResult ajax = AjaxResult.error("程序异常");
        String token = loginService.login(username, password, code, uuid);  // 生成令牌
        if(StringUtils.isNotEmpty(token)){
            ajax = AjaxResult.success();
            ajax.put(Constants.TOKEN, token);
        }
        return ajax;
    }


    @ApiOperation("移动端登录方法")
    @PostMapping("/mobileLogin")
    public AjaxResult mobileLogin(String username, String password, String code, String uuid)
    {
        AjaxResult ajax = AjaxResult.error("程序异常");
        String token = loginService.login(username, password, code, uuid);  // 生成令牌
        if(StringUtils.isNotEmpty(token)){
            ajax = AjaxResult.success();
            ajax.put(Constants.TOKEN, token);   //返回生成的token
        }
        return ajax;
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * @return 路由信息
     */
    @ApiOperation("获取路由信息")
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
