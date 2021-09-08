<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bright.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css"/>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <title>分页导航栏</title>
</head>
<body>
<div>
  <nav aria-label="..." style="text-align:center;">
    <ul class="pagination">
      <li>
        <a href="javascript:ajaxSplit(${info.prePage})" aria-label="Previous">
          <span aria-hidden="true">«</span>
        </a>
      </li>
      <c:forEach begin="1" end="${info.pages}" var="i">
        <c:if test="${info.pageNum==i}">
          <li>
            <a href="javascript:ajaxSplit(${i})" style="background-color: grey">
                ${i}
            </a>
          </li>
        </c:if>
        <c:if test="${info.pageNum!=i}">
          <li>
            <a href="javascript:ajaxSplit(${i})">${i}</a>
          </li>
        </c:if>
      </c:forEach>

      <li>
        <a href="javascript:ajaxSplit(${info.nextPage})" aria-label="Next">
          <span aria-hidden="true">»</span>
        </a>
      </li>
      <li style=" margin-left:150px;color: #0e90d2;height: 35px; line-height: 35px;">
        总共
        <font  style="color:orange;">${info.pages}</font>
        <c:if test="${info.pageNum!=0}">
          当前
          <font  style="color:orange;">${info.pageNum}</font>
        </c:if>
        <c:if test="${info.pageNum==0}">
          当前
          <font style="color:orange;">1</font>;页
        </c:if>
      </li>
    </ul>
  </nav>
</div>
</div>
</body>
</html>
