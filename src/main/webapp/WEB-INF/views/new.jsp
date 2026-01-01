<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>New Post</title>
</head>
<body>
  <h1>New Post</h1>

  <c:if test="${error}">
    <p style="color:red;">title과 content는 필수입니다.</p>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/posts/page">
    <div>
      <label>Title</label><br/>
      <input type="text" name="title" />
    </div>

    <div style="margin-top: 8px;">
      <label>Content</label><br/>
      <textarea name="content" rows="5" cols="40"></textarea>
    </div>

    <div style="margin-top: 12px;">
      <button type="submit">작성</button>
      <a href="${pageContext.request.contextPath}/posts/page">목록으로</a>
    </div>
  </form>
</body>
</html>
