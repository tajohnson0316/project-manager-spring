package com.tajohnson.projectmanager.services.project;

import com.tajohnson.projectmanager.models.project.Task;
import com.tajohnson.projectmanager.repositories.project.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  @Autowired
  private TaskRepository taskRepository;

  public Task createTask(Task task) {
    return taskRepository.save(task);
  }
}