package com.tuiki.productservice.service;

import com.tuiki.productservice.domain.Category;
import com.tuiki.productservice.exception.CategoryNotFoundException;
import com.tuiki.productservice.exception.CategoryValidationException;
import com.tuiki.productservice.exception.InvalidOperationException;
import com.tuiki.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        validateCategory(category);
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Integer id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        validateCategory(category);

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (!category.getProducts().isEmpty()) {
            throw new InvalidOperationException("Cannot delete category with associated products");
        }

        categoryRepository.deleteById(id);
    }

    private void validateCategory(Category category) {
        if (ObjectUtils.isEmpty(category.getName())) {
            throw new CategoryValidationException("El nombre no debe estar vacío");
        }

        if (ObjectUtils.isEmpty(category.getDescription())) {
            throw new CategoryValidationException("La descripción no debe estar vacía");
        }
    }
}
