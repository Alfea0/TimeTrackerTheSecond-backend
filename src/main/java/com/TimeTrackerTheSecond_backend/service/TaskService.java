package com.TimeTrackerTheSecond_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeTrackerTheSecond_backend.repository.TaskRepository;
import com.TimeTrackerTheSecond_backend.model.Task;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(String id, Task task) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            return taskRepository.save(task);
        }
        return null;
    }
}
