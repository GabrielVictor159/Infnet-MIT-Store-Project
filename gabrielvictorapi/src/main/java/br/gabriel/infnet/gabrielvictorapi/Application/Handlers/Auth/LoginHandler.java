package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Auth.LoginCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Auth.LoginDTO;
import br.gabriel.infnet.gabrielvictorapi.Application.Services.JwtTokenService;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;

@Component
public class LoginHandler implements CommandHandler<LoginCommand, LoginDTO> {

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
