<%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 6/21/2023
  Time: 6:54 PM
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
    <%-- TODO: NAVBAR BRAND --%>
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
  <div class="card w-100 mb-3">
    <div class="card-header text-center fs-3">
      ${project.title}
    </div>
    <div class="card-body">
      <div class="d-flex justify-content-start gap-3 mb-3">
        <span class="fw-bold">Description:</span>
        <span>${project.description}</span>
      </div>
      <div class="d-flex justify-content-start gap-3 mb-3">
        <span class="fw-bold">Due Date:</span>
        <span><fmt:formatDate value="${project.dueDate}"/></span>
      </div>
    </div>
  </div>
  <a href="/projects/${project.id}/tasks">View Tasks</a>
  <c:if test="${project.lead.id == userId}">
    <div class="d-flex justify-content-end mb-5">
      <form action="/projects/delete/${project.id}" method="post">
        <input type="hidden" name="_method" value="delete">
        <button type="submit" class="btn btn-danger">Delete Project</button>
      </form>
    </div>
  </c:if>
</div>
</body>
</html>