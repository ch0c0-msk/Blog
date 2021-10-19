package com.bestposts.blog.controllers;

        import com.bestposts.blog.models.Posts;
        import com.bestposts.blog.repos.PostsRepo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {

    @Autowired
    private PostsRepo postsRepo;

    @GetMapping("/main")
    public String main(Model model) {

        Iterable<Posts> posts = postsRepo.findAll();
        model.addAttribute("posts",posts);
        return "main";
    }

}
