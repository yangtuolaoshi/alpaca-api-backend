package icu.ytlsnb.alpacaapibackend.config;

import icu.ytlsnb.alpacaapibackend.aop.ProjectInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringMVCSupport extends WebMvcConfigurationSupport {
    @Resource
    private ProjectInterceptor projectInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor([拦截器对象]).addPathPatterns("[请求路径]");
        registry.addInterceptor(projectInterceptor).addPathPatterns(
                "/interfaceInfo",
                "/interfaceInfo/*"
        ).excludePathPatterns(
                "/user/login",
                "/user/reg"
        );
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 当访问/pages目录下的资源时，走/pages目录下的内容访问
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");// 放行页面
        // 这句话的意思是，当发送了形如/pages/**的请求，就访问/page/目录下的资源
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");// 放行样式表
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");// 放行js
        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");// 放行插件
    }
}
