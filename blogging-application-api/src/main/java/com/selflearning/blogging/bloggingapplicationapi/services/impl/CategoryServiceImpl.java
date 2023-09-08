package com.selflearning.blogging.bloggingapplicationapi.services.impl;

import com.selflearning.blogging.bloggingapplicationapi.entities.Category;
import com.selflearning.blogging.bloggingapplicationapi.exceptions.ResourceNotFoundException;
import com.selflearning.blogging.bloggingapplicationapi.payloads.CategoryDto;
import com.selflearning.blogging.bloggingapplicationapi.repositories.CategoryRepository;
import com.selflearning.blogging.bloggingapplicationapi.services.CategoryService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService
{
    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

//    Constructor injection is actually recommended over field injection, and has several advantages:
//    -> The dependencies are clearly identified. There is no way to forget one when testing, or
//       instantiating the object in any other circumstance (like creating the bean instance
//       explicitly in a config class).
//    -> The dependencies can be final, which helps with robustness and thread-safety.
//    -> You don't need reflection to set the dependencies. InjectMocks is still usable, but not
//       necessary. You can just create mocks by yourself and inject them by simply calling the
//       constructor.
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper)
    {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto)
    {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category createdCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(createdCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId)
    {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId)
    {
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryId);

        if(optionalCategory.isEmpty())
        {
            throw new ResourceNotFoundException("Category", "Category Id", categoryId);
        }

        this.categoryRepository.delete(optionalCategory.get());
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId)
    {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories()
    {
        List<Category> categories = this.categoryRepository.findAll();

        //==================================Java Streams vs for-loop===================================
        //Link-https://blog.jdriven.com/2019/10/loop/

        //===========================1 - Performance========================
        //There are many opinions about which style performs better. The short version basically is,
        // if you have a small list; for loops perform better, if you have a huge list; a parallel
        // stream will perform better.
        //===========================2 - Readability=========================
        //Everything seems hard when it is not familiar. When you first started coding, a for loop
        // looks (and is!) very complicated. But after a few years of coding and having used many for
        // loops, it became more familiar and easier to use. The same applies to the streaming API.

//        categories.forEach(category -> {
//            categoryDtos.add(this.modelMapper.map(category, CategoryDto.class));
//        });

        List<CategoryDto> categoryDtos;

        categoryDtos = categories.stream()
                .map(category -> this.modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        return categoryDtos;
    }
}
