package pl.edu.pw.elka.bdbt.filharmonia.employee.musician;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.address.Address;

@Controller
@RequestMapping("/musician")
public class MusicianController {

    @Autowired
    MusicianRepository musicianRepository;

    @GetMapping("/{id}")
    public String musicianProfile(@PathVariable String id, Model model){
        Musician musician = musicianRepository.findById(Long.valueOf(id)).get();
        model.addAttribute("musician", musician);

        Authentication ctx = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = ctx.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.toString().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("user", musician.getUser());
        model.addAttribute("address", musician.getAddress());

        return "musicianprofile";
    }
}
