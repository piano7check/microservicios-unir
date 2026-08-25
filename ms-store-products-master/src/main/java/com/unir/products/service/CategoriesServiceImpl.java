package com.unir.products.service;

import com.unir.products.data.CategoryRepository;
import com.unir.products.model.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService{

    private final CategoryRepository repository;

    @Autowired
    public CategoriesServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getCategories() {
        return repository.findAll();
    }

    public Category getCategory(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    public Category createCategory(Category category) {
        return repository.save(category);
    }

    public Category updateCategory(Integer categoryId, Category category) {
        if (repository.existsById(categoryId)) {
            category.setId(categoryId);
            return repository.save(category);
        } else {
            return null;
        }
    }

    public boolean removeCategory(Integer categoryId) {
        if (repository.existsById(categoryId)) {
            repository.deleteById(categoryId);
            return true;
        } else {
            return false;
        }
    }

}
