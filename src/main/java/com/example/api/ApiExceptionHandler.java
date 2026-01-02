package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    // 1) 우리가 의도적으로 던진 ApiException은 그대로 통일 포맷으로
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApi(ApiException e, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                e.getCode(),
                e.getMessage(),
                e.getStatus().value(),
                req.getRequestURI()
        );
        return ResponseEntity.status(e.getStatus()).body(body);
    }

    // 2) JSON 파싱 자체가 실패한 경우 (잘못된 JSON)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException e, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_JSON",
                "요청 본문(JSON) 형식이 올바르지 않습니다.",
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 3) 쿼리 파라미터 누락 같은 경우(참고용)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException e, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                "MISSING_PARAM",
                "필수 파라미터가 누락되었습니다: " + e.getParameterName(),
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 4) 마지막: 예상 못한 모든 예외는 500으로 통일
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(Exception e, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                "INTERNAL_ERROR",
                "서버 오류가 발생했습니다.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
