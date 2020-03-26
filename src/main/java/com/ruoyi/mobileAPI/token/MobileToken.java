package com.ruoyi.mobileAPI.token;

import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api("检测用户token")
@RestController
public class MobileToken {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    @ApiOperation("检测用户token")
    @PostMapping("/checkToken")
    public AjaxResult checkToken(HttpServletRequest request)
    {
        AjaxResult ajax = AjaxResult.success("token验证成功");
        SysUser sysUser = tokenService.getLoginUser(request).getUser();
        System.out.println("移动端校验token成功!!!!!!!!!!!");
        ajax.put(AjaxResult.DATA_TAG,sysUser);
        return ajax;
    }

    @ApiOperation("测试方法")
    @PostMapping("/testMethod")
    public AjaxResult testMethod(HttpServletRequest request)
    {
        AjaxResult ajax = AjaxResult.success("此方法用于调用测试接口");
        return ajax;
    }
}
