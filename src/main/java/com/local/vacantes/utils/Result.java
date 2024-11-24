package com.local.vacantes.utils;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
	private T data;
	
    private String message;
    
    private boolean success;  
    
    private List<String> errors;
    
    private Result(T data, String message, boolean success, List<String> errors) {
        this.data = data;
        this.message = message;
        this.success = success;
        this.errors = errors;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, "Success", true, new ArrayList<>());
    }
    
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(data, message, true, new ArrayList<>());
    }

    public static <T> Result<T> failure(String message, List<String> errors) {
        return new Result<>(null, message, false, errors);
    }
    
    public T getData() { 
    	return data; 
    }
    
    public String getMessage() { 
    	return message; 
    }
    
    public boolean isSuccess() { 
    	return success; 
    }
    
    public List<String> getErrors(){
    	return errors;
    }
}
