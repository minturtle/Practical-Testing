package sample.cafekiosk.spring.controller;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class})
    public ErrorResponse bindException(BindException e){
        final String detailMessage = Arrays.toString(
                e.getBindingResult().getAllErrors().stream().map(o -> o.getDefaultMessage()).toArray()
        );

        final ErrorResponse errResp = ErrorResponse.builder()
                .message(e.getMessage())
                .detail(detailMessage)
                .build();

        return errResp;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class ErrorResponse{
        private String message;
        private String detail;
    }
}
