package com.FreteGen.FreteGen.controller;


import com.FreteGen.FreteGen.dto.AuthenticationDTO;
import com.FreteGen.FreteGen.dto.LoginResponseDTO;
import com.FreteGen.FreteGen.dto.RegisterDTO;
import com.FreteGen.FreteGen.repository.UserRepository;
import com.FreteGen.FreteGen.security.TokenService;
import com.FreteGen.FreteGen.user.Clients;
import com.FreteGen.FreteGen.user.UserRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authDTO) {
        var userNamePasswoard = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePasswoard);
        var token = tokenService.generetedToken((Clients) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Clients newClients = new Clients(data.login(), encryptedPassword, UserRole.USER);
        userRepository.save(newClients);

        return ResponseEntity.status(HttpStatus.CREATED).body("user created: " + data.login());

    }

    @PostMapping("/create-admin")
    public ResponseEntity registerAdmin(@RequestBody @Valid RegisterDTO dto) {
        if (userRepository.findByLogin(dto.login()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This record already exists in the database: " + dto.login());

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Clients newClients = new Clients(dto.login(), encryptedPassword, dto.role());
        userRepository.save(newClients);

        return ResponseEntity.status(HttpStatus.CREATED).body("ADMIN created: " + dto.login());

    }

}
