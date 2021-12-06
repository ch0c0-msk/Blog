package com.bestposts.blog;

import com.bestposts.blog.models.Posts;
import com.bestposts.blog.models.Users;
import com.bestposts.blog.repos.PostsRepo;
import com.bestposts.blog.repos.UsersRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private PostsRepo postsRepo;
    @Autowired
    PasswordEncoder passwordEncoder;


    private TestEntityManager entityManager;

    @Test
    void contextLoads() {
    }
    @Test
    public void testCreateUser() {

        Users user = new Users("testik123@mail.ru","Alex","Alex","12345");
        usersRepo.save(user);
        assertThat(user.getEmail()).isEqualTo("testik123@mail.ru");
    }

    @Test
    public void testFindByEmail() {
        String email = "test2002@mail.ru";
        Users user = usersRepo.findByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCreatePost() {

        Posts post = new Posts("label","text of post",61);
        postsRepo.save(post);
    }

    @Test
    public void testGetPassword() {
        Users user = usersRepo.findByEmail("test1@mail.ru");
        String password = user.getPassword();
        assertThat(passwordEncoder.matches(password,user.getPassword())).isTrue();
    }


}
