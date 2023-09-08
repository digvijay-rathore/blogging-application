package com.selflearning.blogging.bloggingapplicationapi.repositories;

import com.selflearning.blogging.bloggingapplicationapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>
{
}
