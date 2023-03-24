package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoriesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        Optional<Category> foundCategory = categoriesRepository.findById(id);
        return foundCategory.orElseThrow(()
                -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Transactional
    public void save(Category category) {
        categoriesRepository.save(category);
    }

    @Transactional
    public void softDelete(Category category) {
        category.setDeleted(true);
        categoriesRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Optional<Category> loadByName(String name) {
        return categoriesRepository.findByName(name);
    }
}
