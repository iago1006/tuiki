package com.tuiki.productservice.exception;

public class CategoryNotFoundException extends RuntimeException {

    private final Integer id;

    public CategoryNotFoundException(Integer id) {
        super("Category not found with id: " + id);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}