package com.selflearning.blogging.bloggingapplicationapi.repositories;

import com.selflearning.blogging.bloggingapplicationapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>
{
}
