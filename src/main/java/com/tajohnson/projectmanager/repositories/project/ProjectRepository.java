package com.tajohnson.projectmanager.repositories.project;

import com.tajohnson.projectmanager.models.project.Project;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
  @NotNull List<Project> findAll();
}