package com.tajohnson.projectmanager.controllers.project;

import com.tajohnson.projectmanager.models.project.Project;
import com.tajohnson.projectmanager.models.project.Task;
import com.tajohnson.projectmanager.models.user.User;
import com.tajohnson.projectmanager.services.project.ProjectService;
import com.tajohnson.projectmanager.services.project.TaskService;
import com.tajohnson.projectmanager.services.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @Autowired
  private UserService userService;

  @Autowired
  private TaskService taskService;

  //  =============== GET ROUTES ===============

  // *** DASHBOARD ROUTE ***
  @GetMapping("/dashboard")
  public String dashboard(HttpSession session, Model model) {
    if (session.getAttribute("userId") == null) {
      return "redirect:/logout";
    }

    Long userId = (Long) session.getAttribute("userId");
    if (!userService.isValidId(userId)) {
      return "redirect:/logout";
    }
    model.addAttribute("userId", userId);
    String userName = userService.getUserById(userId).getUserName();
    model.addAttribute("userName", userName);

    model.addAttribute(
      "projectsNotJoined",
      projectService.getProjectsNotJoined(userId)
    );
    model.addAttribute(
      "projectsJoined",
      userService.getUserById(userId).getProjects()
    );

    return "/project/dashboard.jsp";
  }

  // *** DISPLAY PROJECT FORM ***
  @GetMapping("/projects/new/form")
  public String displayProjectForm(
    @ModelAttribute("project") Project project,
    HttpSession session,
    Model model
  ) {
    if (session.getAttribute("userId") == null) {
      return "redirect:/logout";
    }

    Long userId = (Long) session.getAttribute("userId");
    if (!userService.isValidId(userId)) {
      return "redirect:/logout";
    }
    model.addAttribute("userId", userId);

    return "project/projectForm.jsp";
  }

  // *** VIEW PROJECT ***
  @GetMapping("/projects/{id}")
  public String viewProject(
    @PathVariable("id") Long id,
    HttpSession session,
    Model model
  ) {
    if (session.getAttribute("userId") == null) {
      return "redirect:/logout";
    }

    Long userId = (Long) session.getAttribute("userId");
    if (!userService.isValidId(userId)) {
      return "redirect:/logout";
    }

    model.addAttribute("project", projectService.getProjectById(id));
    model.addAttribute("userId", userId);

    return "project/viewProject.jsp";
  }

  /// *** DISPLAY EDIT FORM ***
  @GetMapping("/projects/edit/{id}")
  public String displayEditProjectForm(
    @PathVariable("id") Long id,
    HttpSession session,
    Model model
  ) {
    if (session.getAttribute("userId") == null) {
      return "redirect:/logout";
    }

    Long userId = (Long) session.getAttribute("userId");
    if (!userService.isValidId(userId)) {
      return "redirect:/logout";
    }

    Project project = projectService.getProjectById(id);
    model.addAttribute("project", project);
    model.addAttribute("userId", userId);

    return "project/projectEditForm.jsp";
  }

  // *** VIEW TASKS ***
  @GetMapping("/projects/{id}/tasks")
  public String viewTasks(
    @PathVariable("id") Long id,
    @ModelAttribute("task") Task task,
    HttpSession session,
    Model model
  ) {
    if (session.getAttribute("userId") == null) {
      return "redirect:/logout";
    }

    Long userId = (Long) session.getAttribute("userId");
    if (!userService.isValidId(userId)) {
      return "redirect:/logout";
    }

    model.addAttribute("project", projectService.getProjectById(id));
    model.addAttribute("user", userService.getUserById(userId));

    return "/project/viewTasks.jsp";
  }

  //  =============== POST ROUTES ===============

  // *** CREATE NEW PROJECT ***
  @PostMapping("/projects/new/create")
  public String createProject(
    @Valid @ModelAttribute("project") Project project,
    BindingResult result,
    HttpSession session,
    Model model
  ) {
    Long userId = (Long) session.getAttribute("userId");

    if (result.hasErrors()) {
      model.addAttribute("project", project);
      model.addAttribute("userId", userId);
      return "project/projectForm.jsp";
    }
    Project newProject = projectService.createProject(project);
    projectService.addUserToTeam(newProject.getId(), userId);

    return String.format("redirect:/projects/%d", newProject.getId());
  }

  // *** ADD USER TO PROJECT TEAM ***
  @PostMapping("/projects/{projectId}/join")
  public String addUserToProject(
    HttpSession session,
    @PathVariable("projectId") Long id
  ) {
    Long userId = (Long) session.getAttribute("userId");
    projectService.addUserToTeam(id, userId);

    return "redirect:/dashboard";
  }

  // *** REMOVE USER FROM PROJECT TEAM ***
  @PostMapping("/projects/{projectId}/leave")
  public String removeUserFromProject(
    HttpSession session,
    @PathVariable("projectId") Long id
  ) {
    Long userId = (Long) session.getAttribute("userId");
    projectService.removeUserFromTeam(id, userId);

    return "redirect:/dashboard";
  }

  // *** CREATE TASK ***
  @PostMapping("/projects/{projectId}/tasks/create")
  public String createTask(
    @PathVariable("projectId") Long id,
    @Valid @ModelAttribute("task") Task task,
    BindingResult result,
    HttpSession session,
    Model model
  ) {
    if (result.hasErrors()) {
      Long userId = (Long) session.getAttribute("userId");
      model.addAttribute("project", projectService.getProjectById(id));
      model.addAttribute("user", userService.getUserById(userId));
      model.addAttribute("task", task);

      return "project/viewTasks.jsp";
    }
    Project project = projectService.getProjectById(id);
    Task newTask = taskService.createTask(task);
    project.getTasks().add(newTask);
    projectService.updateProject(project);

    return String.format("redirect:/projects/%d/tasks", project.getId());
  }

  //  =============== PUT ROUTES ===============

  // *** UPDATE PROJECT ***
  @PutMapping("/projects/update/{id}")
  public String updateProject(
    @Valid @ModelAttribute("project") Project project,
    BindingResult result,
    HttpSession session,
    Model model
  ) {
    Long userId = (Long) session.getAttribute("userId");

    if (result.hasErrors()) {
      model.addAttribute("project", project);
      model.addAttribute("userId", userId);
      return "project/projectEditForm.jsp";
    }
    Project updatedProject = projectService.updateProject(project);
    projectService.addUserToTeam(updatedProject.getId(), userId);

    return String.format("redirect:/projects/%d", project.getId());
  }

  //  =============== PUT ROUTES ===============

  // *** DELETE PROJECT ***
  @DeleteMapping("/projects/delete/{id}")
  public String deleteProject(@PathVariable("id") Long id) {
    Project project = projectService.getProjectById(id);
    for (User user : project.getTeam()) {
      user.getProjects().remove(project);
      userService.updateUser(user);
    }
    projectService.deleteProjectById(id);

    return "redirect:/dashboard";
  }
}