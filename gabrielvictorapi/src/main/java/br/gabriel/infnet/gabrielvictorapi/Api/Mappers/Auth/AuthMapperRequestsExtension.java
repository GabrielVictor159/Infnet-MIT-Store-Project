package br.gabriel.infnet.gabrielvictorapi.Api.Mappers.Auth;

import org.springframework.beans.BeanUtils;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Auth.AuthLoginRequest;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Auth.LoginCommand;

public final class AuthMapperRequestsExtension {
    private AuthMapperRequestsExtension() {}

    public static LoginCommand toLoginCommand(AuthLoginRequest request) {
        var command = new LoginCommand();
        BeanUtils.copyProperties(request, command);
        return command;
    }
}
