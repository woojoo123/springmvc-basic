<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.domain.Post" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>Posts</title>
</head>
<body>
  <h1>Posts</h1>

  <p>
    <a href="${pageContext.request.contextPath}/posts/new">새 글 작성</a>
  </p>

  <p>총 ${count}개</p>

  <%
    List<Post> items = (List<Post>) request.getAttribute("items");
  %>

  <table border="1" cellspacing="0" cellpadding="8">
    <thead>
      <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Content</th>
      </tr>
    </thead>
    <tbody>
      <%
        if (items != null) {
          for (Post p : items) {
      %>
        <tr>
          <td><%= p.getId() %></td>
          <td><%= p.getTitle() %></td>
          <td><%= p.getContent() %></td>
        </tr>
      <%
          }
        }
      %>
    </tbody>
  </table>
</body>
</html>
