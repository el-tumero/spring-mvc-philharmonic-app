package pl.edu.pw.elka.bdbt.filharmonia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// https://stackoverflow.com/questions/28042426/spring-boot-error-creating-bean-with-name-datasource-defined-in-class-path-r
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@Controller
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
