package com.unir.products.service;

import com.unir.products.model.pojo.Category;

import java.util.List;

public interface CategoriesService {

    List<Category> getCategories();

    Category getCategory(Integer categoryId);

    Category createCategory(Category category);

    Category updateCategory(Integer categoryId, Category category);

    boolean removeCategory(Integer categoryId);
}
