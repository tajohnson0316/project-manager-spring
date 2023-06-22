<%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 6/22/2023
  Time: 1:37 AM
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
  <div class="d-flex justify-content-center">
    <div class="card w-100 mb-5">
      <div class="card-header text-center fs-3">
        ${project.title}
      </div>
      <div class="card-body">
        <h3 class="mb-3">Lead by: ${project.lead.userName}</h3>
        <p class="mb-3">Current tasks:</p>
        <ul class="list-group">
          <c:forEach var="task" items="${project.tasks}">
            <li class="list-group-item">
              <p class="fw-bold">
                Added by <span>${task.assignerName}</span>
                on <fmt:formatDate value="${task.createdAt}" type="both"/>
              </p>
                ${task.description}
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
  <c:if test="${project.team.contains(user)}">
    <hr class="mb-5">
    <div class="mb-3">
      <form:form action="/projects/${project.id}/tasks/create" method="post" modelAttribute="task">
        <form:input type="hidden" path="assignerName" value="${user.userName}"/>
        <form:input type="hidden" path="project" value="${project.id}"/>
        <form:label class="form-label" path="description">
          Add a task ticket for this team:
        </form:label>
        <form:textarea class="form-control" path="description"/>
        <p class="text-danger">
          <form:errors path="description"/>
        </p>
        <div class="d-flex justify-content-end">
          <button type="submit" class="btn btn-outline-success">
            Submit
          </button>
        </div>
      </form:form>
    </div>
  </c:if>
</div>
</body>
</html>