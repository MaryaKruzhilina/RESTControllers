package ru.maryKr.bootCrud.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserExceptionEntity> handleException(NoSuchUserException e) {
        UserExceptionEntity entity = new UserExceptionEntity();
        entity.setInfo(e.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<UserExceptionEntity> handleException(Exception e) {
        UserExceptionEntity entity = new UserExceptionEntity();
        entity.setInfo(e.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<UserExceptionEntity> handleException(NotUniqueEmail e) {
        UserExceptionEntity entity = new UserExceptionEntity();
        entity.setInfo(e.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.CONFLICT);
    }
}
