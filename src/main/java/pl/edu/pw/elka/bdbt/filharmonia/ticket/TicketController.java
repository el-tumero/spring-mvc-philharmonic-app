package pl.edu.pw.elka.bdbt.filharmonia.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.dao.UserDao;

import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserDao userDao;


    @PostMapping("/buy/{id}")
    public String buyTicket(@PathVariable String id){
        Authentication ctx = SecurityContextHolder.getContext().getAuthentication();
        if(ctx.getName().equals("anonymusUser")){
            return "403";
        }

        Long ticketId = Long.valueOf(id);
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isEmpty()) return "404";

        if(ticket.get().getUser() != null) return "400";

        User user = userDao.findUserEntityByEmail(ctx.getName());

        ticket.get().setUser(user);

        ticketRepository.save(ticket.get());

        return "buyticketssuccess";

    }

    @PostMapping("/cancel/{id}")
    public String cancelTicket(@PathVariable String id){
        Authentication ctx = SecurityContextHolder.getContext().getAuthentication();
        if(ctx.getName().equals("anonymusUser")){
            return "403";
        }

        Long ticketId = Long.valueOf(id);
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isEmpty()) return "404";

        if(ticket.get().getUser() == null) return "403";

        User user = userDao.findUserEntityByEmail(ctx.getName());

        if(!ticket.get().getUser().getEmail().equals(ctx.getName())) return "403";

        ticket.get().setUser(null);

        ticketRepository.save(ticket.get());

        return "cancelticketsuccess";
    }
}
