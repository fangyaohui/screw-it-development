package com.fang.screw.communal.config;

import com.fang.screw.communal.Interceptor.CurrentUserInterceptor;
import com.fang.screw.communal.component.CurrentUserArgumentResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @FileName WebConfig
 * @Description 在Spring的配置类中注册自定义的参数解析器
 * @Author yaoHui
 * @date 2024-08-02
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    @Resource
    private CurrentUserInterceptor currentUserInterceptor;

    public WebConfig(CurrentUserArgumentResolver currentUserArgumentResolver) {
        this.currentUserArgumentResolver = currentUserArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(currentUserInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/upm/login","/upm/register");
    }
}
