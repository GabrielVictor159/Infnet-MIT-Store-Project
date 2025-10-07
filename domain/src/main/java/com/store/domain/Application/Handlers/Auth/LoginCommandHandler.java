package com.store.domain.Application.Handlers.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.store.domain.Application.Commands.Auth.LoginCommand;
import com.store.domain.Application.DTO.Auth.LoginDTO;
import com.store.domain.Application.Services.JwtTokenService;
import com.store.domain.Domain.Models.User;
import com.store.domain.Infraestructure.Repositories.UserRepository;
import com.store.domain.Shared.Exceptions.OperationException;
import com.store.domain.Shared.MediatorPattern.CommandHandler;

@Component
public class LoginCommandHandler implements CommandHandler<LoginCommand, LoginDTO> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public LoginDTO handle(LoginCommand command) {
        User user = userRepository.findByEmail(command.getEmail())
            .orElseThrow(() -> new OperationException("Usuário ou senha inválidos."));

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new OperationException("Usuário ou senha inválidos.");
        }

        if(!user.getIsActive()) {
            throw new OperationException("Usuário ou senha inválidos.");
        }

        String token = jwtTokenService.generateToken(user);
        return new LoginDTO(token);
    }
}
