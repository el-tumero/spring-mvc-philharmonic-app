package pl.edu.pw.elka.bdbt.filharmonia;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.bdbt.filharmonia.config.JwtUtils;
import pl.edu.pw.elka.bdbt.filharmonia.dao.UserDao;
import pl.edu.pw.elka.bdbt.filharmonia.dto.AuthenticationRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }


    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDao.findUserByEmail(request.getEmail());
        if(user != null){
            String token = jwtUtils.generateToken(user);
            HttpHeaders headers = new HttpHeaders();
            HttpCookie cookie = ResponseCookie.from("token", token).maxAge(604800).httpOnly(true).path("/").build();
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok().headers(headers).body("Done!");
        }
        return ResponseEntity.status(400).body("Error occured!");
    }
}
