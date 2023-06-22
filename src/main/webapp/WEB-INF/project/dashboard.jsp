<%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 6/21/2023
  Time: 3:20 PM
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
  <h1 class="mb-3">Welcome, ${userName}!</h1>
  <div class="d-flex justify-content-between align-items-end mb-3">
    <p>Projects you can join:</p>
    <a href="/projects/new/form" class="btn btn-outline-success" role="button">
      + New Project
    </a>
  </div>
  <div class="d-flex justify-content-center">
    <div class="card w-100 mb-5">
      <div class="card-body">
        <table class="table table-striped table-bordered">
          <thead>
          <tr>
            <th scope="col">Project Name</th>
            <th scope="col">Project Lead</th>
            <th scope="col">Due Date</th>
            <th scope="col">Actions</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="project" items="${projectsNotJoined}">
            <tr>
              <td>
                <a href="/projects/${project.id}">${project.title}</a>
              </td>
              <td>
                  ${project.lead.userName}
              </td>
              <td>
                <fmt:formatDate value="${project.dueDate}"/>
              </td>
              <td>
                <div class="d-flex justify-content-center">
                  <form action="/projects/${project.id}/join" method="post">
                    <button type="submit" class="btn btn-primary">
                      Join Team
                    </button>
                  </form>
                </div>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <hr class="mb-5">
  <p>Projects you're a part of:</p>
  <div class="d-flex justify-content-center">
    <div class="card w-100">
      <div class="card-body">
        <table class="table table-striped table-bordered">
          <thead>
          <tr>
            <th scope="col">Project Name</th>
            <th scope="col">Project Lead</th>
            <th scope="col">Due Date</th>
            <th scope="col">Actions</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="project" items="${projectsJoined}">
            <tr>
              <td>
                <a href="/projects/${project.id}">${project.title}</a>
              </td>
              <td>
                  ${project.lead.userName}
              </td>
              <td>
                <fmt:formatDate value="${project.dueDate}"/>
              </td>
              <td>
                <c:choose>
                  <c:when test="${project.lead.id == userId}">
                    <div class="d-flex justify-content-center">
                      <form action="/projects/delete/${project.id}" method="post">
                        <div class="btn-group" role="group">
                          <a href="/projects/edit/${project.id}" class="btn btn-warning">
                            Edit
                          </a>
                          <input type="hidden" name="_method" value="delete">
                          <button type="submit" class="btn btn-danger">Delete</button>
                        </div>
                      </form>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="d-flex justify-content-center">
                      <form action="/projects/${project.id}/leave" method="post">
                        <button type="submit" class="btn btn-outline-danger">
                          Leave Team
                        </button>
                      </form>
                    </div>
                  </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>