package com.selflearning.blogging.bloggingapplicationapi.repositories;

import com.selflearning.blogging.bloggingapplicationapi.entities.Category;
import com.selflearning.blogging.bloggingapplicationapi.entities.Post;
import com.selflearning.blogging.bloggingapplicationapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>
{
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String keywords);

    //The syntax and features you can use in the SQL query in @Query depend on the DBMS you are using.
    //For example, if you are using MySQL, you would write MySQL-specific SQL syntax within the @Query
    //annotation. If you are using PostgreSQL, you would use PostgreSQL-specific SQL syntax, and so on.
    @Query("select p from Post p where p.title like :keywords")
    List<Post> mySearchByTitle(String keywords);
}
