package com.example.api;

import java.time.Instant;

public class ErrorResponse {
    private final String code;      // 에러 코드(규칙)
    private final String message;   // 사람이 읽는 메시지
    private final int status;       // HTTP status
    private final String path;      // 요청 경로
    private final String timestamp; // 발생 시각

    public ErrorResponse(String code, String message, int status, String path) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = Instant.now().toString();
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public String getPath() { return path; }
    public String getTimestamp() { return timestamp; }
}
