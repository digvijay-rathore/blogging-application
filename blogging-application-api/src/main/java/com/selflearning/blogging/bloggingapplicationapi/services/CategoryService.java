package com.selflearning.blogging.bloggingapplicationapi.services;

import com.selflearning.blogging.bloggingapplicationapi.payloads.CategoryDto;

import java.util.List;

//@Validated
public interface CategoryService
{
    CategoryDto createCategory(/*@Valid*/CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryid);
    void deleteCategory(Integer categoryId);
    CategoryDto getCategoryById(Integer categoryId);
    List<CategoryDto> getAllCategories();
}
