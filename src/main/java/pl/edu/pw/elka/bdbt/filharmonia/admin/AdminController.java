package pl.edu.pw.elka.bdbt.filharmonia.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.UserRepository;
import pl.edu.pw.elka.bdbt.filharmonia.employee.EmployeeRepository;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.Musician;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.MusicianRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    MusicianRepository musicianRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("musicians", musicianRepository.findAll());
        return "admin";
    }

    @GetMapping("/hire")
    public String hiringPanel(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("musician", new Musician());
//        model.addAttribute("musicians", musicianRepository.findAll());
        return "admin/hireemployee";
    }

    @PostMapping("/hire")
    public void hire(@ModelAttribute User user, @ModelAttribute Musician musician, HttpServletResponse response) throws IOException {
        user.setRole("ROLE_USER");
        User savedUser = userRepository.save(user);
        musician.setUser(savedUser);
        musicianRepository.save(musician);
        response.sendRedirect("/admin");

    }

}
