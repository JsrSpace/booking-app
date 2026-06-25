package org.jsr.mvc.bookingapp.auth;

import org.jsr.mvc.bookingapp.entity.User;
import org.jsr.mvc.bookingapp.repo.UserRepository;
import org.jsr.mvc.bookingapp.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();


        User saved = userRepository.save(user);


        String token = jwtService.generateToken(saved);


        return new AuthResponse(token);
    }


    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));


        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new RuntimeException("Не правильный пароль");
        }


        String token = jwtService.generateToken(user);


        return new AuthResponse(token);
    }
}