package com.tajohnson.projectmanager.services.project;

import com.tajohnson.projectmanager.models.project.Project;
import com.tajohnson.projectmanager.models.user.User;
import com.tajohnson.projectmanager.repositories.project.ProjectRepository;
import com.tajohnson.projectmanager.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private UserService userService;

  public List<Project> allProjects() {
    return this.projectRepository.findAll();
  }

  public Project getProjectById(Long id) {
    Optional<Project> optional = projectRepository.findById(id);

    return optional.orElse(null);
  }

  public List<Project> getProjectsNotJoined(Long userId) {
    User user = userService.getUserById(userId);
    List<Project> unjoinedProjects = allProjects();

    unjoinedProjects.removeIf(project -> project.getTeam().contains(user));
    return unjoinedProjects;
  }

  public Project createProject(Project project) {
    project.setTeam(new ArrayList<>());
    return projectRepository.save(project);
  }

  public Project updateProject(Project project) {
    List<User> allUsers = userService.findAll();
    List<User> usersInTeam = new ArrayList<>();

    for (User user : allUsers) {
      if (user.getProjects().contains(project)) {
        usersInTeam.add(user);
      }
    }
    project.setTeam(usersInTeam);
    return projectRepository.save(project);
  }

  public Project addUserToTeam(Long projectId, Long userId) {
    User user = userService.getUserById(userId);
    Project project = getProjectById(projectId);

    project.getTeam().add(user);

    return projectRepository.save(project);
  }

  public Project removeUserFromTeam(Long projectId, Long userId) {
    User user = userService.getUserById(userId);
    Project project = getProjectById(projectId);

    project.getTeam().remove(user);

    return projectRepository.save(project);
  }

  public void deleteProjectById(Long id) {
    projectRepository.deleteById(id);
  }
}