package pl.edu.pw.elka.bdbt.filharmonia;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.bdbt.filharmonia.config.JwtUtils;
import pl.edu.pw.elka.bdbt.filharmonia.dao.UserDao;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.PhilharmonicRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhilharmonicRepository philharmonicRepository;

    @GetMapping("/tickets")
    public String tickets(Model model){
        Authentication ctx = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findUserEntityByEmail(ctx.getName());

        model.addAttribute("tickets", user.getTickets());
//        user.getTickets().get(0).
        return "tickets";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("message", "ssr java epic");
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user, Model model) {
        Philharmonic philharmonic = philharmonicRepository.findById(Long.valueOf("1")).get();
        user.setRole("ROLE_USER");
        user.setPhilharmonic(philharmonic);
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "done";
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletResponse response) throws IOException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        final UserDetails userDetails = userDao.findUserByEmail(user.getEmail());

        if(userDetails != null){

            String token = jwtUtils.generateToken(userDetails);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setMaxAge(60480);
            response.addCookie(cookie);
            response.sendRedirect("/");
        }

        return "wrong";
    }

}
