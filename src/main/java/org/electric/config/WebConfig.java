package org.electric.config;

/**
 * @version 1.0
 * @description //TODO
 **/
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new org.electric.config.LoginInterceptor())
                //所有的admin路径都不能越过登录，直接操作后台
                .addPathPatterns("/admin/**")
                //排除路径admin
                .excludePathPatterns("/wx/")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/index")
                .excludePathPatterns("/admin/comm");
    }
}
