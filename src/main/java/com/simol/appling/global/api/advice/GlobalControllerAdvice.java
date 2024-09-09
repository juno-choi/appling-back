package com.simol.appling.global.api.advice;

import com.simol.appling.global.api.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ResponseError> errors = e.getBindingResult().getAllErrors().stream()
                .map(ResponseError::from)
                .collect(Collectors.toList());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = createProblemDetailFrom(httpStatus, "잘못된 입력입니다.", errors);
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    private static ProblemDetail createProblemDetailFrom(HttpStatus httpStatus, String detail, List<ResponseError> errors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, detail);
        problemDetail.setType(URI.create("/swagger-ui/index.html"));
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }
}
