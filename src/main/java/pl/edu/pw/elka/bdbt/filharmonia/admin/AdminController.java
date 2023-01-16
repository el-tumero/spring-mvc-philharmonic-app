package pl.edu.pw.elka.bdbt.filharmonia.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.UserRepository;
import pl.edu.pw.elka.bdbt.filharmonia.address.Address;
import pl.edu.pw.elka.bdbt.filharmonia.address.AddressRepository;
import pl.edu.pw.elka.bdbt.filharmonia.concert.Concert;
import pl.edu.pw.elka.bdbt.filharmonia.concert.ConcertRepository;
import pl.edu.pw.elka.bdbt.filharmonia.dao.UserDao;
import pl.edu.pw.elka.bdbt.filharmonia.employee.EmployeeRepository;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.Musician;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.MusicianRepository;
import pl.edu.pw.elka.bdbt.filharmonia.hall.Hall;
import pl.edu.pw.elka.bdbt.filharmonia.hall.HallRepository;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.PhilharmonicRepository;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.Ticket;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.TicketRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    MusicianRepository musicianRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    PhilharmonicRepository philharmonicRepository;

    @Autowired
    AddressRepository addressRepository;


    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("musicians", musicianRepository.findAll());
        model.addAttribute("concerts", concertRepository.findAll());
        return "admin";
    }

    @GetMapping("/hire")
    public String hiringPanel(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("musician", new Musician());
        model.addAttribute("address", new Address());
//        model.addAttribute("musicians", musicianRepository.findAll());
        return "admin/hireemployee";
    }

    @PostMapping("/hire")
    public void hire(@ModelAttribute User user, @ModelAttribute Musician musician, @ModelAttribute Address address, HttpServletResponse response) throws IOException {
        Philharmonic philharmonic = philharmonicRepository.findById(Long.valueOf("1")).get();
        user.setRole("ROLE_USER");
        user.setPhilharmonic(philharmonic);
        User savedUser = userRepository.save(user);
        musician.setUser(savedUser);
        musician.setAddress(address);
        musicianRepository.save(musician);
        response.sendRedirect("/admin");

    }

    @GetMapping("/concert/{id}")
    public String concert(@PathVariable String id, Model model){
        Long idLong = Long.valueOf(id);
        Concert concert = concertRepository.findById(idLong).get();
        model.addAttribute("halls", hallRepository.findAll());
        model.addAttribute("concert", concert);
        model.addAttribute("musicians", musicianRepository.findAll());
        return "admin/concert";
    }

    @GetMapping("/concert/create")
    public String createConcertPanel(Model model) {
        model.addAttribute("halls", hallRepository.findAll());
        model.addAttribute("concert", new Concert());
        return "admin/createconcert";
    }

    @PostMapping("/concert/create")
    public void createConcertAction(@ModelAttribute Concert concert, @RequestParam(value = "selection") String selection, HttpServletResponse response) throws IOException {
        Philharmonic philharmonic = philharmonicRepository.findById(Long.valueOf("1")).get();
        Hall hall = hallRepository.findById(Long.valueOf(selection)).get();
        concert.setPhilharmonic(philharmonic);
        concert.setHall(hall);
        concertRepository.save(concert);
        response.sendRedirect("/admin");
    }

    @PostMapping("/concert/edit/{id}")
    public void editConcertAction(@ModelAttribute Concert updatedConcert, @RequestParam(value = "selection") String selection, @PathVariable String id, HttpServletResponse response) throws IOException {
        Concert concert = concertRepository.findById(Long.valueOf(id)).get();
        Hall hall = hallRepository.findById(Long.valueOf(selection)).get();

        if(!updatedConcert.getDate().equals(concert.getDate())){
            concert.setMusicians(null);
        }

        concert.setName(updatedConcert.getName());
        concert.setDescription(updatedConcert.getDescription());
        concert.setType(updatedConcert.getType());
        concert.setDuration(updatedConcert.getDuration());
        concert.setDate(updatedConcert.getDate());
        concert.setHall(hall);



        concertRepository.save(concert);

        response.sendRedirect("/admin/concert/" + id);
    }

    @PostMapping("/concert/delete/{id}")
    public void removeConcert(@PathVariable String id, HttpServletResponse response) throws IOException{
        Long concertId = Long.valueOf(id);
        Concert concert = concertRepository.findById(concertId).get();
        concertRepository.delete(concert);
        response.sendRedirect("/admin");
    }

    @PostMapping("/concert/{id}/add/{musicianId}")
    public void addMusicianToConcert(@PathVariable String id, @PathVariable String musicianId, HttpServletResponse response) throws IOException {
        Long concertId = Long.valueOf(id);
        Long longMusicianId = Long.valueOf(musicianId);
        Concert concert = concertRepository.findById(concertId).get();
        Musician musician = musicianRepository.findById(longMusicianId).get();
        concert.getMusicians().add(musician);
        concertRepository.save(concert);

        response.sendRedirect("/admin/concert/" + id);
    }

    @PostMapping("/concert/{id}/remove/{musicianId}")
    public void removeMusicianFromConcert(@PathVariable String id, @PathVariable String musicianId, HttpServletResponse response) throws IOException {
        Long concertId = Long.valueOf(id);
        Long longMusicianId = Long.valueOf(musicianId);
        Concert concert = concertRepository.findById(concertId).get();
        Musician musician = musicianRepository.findById(longMusicianId).get();
        concert.getMusicians().remove(musician);
        concertRepository.save(concert);

        response.sendRedirect("/admin/concert/" + id);
    }

    @PostMapping("/concert/{id}/tickets/start")
    public void startTicketSale(@PathVariable String id, HttpServletResponse response) throws IOException {
        Long concertId = Long.valueOf(id);
        Concert concert = concertRepository.findById(concertId).get();
        if(!concert.getTickets().isEmpty()) {
            response.sendError(400);
            return;
        }
        int hallCapacity = concert.getHall().getCapacity();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        int placesPerRow = 10;
        for (int i = 0; i < hallCapacity; i++) {
            int x = i % placesPerRow;
            int y = i / placesPerRow;
            Ticket ticket = new Ticket();
            ticket.setConcert(concert);
            ticket.setPrice(20);
            ticket.setSeatId(alphabet[y] + "" + x);
            ticketRepository.save(ticket);
        }

        response.sendRedirect("/admin/concert/" + id);
    }

    @PostMapping("/concert/{id}/tickets/delete")
    public void deleteTickets(@PathVariable String id, HttpServletResponse response) throws IOException{
        Long concertId = Long.valueOf(id);
        Concert concert = concertRepository.findById(concertId).get();
        if(concert.getTickets().isEmpty()) {
            response.sendError(400);
            return;
        }
        List<Ticket> tickets = concert.getTickets();

        tickets.forEach(ticket -> {
            ticketRepository.delete(ticket);
        });

        response.sendRedirect("/admin/concert/" + id);
    }

    @PostMapping("/edit/musician/{id}")
    public void editMusician(@PathVariable String id, @ModelAttribute Musician updatedMusician, @ModelAttribute User updatedUser, @ModelAttribute Address updatedAddress, HttpServletResponse response) throws IOException {
        Musician musician = musicianRepository.findById(Long.valueOf(id)).get();

        User user = musician.getUser();
        user.setEmail(updatedUser.getEmail());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setBirthdate(updatedUser.getBirthdate());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setPassword(updatedUser.getPassword());
        userRepository.save(user);

        musician.setGender(updatedMusician.getGender());
        musician.setIban(updatedMusician.getIban());
        musician.setPesel(updatedMusician.getPesel());
        musician.setSalary(updatedMusician.getSalary());
        musician.setEducation(updatedMusician.getEducation());
        musician.setSpecialization(updatedMusician.getSpecialization());
        musicianRepository.save(musician);

        Address address = musician.getAddress();
        address.setTown(updatedAddress.getTown());
        address.setStreet(updatedAddress.getStreet());
        address.setApartamentNumber(updatedAddress.getApartamentNumber());
        address.setPostcode(updatedAddress.getPostcode());

        addressRepository.save(address);

        response.sendRedirect("/musician/" + id);
    }

}
