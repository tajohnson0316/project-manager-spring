package com.tajohnson.projectmanager.models.project;

import com.tajohnson.projectmanager.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Project title is required")
  @Size(min = 3, message = "Minimum of 3 characters required")
  private String title;

  @NotBlank(message = "Project description is required")
  @Size(min = 3, message = "Minimum of 3 characters required")
  private String description;

  @NotNull(message = "Project due date is required")
  @Future(message = "Due date must be later than present-day")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dueDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User lead;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(
    name = "users_projects",
    joinColumns = @JoinColumn(name = "project_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> team;

  @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
  private List<Task> tasks;

  @Column(updatable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdAt;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updatedAt;

  public Project() {
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = new Date();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public @NotNull Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(@NotNull Date dueDate) {
    this.dueDate = dueDate;
  }

  public User getLead() {
    return lead;
  }

  public void setLead(User lead) {
    this.lead = lead;
  }

  public List<User> getTeam() {
    return team;
  }

  public void setTeam(List<User> team) {
    this.team = team;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}