package com.TimeTrackerTheSecond_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TimeTrackerTheSecond_backend.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
}
