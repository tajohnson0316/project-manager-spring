package com.tajohnson.projectmanager.repositories.project;

import com.tajohnson.projectmanager.models.project.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}