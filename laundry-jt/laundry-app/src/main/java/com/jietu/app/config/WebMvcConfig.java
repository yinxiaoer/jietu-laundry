package com.jietu.app.config;

import com.jietu.app.interceptor.AuthInterceptor;
import com.jietu.app.interceptor.LogInterceptor;
import com.jietu.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sun.net.www.protocol.http.AuthCache;

/**
 * web mvc 配置
 * @author: 印修河
 * @date: 2018/4/9 17:03
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LogInterceptor logInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;
    @Value("${upload.root.path}")
    private String uploadRootPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        //日志拦截器
        registry.addInterceptor(logInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/error", "/upload/**");
        //权限拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/error", "/upload/**", "/customer/authorization", "/wxpay/payResultNotice");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + uploadRootPath);
        registry.addResourceHandler("/upload/static/**").addResourceLocations("classpath:/static/");

    }
}
