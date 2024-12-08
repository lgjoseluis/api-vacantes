package com.local.vacantes.infrastructure.configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;

import com.local.vacantes.utils.ProblemDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Manejo de EntityNotFoundException, lanzada si no se encuentra un recurso
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
    	logger.error("EntityNotFoundException: {}", ex.getMessage());
    	
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request, "Recurso no encontrado");
    }

    // Manejo de IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
    	logger.warn("IllegalArgumentException: {}", ex.getMessage());
    	
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request, "Argumento inválido");
    }
    
    //NoResourceFoundException
    
    @Override    
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
    		MethodArgumentNotValidException ex, 
            HttpHeaders headers, 
            HttpStatusCode status, 
            WebRequest request) {

        List<String> validationErrors = ex.getBindingResult()
        		.getFieldErrors()
        		.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ProblemDetails apiError = new ProblemDetails(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Error en los datos de entrada",
                request.getDescription(false),
                validationErrors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {    	
        String error = ex.getName() + " debe ser de tipo " + ex.getRequiredType().getName();
        
        logger.error("{}: {}", ex.getName(), ex.getMessage(), ex);

        ProblemDetails apiError = new ProblemDetails(
        		HttpStatus.BAD_REQUEST.value(), 
        		HttpStatus.BAD_REQUEST.getReasonPhrase(), 
        		error, 
        		request.getDescription(false), 
        		Collections.singletonList(ex.getLocalizedMessage()));
        
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    //@ExceptionHandler(HttpMessageNotReadableException.class)
    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    	String error = "Invalid JSON or request body"; //ex.getMessage();
    	
    	ProblemDetails apiError = new ProblemDetails(
    			HttpStatus.BAD_REQUEST.value(), 
        		HttpStatus.BAD_REQUEST.getReasonPhrase(),
        		error,
        		request.getDescription(false), 
        		Collections.singletonList(ex.getLocalizedMessage()));
    	
    	return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    
    // Manejo de excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleAllExceptions(Exception ex, WebRequest request) {
    	logger.error("Exception-error inesperado: {}", ex.getMessage(), ex);
    	
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request, "Error interno en el servidor");
    }

    // Método helper para construir la respuesta de error
    private ResponseEntity<ProblemDetails> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request, String message) {
    	List<String> details = Collections.singletonList(ex.getMessage());
        ProblemDetails apiError = new ProblemDetails(status.value(), status.getReasonPhrase(), message, request.getDescription(false), details);
        return new ResponseEntity<>(apiError, status);
    }
}
