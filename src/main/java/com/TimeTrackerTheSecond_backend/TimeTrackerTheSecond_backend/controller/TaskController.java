package com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.model.Task;
import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.service.TaskService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    // Den här endpointen används när man checkar in på en uppgift
    @PostMapping("/checkin/{id}")
    public Task checkIn(@PathVariable String id) {
        return taskService.checkIn(id);
    }
    
    // Den här endpointen används när man checkar ut från en uppgift
    @PostMapping("/checkout/{id}")
    public Task checkOut(@PathVariable String id) {
        return taskService.checkOut(id);
    }

    // Den här endpointen visar statistiken för en vecka
    @GetMapping("/stats/week")
    public long getTotalWorkedMinutesForWeek() {
        return taskService.getTotalWorkedMinutesForWeek();
    }

}
