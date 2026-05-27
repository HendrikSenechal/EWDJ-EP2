package com.example.spring_boot_exceptions.config;

import com.example.spring_boot_exceptions.interceptor.MeasurementInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    //private final MeasurementInterceptor measurementInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(measurementInterceptor).addPathPatterns("/welcome");
    }
}
