package pl.edu.pw.elka.bdbt.filharmonia.concert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.Ticket;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/concert")
public class ConcertController {

    @Autowired
    ConcertRepository concertRepository;

    @GetMapping("/{id}")
    public String concertPage(@PathVariable String id, Model model){
        Long idLong = Long.valueOf(id);
        Optional<Concert> concert = concertRepository.findById(idLong);
        model.addAttribute("concert", concert.get());
        return "concert";
    }

    @GetMapping("/{id}/tickets")
    public String buyTicketsPage(@PathVariable String id, Model model){
        Long idLong = Long.valueOf(id);
        Optional<Concert> concert = concertRepository.findById(idLong);
        model.addAttribute("concert", concert.get());
        concert.get().getTickets().sort(Comparator.comparing(Ticket::getId));

        model.addAttribute("tickets", concert.get().getTickets());

        return "ticket/buy";
    }

}
