package com.bestposts.blog.repos;

import com.bestposts.blog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bestposts.blog.models.Posts;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepo extends JpaRepository<Posts, Integer> {

    @Query("select p from Posts p where p.authorId = ?1")
    List<Posts> findAllByAuthor(Integer id);


}
