package com.springrest.services;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.model.dbForJWT.Userz;
import com.springrest.repository.UserzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserzService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserzRepository userzRepository;

    public UserzService(UserzRepository userzRepository) {
        this.userzRepository = userzRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        Userz userz = userzRepository.findByUsername(username);
        return new User(userz.getUsername(), userz.getPassword(), emptyList());
    }

    public void signUp(Userz u) {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userzRepository.save(u);
    }
}
