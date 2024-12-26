package com.local.vacantes.infrastructure.api.interceptor;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.local.vacantes.infrastructure.configuration.MediaTypeConfig;

@Component
public class MediaTypeInterceptor implements HandlerInterceptor{
	private final List<String> supportedMediaTypes;
	
	public MediaTypeInterceptor(MediaTypeConfig mediaTypeConfig) {
        this.supportedMediaTypes = mediaTypeConfig.getSupportedMediaTypes();
    }
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String acceptHeader = request.getHeader("Accept");

        if (acceptHeader == null || supportedMediaTypes.stream().noneMatch(acceptHeader::contains)) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().write("Unsupported media type. Supported types are: " + String.join(", ", supportedMediaTypes));
            return false;
        }

        return true;
    }
}
