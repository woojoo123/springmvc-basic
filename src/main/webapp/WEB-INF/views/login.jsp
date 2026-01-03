<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>Login</title>
</head>
<body>
  <h1>Login</h1>

  <form method="post" action="${pageContext.request.contextPath}/login">
    <input type="hidden" name="redirectURL" value="${param.redirectURL}" />

    <div>
      <label>Username</label><br/>
      <input type="text" name="username" />
    </div>

    <div style="margin-top: 8px;">
      <label>Password</label><br/>
      <input type="password" name="password" />
    </div>

    <div style="margin-top: 12px;">
      <button type="submit">로그인</button>
    </div>
  </form>
</body>
</html>
