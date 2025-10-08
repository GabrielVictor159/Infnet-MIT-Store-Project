package br.gabriel.infnet.gabrielvictorapi.Api.Mappers.User;

import org.springframework.beans.BeanUtils;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.User.CreateUserRequest;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.CreateUserCommand;

public final class UserMapperExtension {
    public static CreateUserCommand toCreateUserCommand(CreateUserRequest request){
        var command = new CreateUserCommand();
        BeanUtils.copyProperties(request, command);
        return command;
    }
}
