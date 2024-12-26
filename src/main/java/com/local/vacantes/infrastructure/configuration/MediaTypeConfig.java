package com.local.vacantes.infrastructure.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaTypeConfig {
	@Value("${media.type.v1}")
    private String v1MediaType;

    @Value("${media.type.v2}")
    private String v2MediaType;

    public String getV1MediaType() {
        return v1MediaType;
    }

    public String getV2MediaType() {
        return v2MediaType;
    }
    
    public List<String> getSupportedMediaTypes() {
        return List.of(v1MediaType, v2MediaType);
    }
}
