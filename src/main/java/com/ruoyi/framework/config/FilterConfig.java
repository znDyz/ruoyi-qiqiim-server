package com.ruoyi.framework.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.xss.XssFilter;

/**
 * Filter配置(配置自定义的过滤器)
 *
 * @author ruoyi
 */
@Configuration
public class FilterConfig
{
    @Value("${xss.enabled}")
    private String enabled;

    @Value("${xss.excludes}")
    private String excludes;

    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean xssFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        //setFilter就是你所定义的过滤器filter类
        registration.setFilter(new XssFilter());

        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", excludes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }
    /**
     * //过滤应用程序中所有资源,当前应用程序根下的所有文件包括多级子目录下的所有文件，注意这里*前有“/”
     *   registration.addUrlPatterns("/*");
     *   //过滤指定的类型文件资源, 当前应用程序根目录下的所有html文件，注意：*.html前没有“/”,否则错误
     *   registration.addUrlPatterns(".html");
     *   //过滤指定的目录下的所有文件,当前应用程序根目录下的folder_name子目录（可以是多级子目录）下所有文件
     *   registration.addUrlPatterns("/folder_name/*");
     *   //过滤指定文件
     *   registration.addUrlPatterns("/index.html");
     * */
}
