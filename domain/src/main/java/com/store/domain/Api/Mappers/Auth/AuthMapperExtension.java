package com.store.domain.Api.Mappers.Auth;

import org.springframework.beans.BeanUtils;

import com.store.domain.Api.Controllers.Requests.Auth.AuthLoginRequest;
import com.store.domain.Application.Commands.Auth.LoginCommand;

public final class AuthMapperExtension {
    private AuthMapperExtension() {}

    public static LoginCommand toLoginCommand(AuthLoginRequest request) {
        var command = new LoginCommand();
        BeanUtils.copyProperties(request, command);
        return command;
    }
}
