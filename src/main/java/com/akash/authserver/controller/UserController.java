package com.akash.authserver.controller;

import com.akash.authserver.models.User;
import com.akash.authserver.repository.UserRepository;
import com.akash.authserver.service.UserServiceImpl;
import com.akash.authserver.util.AuthenticationRequest;
import com.akash.authserver.util.AuthenticationResponse;
import com.akash.authserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtTokenUtil;


    @GetMapping("/hello")
    public String sayHello(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        User saveUser = new User();
        saveUser.setUsername(user.getUsername());
        saveUser.setPassword(encoder.encode(user.getPassword()));
        saveUser.setRole(user.getRole());
        userRepository.save(saveUser);
        saveUser.setPassword(null);
        return saveUser;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


}
