<%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 6/21/2023
  Time: 4:10 PM
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
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a href="/dashboard" class="navbar-brand">
      Application
    </a>
    <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a href="/dashboard" class="nav-link">
            Home
          </a>
        </li>
      </ul>
      <a href="/logout" class="btn btn-outline-danger" role="button">
        Log Out
      </a>
    </div>
  </div>
</nav>
<div class="container p-5">
  
  <h1>Create a Project</h1>
  <form:form action="/projects/new/create" method="post" modelAttribute="project">
    <form:input type="hidden" path="lead" value="${userId}"/>
    <div class="mb-3">
      <form:label path="title" class="form-label">
        Project Title:
      </form:label>
      <form:input path="title" class="form-control"/>
      <p class="text-danger">
        <form:errors path="title"/>
      </p>
    </div>
    <div class="mb-3">
      <form:label path="description" class="form-label">
        Project Description:
      </form:label>
      <form:textarea path="description" class="form-control"/>
      <p class="text-danger">
        <form:errors path="description"/>
      </p>
    </div>
    <div class="mb-3">
      <form:label path="dueDate" class="form-label">
        Due Date:
      </form:label>
      <form:input type="date" path="dueDate" class="form-control"/>
      <p class="text-danger">
        <form:errors path="dueDate"/>
      </p>
    </div>
    <div class="d-flex justify-content-end gap-3">
      <a href="/dashboard" class="btn btn-danger" role="button">Cancel</a>
      <button type="submit" class="btn btn-primary">Confirm</button>
    </div>
  </form:form>
</div>
</body>
</html>