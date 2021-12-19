package com.bestposts.blog;


import com.bestposts.blog.models.Users;
import com.bestposts.blog.repos.UsersRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserServiceTest {

    @Autowired
    private UsersRepo usersRepo;

    @Test
    public void createUserTest() {

        Users user = new Users("ttt@mail.ru","test","test","1123");
        if (usersRepo.findByEmail("ttt@mail.ru") == null) {

            Users createdUser = usersRepo.save(user);
            assertThat(createdUser).isNotNull();
        } else assertThat(true);

    }

    @Test
    public void findUserTest() {

        String email = "test1@mail.ru";
        Users user = usersRepo.findByEmail(email);
        assertThat(user).isNotNull();
    }
}
