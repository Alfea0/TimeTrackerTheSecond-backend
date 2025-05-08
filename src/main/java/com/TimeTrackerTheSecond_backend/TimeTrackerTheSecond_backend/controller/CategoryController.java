package com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.model.Category;
import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.service.CategoryService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategorys() {
        return categoryService.getAllCategorys();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable String id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    // Den här endpointen används när man checkar in på en uppgift
    @PostMapping("/checkin/{id}")
    public Category checkIn(@PathVariable String id) {
        return categoryService.checkIn(id);
    }
    
    // Den här endpointen används när man checkar ut från en uppgift
    @PostMapping("/checkout/{id}")
    public Category checkOut(@PathVariable String id) {
        return categoryService.checkOut(id);
    }

    // Den här endpointen visar statistiken för en vecka
    @GetMapping("/stats/week")
    public long getTotalWorkedMinutesForWeek() {
        return categoryService.getTotalWorkedMinutesForWeek();
    }
    
    @GetMapping("/stats/week/per-category")
    public Map<String, Long> getWorkedMinutesPerCategoryThisWeek() {
        return categoryService.getWorkedMinutesPerCategoryThisWeek();
    }

}
