package com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.model.Task;
import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.repository.TaskRepository;

import java.time.Duration;
import java.time.LocalDate;
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

     // En metod för att beräkna arbetad tid
     public long calculateWorkedMinutes(Task task) {
        if (task.getCheckInTime() != null && task.getCheckOutTime() != null) {
            Duration duration = Duration.between(task.getCheckInTime(), task.getCheckOutTime());
            return duration.toMinutes();  
        }
        return 0;
    }

    // En metod för att beräkna tiden över en vecka
    public long getTotalWorkedMinutesForWeek() {
        LocalDateTime startOfWeek = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1).atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);

        long totalMinutes = 0;
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
    
            if (task.getCheckInTime() != null && task.getCheckOutTime() != null &&
                (task.getCheckInTime().isAfter(startOfWeek) || task.getCheckInTime().isEqual(startOfWeek)) &&
                task.getCheckOutTime().isBefore(endOfWeek)) {
                totalMinutes += calculateWorkedMinutes(task);
            }
        }
        return totalMinutes; 
    }
}
