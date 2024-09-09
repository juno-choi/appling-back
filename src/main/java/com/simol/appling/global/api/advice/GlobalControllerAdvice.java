package com.simol.appling.global.api.advice;

import com.simol.appling.global.api.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private ProblemDetail createProblemDetailFrom(HttpStatus httpStatus, String detail, List<ResponseError> errors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, detail);
        problemDetail.setType(URI.create("/swagger-ui/index.html"));
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ResponseError> errors = e.getBindingResult().getAllErrors().stream()
                .map(ResponseError::from)
                .collect(Collectors.toList());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = createProblemDetailFrom(httpStatus, "잘못된 입력입니다.", errors);
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<ResponseError> errors = List.of(ResponseError.from("", e.getMessage()));
        ProblemDetail problemDetail = createProblemDetailFrom(httpStatus, httpStatus.getReasonPhrase(), errors);
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }
}
