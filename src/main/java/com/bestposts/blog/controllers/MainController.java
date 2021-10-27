package com.bestposts.blog.controllers;

        import com.bestposts.blog.models.Posts;
        import com.bestposts.blog.repos.PostsRepo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestParam;

        import java.util.ArrayList;
        import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private PostsRepo postsRepo;

    @GetMapping("/main")
    public String main(Model model) {

        Iterable<Posts> posts = postsRepo.findAll();
        model.addAttribute("posts", posts);
        return "main";
    }

    @GetMapping("/add")
    public String showAddPage(Model model) {

        return "addPost";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String label, @RequestParam String postText, Model model) {

        Posts post = new Posts(label, postText);
        postsRepo.save(post);
        return "redirect:/main";
    }

    @GetMapping("/main/{id}")
    public String showPost(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Posts> post = postsRepo.findById(id);
        ArrayList<Posts> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "post-details";
    }
    @GetMapping("/login")
    public String login() {

        return "login";
    }
}