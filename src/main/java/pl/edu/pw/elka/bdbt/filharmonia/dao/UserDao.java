package pl.edu.pw.elka.bdbt.filharmonia.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.UserRepository;

import java.util.Collections;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public UserDetails findUserByEmail(String email) {

        User user = new User();
        user.setEmail(email);
        Example<User> example = Example.of(user);
        User result = userRepository.findOne(example).orElseThrow(() -> new UsernameNotFoundException("No user was found"));

        return new org.springframework.security.core.userdetails.User(result.getEmail(), result.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
