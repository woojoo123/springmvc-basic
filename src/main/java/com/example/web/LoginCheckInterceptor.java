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

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 요청이 Controller에 도달하기 전에 실행되는 메서드
     * 로그인 여부를 체크합니다.
     * 
     * 동작 과정:
     * 1. 요청이 들어옴 (예: GET /posts/new)
     * 2. 이 메서드가 먼저 실행됨 (Interceptor)
     * 3. 세션에서 로그인 정보 확인
     * 4. 로그인했으면 → return true (Controller로 진행)
     * 5. 로그인 안 했으면 → return false (Controller 실행 안 함, 로그인 페이지로 보냄)
     * 
     * @return true: 로그인 OK, Controller 실행 / false: 로그인 안 함, 차단
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isPublicApi(request)) {
            return true;
        }

        // 1. 이 사용자의 세션 가져오기
        //    - request.getSession(false): 세션이 없으면 null 반환 (새로 만들지 않음)
        //    - 세션이 있으면 그 세션 반환
        HttpSession session = request.getSession(false);
        
        // 2. 세션에서 로그인 정보 꺼내기
        //    - 이름표 "LOGIN_USER"로 찾기
        //    - 있으면: LoginUser 객체
        //    - 없으면: null (로그인 안 함)
        //    - 각 사용자마다 별도의 세션이므로, 자신의 정보만 나옴
        LoginUser user = (session == null) ? null : (LoginUser) session.getAttribute(SessionConst.LOGIN_USER);

        // 3. 로그인했는지 확인
        if (user != null) {
            return true; // 로그인 OK → Controller로 진행
        }

        // 4. 로그인 안 했으면 처리
        //    - API 요청이면: JSON 에러 응답
        //    - 화면 요청이면: 로그인 페이지로 redirect
        if (isApiRequest(request)) {
            // API 요청: JSON 형식으로 에러 응답
            ErrorResponse body = new ErrorResponse(
                    "UNAUTHORIZED",
                    "로그인이 필요합니다.",
                    HttpStatus.UNAUTHORIZED.value(),
                    request.getRequestURI()
            );
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(body));
            return false;  // Controller 실행 안 함
        }

        // 화면 요청: 로그인 페이지로 redirect (+원래 가려던 URL 전달)
        // 예: /posts/new로 가려다가 로그인 페이지로 보내면,
        //     로그인 후 /posts/new로 자동 이동
        String redirectURL = request.getRequestURI();
        if (request.getQueryString() != null) {
            redirectURL += "?" + request.getQueryString();
        }
        response.sendRedirect(request.getContextPath() + "/login?redirectURL=" + java.net.URLEncoder.encode(redirectURL, "UTF-8"));
        return false;  // Controller 실행 안 함
    }

    // 컨텍스트 경로를 제거한 실제 경로를 구하는 메서드
    private String getPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        return uri.substring(ctx.length());
    }
    
    // /api/로 시작하면 API 요청
    private boolean isApiRequest(HttpServletRequest request) {
        String path = getPath(request);
        return path.startsWith("/api/");
    }

    private boolean isPublicApi(HttpServletRequest request) {
        String path = getPath(request);
        return "GET".equalsIgnoreCase(request.getMethod())
                && "/api/posts".equals(path);
    }
}
