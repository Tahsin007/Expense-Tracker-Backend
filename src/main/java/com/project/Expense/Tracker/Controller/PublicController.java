package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Exception.ApiException;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Service.AuthService;
import com.project.Expense.Tracker.Service.UserDetailsServiceImpl;
import com.project.Expense.Tracker.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    private static final Logger log = LoggerFactory.getLogger(PublicController.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        } catch (Exception e) {
            log.info("User Name : "+user.getUserName());
            throw new ApiException("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        String jwt = jwtUtils.generateToken(userDetails);
        return new ResponseEntity<>("Bearer Token : "+jwt, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public User signUp(@RequestBody User user){
        return authService.signUpService(user);
    }
}
