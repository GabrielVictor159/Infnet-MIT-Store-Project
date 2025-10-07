package com.store.domain.Api.Mappers.User;

import org.springframework.beans.BeanUtils;

import com.store.domain.Api.Controllers.Requests.User.CreateUserRequest;
import com.store.domain.Application.Commands.User.CreateUserCommand;

public final class UserMapperExtension {
    public static CreateUserCommand toCreateUserCommand(CreateUserRequest request){
        var command = new CreateUserCommand();
        BeanUtils.copyProperties(request, command);
        return command;
    }
}
