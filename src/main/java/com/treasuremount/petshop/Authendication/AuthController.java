/*
package com.treasuremount.petshop.Authendication;



import com.treasuremount.petshop.Authendication.Jwt.JwtUtil;
import com.treasuremount.petshop.Login.LoginDTO;
import com.treasuremount.petshop.Login.LoginResDTO;
import com.treasuremount.petshop.Repository.UserRepo;
import com.treasuremount.petshop.utils.Mapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
        @Autowired
        HttpSession session;

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private UserRepo repository;
    private Mapper mapper;
    private JwtUtil jwtUtil;


    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepo repository, Mapper mapper, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
    }




    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@RequestBody LoginDTO loginRequest) {

        try {
            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getMobileNumber());

            // Attempt authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getMobileNumber(),
                            loginRequest.getPassword()
                    )
            );

            // Set the authentication in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
//            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
              String jwtToken="";

            // Manually set session attribute to ensure session persistence
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            Map<String, Object> response= repository.findByMobileNumberAndPasswordForLogin(loginRequest.getMobileNumber());
            LoginResDTO responseDto=mapper.toDTO(response,jwtToken);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED).build();

        }
    }
}
*/
