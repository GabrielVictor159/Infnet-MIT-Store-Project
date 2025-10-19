package br.gabriel.infnet.gabrielvictorapi.Application.Commands.User;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.User.GetUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;

public class GetUserCommand implements Command<GetUserDTO> {
    private Integer RequestUser;
    private Integer UserId;
    public Integer getRequestUser() {
        return RequestUser;
    }
    public void setRequestUser(Integer requestUser) {
        RequestUser = requestUser;
    }
    public Integer getUserId() {
        return UserId;
    }
    public void setUserId(Integer userId) {
        UserId = userId;
    }

}
