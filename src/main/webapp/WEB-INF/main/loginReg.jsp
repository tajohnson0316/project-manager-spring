<%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 6/20/2023
  Time: 5:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<html>
<head>
  <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
  <script type="text/javascript" src="/js/app.js"></script>
  
  <title>Title</title>
</head>
<body>
<div class="container p-5">
  <%-- TODO: APPLICATION HEADER --%>
  <div class="d-flex justify-content-apart gap-3 p-5">
    <!-- Registration card start -->
    <div class="card col-6 mb-3">
      <div class="card-body">
        <h2 class="text-success">Register</h2>
        <form:form action="/users/register" method="post" modelAttribute="newUser">
          <!-- Username -->
          <div class="mb-3">
            <form:label path="userName" class="form-label">
              Username:
            </form:label>
            <form:input path="userName" class="form-control"/>
            <p class="text-danger">
              <form:errors path="userName"/>
            </p>
          </div>
          <!-- Email -->
          <div class="mb-3">
            <form:label path="email" class="form-label">
              E-mail:
            </form:label>
            <form:input type="email" path="email" class="form-control"/>
            <p class="text-danger">
              <form:errors path="email"/>
            </p>
          </div>
          <!-- Password -->
          <div class="mb-3">
            <form:label path="password" class="form-label">
              Password:
            </form:label>
            <form:input type="password" path="password" class="form-control"/>
            <p class="text-danger">
              <form:errors path="password"/>
            </p>
          </div>
          <!-- Confirm Password -->
          <div class="mb-3">
            <form:label path="confirmPassword" class="form-label">
              Confirm password:
            </form:label>
            <form:input type="password" path="confirmPassword" class="form-control"/>
            <p class="text-danger">
              <form:errors path="confirmPassword"/>
            </p>
          </div>
          <button type="submit" class="btn btn-success">Register</button>
        </form:form>
      </div>
    </div>
    <!-- Login card start -->
    <div class="card col-4 mb-3 h-50">
      <div class="card-body">
        <h2 class="text-primary">Log In</h2>
        <form:form action="/users/login" method="post" modelAttribute="newLogin">
          <!-- Email -->
          <div class="mb-3">
            <form:label path="logEmail" class="form-label">
              E-mail:
            </form:label>
            <form:input type="email" path="logEmail" class="form-control"/>
            <p class="text-danger">
              <form:errors path="logEmail"/>
            </p>
          </div>
          <!-- Password -->
          <div class="mb-3">
            <form:label path="logPassword" class="form-label">
              Password:
            </form:label>
            <form:input type="password" path="logPassword" class="form-control"/>
            <p class="text-danger">
              <form:errors path="logPassword"/>
            </p>
          </div>
          <button type="submit" class="btn btn-primary">Log In</button>
        </form:form>
      </div>
    </div>
  </div>
</div>
</body>
</html>