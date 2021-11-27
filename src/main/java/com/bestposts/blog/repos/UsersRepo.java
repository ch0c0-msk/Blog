package com.bestposts.blog.repos;

import com.bestposts.blog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Integer> {

}
