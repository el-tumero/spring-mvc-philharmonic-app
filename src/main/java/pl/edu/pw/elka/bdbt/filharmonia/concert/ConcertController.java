package pl.edu.pw.elka.bdbt.filharmonia.concert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.UserRepository;
import pl.edu.pw.elka.bdbt.filharmonia.dao.UserDao;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.Ticket;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/concert")
public class ConcertController {

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    UserDao userDao;

    @GetMapping("/{id}")
    public String concertPage(@PathVariable String id, Model model){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idLong = Long.valueOf(id);
        Optional<Concert> concert = concertRepository.findById(idLong);
        model.addAttribute("concert", concert.get());


        if(userEmail.equals("anonymousUser")){
            model.addAttribute("user", null);
            return "concert";
        }

        final User user = userDao.findUserEntityByEmail(userEmail);
        model.addAttribute("user", user);
        return "concert";
    }

    @GetMapping("/{id}/tickets")
    public String buyTicketsPage(@PathVariable String id, Model model, HttpServletResponse response) throws IOException {
        Authentication ctx = SecurityContextHolder.getContext().getAuthentication();
        if(ctx.getName().equals("anonymousUser")){
            response.sendRedirect("/user/login");
            return "wrong";
        }
        final User user = userDao.findUserEntityByEmail(ctx.getName());
        model.addAttribute("user", user);


        Long idLong = Long.valueOf(id);
        Optional<Concert> concert = concertRepository.findById(idLong);
        model.addAttribute("concert", concert.get());
        concert.get().getTickets().sort(Comparator.comparing(Ticket::getId));

        model.addAttribute("tickets", concert.get().getTickets());

        return "ticket/buy";
    }

}
