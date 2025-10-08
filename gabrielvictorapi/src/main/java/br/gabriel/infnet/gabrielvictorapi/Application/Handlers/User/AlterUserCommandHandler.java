package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.AlterUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class AlterUserCommandHandler implements CommandHandler<AlterUserCommand, Boolean> {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Boolean handle(AlterUserCommand command) {
        var requestUser = userRepository.findById(command.getRequestUserId()).get();
        if(command.getId()==null) command.setId(command.getRequestUserId());
        if(command.getId()!=command.getRequestUserId() && requestUser.getRule()!=UserRulesEnum.Admin){
            throw new ForbiddenException("Você não tem permissão para alterar esse usuário");
        }
        var user = userRepository.findById(command.getId());
        if(!user.isPresent()){
            throw new OperationException("Usuário não encontrado");
        }

        if (command.getName() != null) user.get().setName(command.getName());
        if (command.getCpf() != null) user.get().setCpf(command.getCpf());
        if (command.getPhone() != null) user.get().setPhone(command.getPhone());
        if (command.getBirthDate() != null) user.get().setBirthDate(command.getBirthDate());

        userRepository.save(user.get());

        return true;
    }
}
