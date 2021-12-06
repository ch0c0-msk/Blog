package com.bestposts.blog.controllers;

        import com.bestposts.blog.models.CustomUserDetailsService;
        import com.bestposts.blog.models.Posts;
        import com.bestposts.blog.models.Users;
        import com.bestposts.blog.repos.PostsRepo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestParam;

        import java.security.Principal;
        import java.util.ArrayList;
        import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private CustomUserDetailsService userDetailsService;

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
    public String addPost(@RequestParam String label, @RequestParam String postText, Model model,Principal principal) {

        Users user = (Users) userDetailsService.loadUserByUsername(principal.getName());
        Integer currentUserId = user.getId();
        Posts post = new Posts(label, postText, currentUserId);
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

    @GetMapping("/main/{id}/edit")
    public String editPost(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Posts> post = postsRepo.findById(id);
        ArrayList<Posts> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "editPost";
    }

    @PostMapping("/main/{id}/edit")
    public String updatePost(@PathVariable(value = "id") Integer id, @RequestParam String label, @RequestParam String postText, Model model) {
        Posts post = postsRepo.findById(id).orElseThrow();
        post.setLabel(label);
        post.setPostText(postText);
        postsRepo.save(post);
        return "redirect:/main";
    }

    @PostMapping("/main/{id}/remove")
    public String removePost(@PathVariable(value = "id") Integer id, Model model) {
        Posts post = postsRepo.findById(id).orElseThrow();
        postsRepo.delete(post);
        return "redirect:/main";
    }

    @GetMapping("/my-posts")
    public String myPosts(Principal principal, Model model) {
        if (principal.getName() != null) {
            Users user = (Users) userDetailsService.loadUserByUsername(principal.getName());
            Integer currentUserId = user.getId();
            Iterable<Posts> posts = postsRepo.findAllByAuthor(currentUserId);
            model.addAttribute("posts", posts);
            return "myPosts";
        }
        else {
            return "signIn";
        }
    }

}