package pl.edu.pw.elka.bdbt.filharmonia.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.UserRepository;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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
    HallRepository hallRepository;

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    PhilharmonicRepository philharmonicRepository;


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

//    @GetMapping("/addtoconcert/{id}")
//    public String musicianConcertPanel(@PathVariable String id, Model model){
//        Long idLong = Long.valueOf(id);
//        Optional<Musician> musician = musicianRepository.findById(idLong);
//
//        model.addAttribute("musician", musician.get());
//
//        model.addAttribute("concerts", concertRepository.findAll());
////        model.addAttribute("test", id);
//        return "admin/musicianconcert";
//    }
//
//    @PostMapping("/addtoconcert/{id}/{musicianId}")
//    public void addToConcert(@PathVariable String id, @PathVariable String musicianId, HttpServletResponse response) throws IOException {
//        Long concertId = Long.valueOf(id);
//        Long longMusicianId = Long.valueOf(musicianId);
//
//        Optional<Concert> concert = concertRepository.findById(concertId);
//        Optional<Musician> musician = musicianRepository.findById(longMusicianId);
//
//        List<Concert> concerts = musician.get().getConcerts();
//
//        concerts.add(concert.get());
//
//        musicianRepository.save(musician.get());
//
//        response.sendRedirect("/admin");
//
//
//
//    }

}
