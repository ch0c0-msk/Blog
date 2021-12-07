package com.bestposts.blog.repos;

import com.bestposts.blog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;

public interface UsersRepo extends JpaRepository<Users, Integer> {

   @Query("select u from Users u where u.email = ?1")
    Users findByEmail(String email);

}
