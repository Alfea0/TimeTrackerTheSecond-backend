package com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.model.Category;
import com.TimeTrackerTheSecond_backend.TimeTrackerTheSecond_backend.repository.CategoryRepository;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(String id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setId(id);
            return categoryRepository.save(category);
        }
        return null;
    }

     // Denna metoden används för att checka in på en uppgift
     public Category checkIn(String id) {
        Optional<Category> taskOptional = categoryRepository.findById(id);
        if (taskOptional.isPresent()) {
            Category category = taskOptional.get();
            category.setCheckInTime(LocalDateTime.now());
            category.setActive(true); 
            return categoryRepository.save(category);
        }
        return null;
    }

    // Denna metoden används för att checka ut från en uppgift
    public Category checkOut(String id) {
        Optional<Category> taskOptional = categoryRepository.findById(id);
        if (taskOptional.isPresent()) {
            Category category = taskOptional.get();
            category.setCheckOutTime(LocalDateTime.now());
            category.setActive(false); 
            return categoryRepository.save(category);
        }
        return null;
    }

     // En metod för att beräkna arbetad tid
     public long calculateWorkedMinutes(Category category) {
        if (category.getCheckInTime() != null && category.getCheckOutTime() != null) {
            Duration duration = Duration.between(category.getCheckInTime(), category.getCheckOutTime());
            return duration.toMinutes();  
        }
        return 0;
    }

    // En metod för att beräkna tiden över en vecka
    public long getTotalWorkedMinutesForWeek() {
        LocalDateTime startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);

        long totalMinutes = 0;
        List<Category> categorys = categoryRepository.findAll();
        for (Category category : categorys) {
            if (category.getCheckInTime() != null && category.getCheckOutTime() != null &&
                !category.getCheckInTime().isBefore(startOfWeek) && category.getCheckOutTime().isBefore(endOfWeek)) {
                totalMinutes += calculateWorkedMinutes(category);
            }
        }
        return totalMinutes; 
    }

    // Metod för att hämta arbetad tid per kategori denna vecka
    public Map<String, Long> getWorkedMinutesPerCategoryThisWeek() {
        LocalDateTime startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);

        List<Category> allCategories = categoryRepository.findAll();
        Map<String, Long> stats = new HashMap<>();

        // Loopar igenom alla kategorier och summerar arbetstiden per kategori
        for (Category category : allCategories) {
            if (category.getCheckInTime() != null && category.getCheckOutTime() != null &&
                !category.getCheckInTime().isBefore(startOfWeek) && category.getCheckOutTime().isBefore(endOfWeek)) {
                
                long minutes = Duration.between(category.getCheckInTime(), category.getCheckOutTime()).toMinutes();
                stats.merge(category.getName(), minutes, Long::sum);
            }
        }

    return stats;
}

}
