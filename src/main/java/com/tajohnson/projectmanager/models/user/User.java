package com.tajohnson.projectmanager.models.user;

import com.tajohnson.projectmanager.models.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Username is required!")
  @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
  private String userName;

  @Column(unique = true)
  @NotBlank(message = "Email is required!")
  @Email(message = "Please enter a valid email")
  private String email;

  @NotBlank(message = "Password is required!")
  @Size(message = "Password must be between 8 and 128 characters")
  private String password;

  @Transient
  @NotBlank(message = "Password confirmation is required!")
  @Size(min = 8, max = 128, message = "Passwords must match")
  private String confirmPassword;

  @OneToMany(mappedBy = "lead", fetch = FetchType.LAZY)
  private List<Project> leadProjects;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "users_projects",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "project_id")
  )
  private List<Project> projects;


  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public List<Project> getLeadProjects() {
    return leadProjects;
  }

  public void setLeadProjects(List<Project> leadProjects) {
    this.leadProjects = leadProjects;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
}