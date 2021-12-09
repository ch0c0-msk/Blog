package com.bestposts.blog.controllers;

        import com.bestposts.blog.models.CustomUserDetailsService;
        import com.bestposts.blog.models.Posts;
        import com.bestposts.blog.models.Users;
        import com.bestposts.blog.repos.PostsRepo;
        import com.bestposts.blog.repos.UsersRepo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import javax.persistence.criteria.CriteriaBuilder;
        import java.security.Principal;
        import java.util.ArrayList;
        import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private  UsersRepo usersRepo;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/main")
    public String main(Model model, Principal principal) {
        Integer curUsrId = null;
        if (principal != null) {
            curUsrId = ((Users) userDetailsService.loadUserByUsername(principal.getName())).getId();
        }
        Iterable<Posts> posts = postsRepo.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("curUsrId",curUsrId);
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
        String author = user.getFirstName() + ' ' + user.getLastName();
        Posts post = new Posts(label, postText, currentUserId, author);
        postsRepo.save(post);
        return "redirect:/main";
    }

    @GetMapping("/main/{id}")
    public String showPost(@PathVariable(value = "id") Integer id, Model model, Principal principal) {

        Optional<Posts> post = postsRepo.findById(id);
        Integer authorId = (post.get()).getAuthorId();
        Integer currId = null;

        if (principal != null) {

            Users user = (Users) userDetailsService.loadUserByUsername(principal.getName());
            currId = user.getId();
        }

        ArrayList<Posts> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("authorId",authorId);
        model.addAttribute("currId", currId);
        model.addAttribute("post",res);
        return "post-details";
    }

    @GetMapping("/main/{id}/edit")
    public String editPost(@PathVariable(value = "id") Integer id, Model model, Principal principal) {

        Optional<Posts> post = postsRepo.findById(id);

        Integer authorId = (post.get()).getAuthorId();
        Integer currId = ((Users) userDetailsService.loadUserByUsername(principal.getName())).getId();

        if ( authorId == currId) {

            ArrayList<Posts> res = new ArrayList<>();
            post.ifPresent(res::add);
            model.addAttribute("post", res);
            return "editPost";
        }

        return "redirect:/main";
    }

    @PostMapping("/main/{id}/edit")
    public String updatePost(@PathVariable(value = "id") Integer id, @RequestParam String label, @RequestParam String postText, Model model, Principal principal) {

        Posts post = postsRepo.findById(id).orElseThrow();
        Integer authorId = post.getAuthorId();
        Integer currId = ((Users) userDetailsService.loadUserByUsername(principal.getName())).getId();

        if ( authorId == currId) {

            post.setLabel(label);
            post.setPostText(postText);
            postsRepo.save(post);
            return "redirect:/main";
        }

        return "redirect:/main";
    }

    @PostMapping("/main/{id}/remove")
    public String removePost(@PathVariable(value = "id") Integer id, Model model, Principal principal) {

        Posts post = postsRepo.findById(id).orElseThrow();

        Integer authorId = post.getAuthorId();
        Integer currId = ((Users) userDetailsService.loadUserByUsername(principal.getName())).getId();

        if ( authorId == currId) {

            postsRepo.delete(post);
            return "redirect:/main";
        }

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