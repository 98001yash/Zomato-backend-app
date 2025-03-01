package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.entities.User;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public  class  UserService implements  UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

    public User getUserById(Long userId) {
       return userRepository.findById(userId)
               .orElseThrow(()->new ResourceNotFoundException("user not found with id: "+userId));
    }
}
