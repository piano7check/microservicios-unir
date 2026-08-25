package com.unir.products.controller;

import com.unir.products.model.pojo.Category;
import com.unir.products.service.CategoriesServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoriesController {

    private final CategoriesServiceImpl service;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = service.getCategories();

        if (!categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Integer categoryId) {
        Category category = service.getCategory(categoryId);

        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
