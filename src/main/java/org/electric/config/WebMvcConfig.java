package org.electric.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO SpringBoot的内置Tomcat配置扩展
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.servlet.upload.drive.letter}")
    protected String driveLetter;

    @Value("${spring.servlet.upload.base-path}")
    protected String basePath;

    /**
     * @description Tomcat的虚拟路径映射，用于图片上传后的页面显示
     * 将本地路径c:/xxx/地址映射为/xxx/
     * @param registry
     * @return void
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+basePath+"/**").addResourceLocations("file:"+driveLetter+":/"+basePath+"/");
    }
}
