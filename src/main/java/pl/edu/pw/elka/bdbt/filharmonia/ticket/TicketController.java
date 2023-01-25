package pl.edu.pw.elka.bdbt.filharmonia.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.dao.UserDao;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserDao userDao;


    @PostMapping("/buy/{id}")
    public void buyTicket(@PathVariable String id, HttpServletResponse response) throws IOException {
        Authentication ctx = SecurityContextHolder.getContext().getAuthentication();
        if(ctx.getName().equals("anonymusUser")){
            return;
        }

        Long ticketId = Long.valueOf(id);
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isEmpty()) return;

        if(ticket.get().getUser() != null) return;

        User user = userDao.findUserEntityByEmail(ctx.getName());

        ticket.get().setUser(user);

        ticketRepository.save(ticket.get());

        response.sendRedirect("/user/tickets");

//        return "ticket/buysuccess";

    }

    @PostMapping("/cancel/{id}")
    public void cancelTicket(@PathVariable String id, HttpServletResponse response) throws IOException {
        Authentication ctx = SecurityContextHolder.getContext().getAuthentication();
        if(ctx.getName().equals("anonymusUser")){
            return;
        }

        Long ticketId = Long.valueOf(id);
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isEmpty()) return;

        if(ticket.get().getUser() == null) return;

        User user = userDao.findUserEntityByEmail(ctx.getName());

        if(!ticket.get().getUser().getEmail().equals(ctx.getName())) return;

        ticket.get().setUser(null);

        ticketRepository.save(ticket.get());

        response.sendRedirect("/user/tickets");
//        return "ticket/cancelsuccess";
    }
}
