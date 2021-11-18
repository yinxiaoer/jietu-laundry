package com.jietu.admin.conf;

import com.jietu.admin.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc 配置
 * @author: 印修河
 * @date: 2018/4/9 17:03
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LogInterceptor logInterceptor;
    /**
     * 上传根路径
     */
    @Value("${upload.root.path}")
    private String uploadRootPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //指定静态资源路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + uploadRootPath);
        registry.addResourceHandler("/upload/static/**").addResourceLocations("classpath:/static/");
    }
}
