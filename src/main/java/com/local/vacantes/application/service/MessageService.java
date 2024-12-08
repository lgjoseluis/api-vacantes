package com.local.vacantes.application.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MessageService {
	private final MessageSource messageSource;
	private final LocaleResolver localeResolver;
    private final HttpServletRequest request;

    public MessageService(MessageSource messageSource, LocaleResolver localeResolver, HttpServletRequest request) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
        this.request = request;
    }

    /**
     * Obtiene un mensaje desde el archivo de properties.
     *
     * @param key  La clave del mensaje.
     * @param locale Idioma
     * @param args Argumentos opcionales para el mensaje.
     * @return Texto del mensaje.
     */
    public String getMessage(String key, Object... args) {
    	Locale locale = localeResolver.resolveLocale(request);
    	System.out.println(Locale.getDefault()+ " - " + locale);
        return messageSource.getMessage(key, args, locale);
    }
}
