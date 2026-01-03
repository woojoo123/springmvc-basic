package com.example.auth;

/**
 * 로그인한 사용자의 정보를 담는 클래스
 * 
 * 세션에 저장할 때 이 객체를 사용합니다.
 * username과 role을 하나의 객체로 묶어서 관리합니다.
 * 
 * 사용 예시:
 *   LoginUser user = new LoginUser("woojoo", "ADMIN");
 *   session.setAttribute(SessionConst.LOGIN_USER, user);
 *   
 *   나중에 꺼낼 때:
 *   LoginUser user = session.getAttribute(SessionConst.LOGIN_USER);
 *   String username = user.getUsername();  // "woojoo"
 *   String role = user.getRole();          // "ADMIN"
 */
public class LoginUser {
    /** 사용자 이름 */
    private final String username;
    
    /** 사용자 권한: "USER" (일반 사용자) 또는 "ADMIN" (관리자) */
    private final String role;

    /**
     * 로그인 사용자 정보 생성
     * @param username 사용자 이름
     * @param role 사용자 권한 ("USER" 또는 "ADMIN")
     */
    public LoginUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    /** 사용자 이름 반환 */
    public String getUsername() { return username; }
    
    /** 사용자 권한 반환 ("USER" 또는 "ADMIN") */
    public String getRole() { return role; }
}