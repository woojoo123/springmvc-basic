package com.example.auth;

/**
 * 세션에 저장할 때 사용하는 이름표(키)를 정의한 클래스
 * 
 * 왜 이렇게 만들었나요?
 * - 여러 파일에서 같은 이름표를 사용할 때 오타를 방지하기 위해
 * - 예: "LOGIN_USER" vs "loginUser" vs "LOGIN_USR" (오타!)
 * - SessionConst.LOGIN_USER를 쓰면 컴파일 타임에 오타 체크 가능
 * 
 * 사용 예시:
 * - session.setAttribute(SessionConst.LOGIN_USER, user);  // 저장
 * - LoginUser user = session.getAttribute(SessionConst.LOGIN_USER);  // 꺼내기
 */
public class SessionConst {
    /**
     * 로그인한 사용자 정보를 세션에 저장할 때 사용하는 이름표
     * 
     * 세션 구조:
     *   세션[세션ID]["LOGIN_USER"] = LoginUser 객체
     *   
     * 각 사용자마다 별도의 세션이 있으므로:
     *   - 사용자 A의 세션: "LOGIN_USER" → LoginUser("woojoo")
     *   - 사용자 B의 세션: "LOGIN_USER" → LoginUser("admin")
     *   같은 이름표를 써도 각자의 세션에 저장되므로 문제없음!
     */
    public static final String LOGIN_USER = "LOGIN_USER";
}