package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.ConfirmUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.LogMaskResult;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;

import jakarta.transaction.Transactional;


@Component
public class ConfirmUserCommandHandler implements CommandHandler<ConfirmUserCommand, String> {

    @Autowired
    private UserRepository userRepository;

    @Value("classpath:static/confirm_user_sucess.html")
    private Resource defaultUserSucessTemplate;

    @Override
    @Transactional
    @LogMaskResult
    public String handle(ConfirmUserCommand command) {
       User user = userRepository.findByCreateId(command.getId())
            .orElseThrow(() -> new OperationException("Identificador unico invalido."));
        
        user.setIsActive(true);
        user.setCreateId(null);

        var bodyHtml = "";

        try {
            bodyHtml = new String(defaultUserSucessTemplate.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodyHtml;
    }
}