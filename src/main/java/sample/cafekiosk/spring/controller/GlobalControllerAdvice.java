package sample.cafekiosk.spring.controller;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorResponse> bindException(BindException e){
        final ErrorResponse errResp = ErrorResponse.builder()
                .message(e.getMessage())
                .detail(e.getBindingResult().toString())
                .build();

        return new ResponseEntity<>(errResp, HttpStatus.BAD_REQUEST);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorResponse{
        private String message;
        private String detail;
    }
}
