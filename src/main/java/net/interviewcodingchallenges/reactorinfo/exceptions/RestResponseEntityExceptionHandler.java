package net.interviewcodingchallenges.reactorinfo.exceptions;


import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler({NotFoundException.class, EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler(ValueNotVerifiedException.class)
    protected ResponseEntity<Object> handleNotVerified(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), 
          new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
    
    @ExceptionHandler({ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatusException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleSqlException(Throwable ex, WebRequest request) {
    	while(ex.getCause() != null) {
    		ex = ex.getCause();
    	}
        return handleExceptionInternal((Exception)ex, ex.getMessage(), 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    @ExceptionHandler(StaleObjectStateException.class)
    protected ResponseEntity<Object> handleStaleObjectException(Throwable ex, WebRequest request) {
    	while(ex.getCause() != null) {
    		ex = ex.getCause();
    	}
        return handleExceptionInternal((Exception)ex, ex.getMessage(), 
          new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
    }

}
