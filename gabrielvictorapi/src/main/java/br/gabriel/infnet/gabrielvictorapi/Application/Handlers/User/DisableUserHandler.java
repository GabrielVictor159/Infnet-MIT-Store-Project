package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.User;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.DisableUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class DisableUserHandler implements CommandHandler<DisableUserCommand, Boolean> {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Boolean handle(DisableUserCommand command) {
        var requestUser = userRepository.findById(command.getRequestUserId()).get();

        if(command.getId()!=command.getRequestUserId() && requestUser.getRule()!=UserRulesEnum.Admin){
            throw new ForbiddenException("Você não tem permissão para alterar esse usuário");
        }

        var user = userRepository.findById(command.getId());
        if(!user.isPresent()){
            throw new OperationException("Usuário não encontrado");
        }

        user.get().setIsActive(false);
        user.get().setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user.get());

        return true;
    }
}

