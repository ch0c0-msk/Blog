package com.bestposts.blog.repos;

import org.springframework.data.repository.CrudRepository;
import com.bestposts.blog.models.Posts;

public interface PostsRepo extends CrudRepository<Posts, Integer> {
}
