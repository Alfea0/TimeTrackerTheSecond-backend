package com.TimeTrackerTheSecond_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeTrackerTheSecond_backend.repository.TaskRepository;
import com.TimeTrackerTheSecond_backend.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

     // Denna metoden används för att checka in på en uppgift
     public Task checkIn(String id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCheckInTime(LocalDateTime.now());
            task.setActive(true); 
            return taskRepository.save(task);
        }
        return null;
    }

    // Denna metoden används för att checka ut från en uppgift
    public Task checkOut(String id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCheckOutTime(LocalDateTime.now());
            task.setActive(false); 
            return taskRepository.save(task);
        }
        return null;
    }
}
