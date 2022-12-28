package pl.edu.pw.elka.bdbt.filharmonia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("message", "ssr java epic");
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "done";
    }

}
