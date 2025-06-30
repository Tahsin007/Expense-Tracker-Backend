package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Service.AuthService;
import com.project.Expense.Tracker.Service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
//            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            log.info(String.valueOf(userDetails));
            return new ResponseEntity<>("Sign In Success", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sign-up")
    public User signUp(@RequestBody User user){
        return authService.signUpService(user);
    }
}
