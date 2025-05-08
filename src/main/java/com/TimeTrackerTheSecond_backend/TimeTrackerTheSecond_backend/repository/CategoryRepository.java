package com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
