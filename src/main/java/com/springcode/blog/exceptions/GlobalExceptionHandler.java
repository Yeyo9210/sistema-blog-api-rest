package com.springcode.blog.exceptions;

import com.springcode.blog.dao.ErrorDetalles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
Esta anotacion va a permitir manejar excepciones handler
Captura todas las excepciones de nuestra aplicacion
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    /*
    Se encarga de manejar las excepciones que se hayan detallado
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),resourceNotFoundException.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetalles> manejarBlogAppException(BlogAppException blogAppException,WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),blogAppException.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalExceptin(Exception exception,WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    Nos ayudara a ver los errores de las validaciones
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> errores = new HashMap<>();
        /*
        Nos obtiene todos los errores
         */
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();

            errores.put(nombreCampo,mensaje);

        });

        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }
}
