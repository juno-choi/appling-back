package com.simol.appling.global.api.advice;

import com.simol.appling.global.api.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice(basePackages = "com.simol.appling")
public class GlobalAdvice {

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> illegalArgumentException(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<ResponseError> errors = List.of(ResponseError.from("", e.getMessage()));
        ProblemDetail problemDetail = createProblemDetailFrom(httpStatus, httpStatus.getReasonPhrase(), errors);
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    private static ProblemDetail createProblemDetailFrom(HttpStatus httpStatus, String detail, List<ResponseError> errors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, detail);
        problemDetail.setType(URI.create("/swagger-ui/index.html"));
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }
}
