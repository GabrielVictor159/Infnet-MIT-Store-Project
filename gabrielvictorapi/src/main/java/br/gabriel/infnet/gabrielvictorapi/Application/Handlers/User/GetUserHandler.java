package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.GetUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.User.GetUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class GetUserHandler implements CommandHandler<GetUserCommand, GetUserDTO>{
     @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public GetUserDTO handle(GetUserCommand command) {
        var requestUser = userRepository.findById(command.getRequestUser()).get();
        if(command.getUserId()!=command.getRequestUser() && requestUser.getRule()!=UserRulesEnum.Admin){
            throw new ForbiddenException("Você não tem permissão para acessar os dados do usuário");
        }
        var user = userRepository.findById(command.getUserId());
        if(!user.isPresent()){
            throw new OperationException("Usuário não encontrado");
        }

        GetUserDTO result = new GetUserDTO();
        BeanUtils.copyProperties(user.get(),result);

        return result;
    }
}
