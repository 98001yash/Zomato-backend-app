package com.company.project.Zomato.ZomatoApp.services.Impl;

import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.dto.SignupDto;
import com.company.project.Zomato.ZomatoApp.dto.UserDto;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.entities.User;
import com.company.project.Zomato.ZomatoApp.enums.Role;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.exceptions.RuntimeConflictException;
import com.company.project.Zomato.ZomatoApp.repositories.UserRepository;
import com.company.project.Zomato.ZomatoApp.security.JwtService;
import com.company.project.Zomato.ZomatoApp.services.AuthService;
import com.company.project.Zomato.ZomatoApp.services.CustomerService;
import com.company.project.Zomato.ZomatoApp.services.RestaurantService;
import com.company.project.Zomato.ZomatoApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.company.project.Zomato.ZomatoApp.enums.Role.RESTAURANT;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final WalletService walletService;
     private final RestaurantService restaurantService;
     private final PasswordEncoder passwordEncoder;
     private final AuthenticationManager authenticationManager;
     private final JwtService jwtService;



    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};
    }

    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {
      User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
      if(user!=null)
          throw  new RuntimeConflictException("Cannot signup,  User already exists with email "+signupDto.getEmail());

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.CUSTOMER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

          // create user related entities
            customerService.createNewCustomer(savedUser);
            walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public RestaurantDto onboardNewRestaurant(Long userId, String hotelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+userId));

        if(user.getRoles().contains(RESTAURANT))
            throw new RuntimeConflictException("user with id "+userId+" is already a Restaurant");

        Restaurant createRestaurant = Restaurant.builder()
                .user(user)
                .rating(0.0)
                .hotelId(hotelId)
                .available(true)
                .build();

        user.getRoles().add(RESTAURANT);
        userRepository.save(user);

        Restaurant savedRestaurant = restaurantService.createNewRestaurant(createRestaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }


}
