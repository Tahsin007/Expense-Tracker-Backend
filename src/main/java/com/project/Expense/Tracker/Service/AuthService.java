package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.BlacklistedToken;
import com.project.Expense.Tracker.Entity.DTO.AuthResponse;
import com.project.Expense.Tracker.Entity.DTO.RefreshRequest;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Exception.ResourceNotFoundException;
import com.project.Expense.Tracker.Exception.UnauthorizedAccessEcxception;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.BlacklistedTokenRepository;
import com.project.Expense.Tracker.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User signUpService(User user){

        if (!user.getPassword().startsWith("$2a$")) { // BCrypt hashes start with $2a$
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRoles(List.of("USER"));
        return authRepository.save(user);
    }

    public AuthResponse signInService(User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        } catch (Exception e) {
            log.info("User Name : "+user.getUserName());
            throw new ResourceNotFoundException("Username or password incorrect");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        String jwt = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        return new AuthResponse(jwt,refreshToken);

    }

    public AuthResponse refresh(RefreshRequest request){
        String refreshToken = request.getRefreshToken();
        String username = jwtUtils.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtUtils.validateToken(refreshToken)) {
            String newAccessToken = jwtUtils.generateToken(userDetails);
            return new AuthResponse(newAccessToken, refreshToken);
        } else {
            throw new UnauthorizedAccessEcxception("Invalid Refresh Token");
        }
    }

    public void logout(String token) {
        Instant expiryDate = jwtUtils.extractExpiration(token).toInstant();
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, expiryDate);
        blacklistedTokenRepository.save(blacklistedToken);
    }


}
