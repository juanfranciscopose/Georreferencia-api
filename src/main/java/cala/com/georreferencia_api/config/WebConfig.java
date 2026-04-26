package cala.com.georreferencia_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cala.com.georreferencia_api.interceptors.LoggerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoggerInterceptor loggingInterceptor() {
        return new LoggerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor());
    }
}