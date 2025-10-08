package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Email.ForbiddenPasswordEmailCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.ForbiddenPasswordCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;
import jakarta.transaction.Transactional;

@Component
public class ForbiddenPasswordHandler implements CommandHandler<ForbiddenPasswordCommand, Optional<UUID>> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private Mediator mediator; 

    @Override
    @Transactional
    public Optional<UUID> handle(ForbiddenPasswordCommand command) {
        Optional<User> user = userRepository.findByEmail(command.getEmail());
        if(!user.isPresent()){
            return null;
        }

        user.get().setAlterPasswordId(UUID.randomUUID());

        var forbiddenPasswordEmailCommand = new ForbiddenPasswordEmailCommand();
        forbiddenPasswordEmailCommand.setEmail(user.get().getEmail());
        forbiddenPasswordEmailCommand.setForbiddenPasswordId(user.get().getAlterPasswordId());

         var sucessSendEmail = mediator.Handler(forbiddenPasswordEmailCommand);

        if(sucessSendEmail){
            userRepository.save(user.get());
        } else {
            throw new OperationException("Não foi possível enviar o email de confirmação.");
        }

        return Optional.ofNullable(user.get().getAlterPasswordId());
    }
}