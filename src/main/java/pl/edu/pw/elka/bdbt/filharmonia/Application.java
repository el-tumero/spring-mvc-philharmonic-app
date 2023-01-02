package pl.edu.pw.elka.bdbt.filharmonia;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.elka.bdbt.filharmonia.dao.UserDao;
import pl.edu.pw.elka.bdbt.filharmonia.employee.Employee;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.Musician;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.MusicianRepository;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.PhilharmonicRepository;

import javax.persistence.Id;
import java.util.Optional;

// https://stackoverflow.com/questions/28042426/spring-boot-error-creating-bean-with-name-datasource-defined-in-class-path-r
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@Controller
public class Application {

	@Autowired
	UserDao userDao;

	@Autowired
	MusicianRepository musicianRepository;

	@Autowired
	PhilharmonicRepository philharmonicRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/")
	public String index(Model model) {
		Authentication ctx = SecurityContextHolder.getContext().getAuthentication();

		Optional<Philharmonic> philharmonic = philharmonicRepository.findById(1L);

		model.addAttribute("philharmonic", philharmonic.get());

		model.addAttribute("concerts", philharmonic.get().getConcerts());

//		philharmonic.get().getConcerts().get(0).getDate().toString()

//		philharmonic.get().getOwner().getFirstName()
		if(ctx.getName().equals("anonymousUser")){
			model.addAttribute("user", null);
			return "index";
		}

		final User user = userDao.findUserEntityByEmail(ctx.getName());
		model.addAttribute("user", user);
		return "index";
	}

//	@GetMapping("/admin")
//	public String admin() {
//
//		User user = userDao.findUserEntityByEmail("jan");
//		Musician m = new Musician();
//		m.setEducation("high");
//		m.setGender("male");
//		m.setIban("30109024025234614273875175");
//		m.setPesel("89081152495");
//		m.setUser(user);
//
//		musicianRepository.save(m);
//
//		return "admin";
//	}

	@GetMapping("/user")
	public String user() {
		return "user";
	}


}
