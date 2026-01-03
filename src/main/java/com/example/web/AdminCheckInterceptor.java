package com.example.web;

import com.example.api.ErrorResponse;
import com.example.auth.LoginUser;
import com.example.auth.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminCheckInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 요청이 Controller에 도달하기 전에 실행되는 메서드
     * 관리자 권한을 체크합니다.
     * 
     * 동작 과정:
     * 1. 요청이 들어옴 (예: GET /admin/dashboard)
     * 2. 이 메서드가 먼저 실행됨 (Interceptor)
     * 3. 세션에서 로그인 정보 확인
     * 4. 관리자(ADMIN)면 → return true (Controller로 진행)
     * 5. 일반 사용자(USER)거나 로그인 안 했으면 → return false (403 에러)
     * 
     * @return true: 관리자 OK, Controller 실행 / false: 권한 없음, 차단
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 이 사용자의 세션 가져오기
        HttpSession session = request.getSession(false);
        
        // 2. 세션에서 로그인 정보 꺼내기
        //    - 이름표 "LOGIN_USER"로 찾기
        LoginUser user = (session == null) ? null : (LoginUser) session.getAttribute(SessionConst.LOGIN_USER);

        // 3. 관리자인지 확인
        //    - user != null: 로그인했는지 확인
        //    - "ADMIN".equals(user.getRole()): 권한이 ADMIN인지 확인
        if (user != null && "ADMIN".equals(user.getRole())) {
            return true;  // 관리자 OK → Controller로 진행
        }

        // 4. 권한 없음 처리
        //    - API 요청이면: JSON 형식으로 에러 응답
        //    - 화면 요청이면: 403 에러 페이지
        if (isApi(request)) {
            // API 요청: JSON 형식으로 에러 응답
            ErrorResponse body = new ErrorResponse(
                    "FORBIDDEN",
                    "관리자 권한이 필요합니다.",
                    HttpStatus.FORBIDDEN.value(),
                    request.getRequestURI()
            );
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(body));
            return false;  // Controller 실행 안 함
        }

        // 화면 요청: 403 에러 페이지
        response.sendError(HttpStatus.FORBIDDEN.value(), "관리자 권한이 필요합니다.");
        return false;  // Controller 실행 안 함
    }

    private boolean isApi(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return accept != null && accept.contains("application/json");
    }
}
