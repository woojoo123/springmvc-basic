package com.example.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.auth.LoginUser;
import com.example.auth.SessionConst;

/**
 * 로그인/인증 관련 컨트롤러
 */
@Controller
public class AuthController {
    
    /**
     * 로그인 페이지 보여주기
     * GET /login
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";  // /WEB-INF/views/login.jsp
    }

    /**
     * 로그인 처리
     * POST /login
     * 
     * 동작 과정:
     * 1. 사용자가 로그인 폼에서 username, password 입력
     * 2. 이 메서드가 실행됨
     * 3. 권한 결정 (admin이면 ADMIN, 그 외 USER)
     * 4. 세션에 로그인 정보 저장 ← 여기가 핵심!
     * 5. 원래 가려던 페이지로 이동 (redirectUrl이 있으면) 또는 /posts/page로 이동
     * 
     * 세션 저장 설명:
     * - request.getSession(): 이 사용자의 세션 가져오기 (없으면 새로 생성)
     * - 각 사용자마다 별도의 세션이 있음 (세션ID로 구분)
     * - session.setAttribute(이름표, 내용): 세션에 저장
     *   예: session.setAttribute("LOGIN_USER", LoginUser 객체)
     * - 나중에 다른 페이지에서 session.getAttribute("LOGIN_USER")로 꺼낼 수 있음
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false) String redirectUrl,
                        HttpServletRequest request) {

        // 실습용 : admin이면 ADMIN, 그 외 USER
        // (비밀번호 검증은 생략)
        String role = "admin".equalsIgnoreCase(username) ? "ADMIN" : "USER";

        // 1. 이 사용자의 세션 가져오기
        //    - 각 사용자마다 별도의 세션이 있음 (세션ID로 구분)
        //    - 쿠키에 세션ID가 저장되어 있어서, 같은 사용자는 같은 세션을 사용
        HttpSession session = request.getSession();
        
        // 2. 세션에 로그인 정보 저장
        //    - 이름표: SessionConst.LOGIN_USER (= "LOGIN_USER")
        //    - 내용: LoginUser 객체 (username, role 포함)
        //    - 구조: 세션[세션ID]["LOGIN_USER"] = LoginUser 객체
        session.setAttribute(SessionConst.LOGIN_USER, new LoginUser(username, role));

        // 3. 원래 가려던 페이지가 있으면 그곳으로, 없으면 /posts/page로
        if (redirectUrl != null && !redirectUrl.isBlank()) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/posts/page";
        }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
            return "redirect:/login";
    }
}
