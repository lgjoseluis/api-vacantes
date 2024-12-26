package com.local.vacantes.infrastructure.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.local.vacantes.infrastructure.api.interceptor.MediaTypeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	private final MediaTypeInterceptor mediaTypeInterceptor;
	
	public WebMvcConfig(MediaTypeInterceptor mediaTypeInterceptor) {
        this.mediaTypeInterceptor = mediaTypeInterceptor;
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mediaTypeInterceptor)
                .addPathPatterns("/**"); // Aplica a todas las rutas. Ajusta según tu necesidad.
    }
}
