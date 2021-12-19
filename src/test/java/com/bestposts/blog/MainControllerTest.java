package com.bestposts.blog;

import com.bestposts.blog.controllers.MainController;
import com.bestposts.blog.models.Posts;
import com.bestposts.blog.repos.PostsRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithUserDetails("test1@mail.ru")
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Autowired
    private PostsRepo postsRepo;

    @Test
    public void profilePageTest() throws Exception {

        this.mockMvc.perform(get("/profile/61"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='profile']/div[1]/div[3]/div[4]/h6").string("test1@mail.ru"));
    }

    @Test
    public void postListTest() throws Exception {

        this.mockMvc.perform(get("/main"))
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div/div[2]/div").nodeCount(postsRepo.getCount()));

    }

    @Test
    public void userPostListTest() throws Exception {

        this.mockMvc.perform(get("/my-posts"))
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div/div[2]/div").nodeCount(postsRepo.getCountByAuthor(61)));

    }

    @Test
    public void addPostTest() throws Exception {

        Posts post = new Posts();
        post.setPostText("postTest");
        post.setLabel("postLabel");
        post.setAuthorId(61);
        Posts createdPost = postsRepo.save(post);
        Assert.assertNotNull(createdPost);
    }

}
