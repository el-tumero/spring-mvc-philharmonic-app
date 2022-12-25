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

//    @GetMapping
//    List<User> getUsers() { return userRepository.findAll(); }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("message", "ssr java epic");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        List<User> users = userRepository.findAll();
//        users.get(0).getEmail()
        model.addAttribute("users", users);
        return "done";
    }

}
