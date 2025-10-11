package br.gabriel.infnet.gabrielvictorapi.Application.Commands.File;

import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.GetOneFileDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotNull;

public class GetOneFileCommand implements Command<GetOneFileDTO> {
    @NotNull
    private Integer userRequestId;
    @NotNull
    private UUID id;
    public Integer getUserRequestId() {
        return userRequestId;
    }
    public void setUserRequestId(Integer userRequestId) {
        this.userRequestId = userRequestId;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
   

    
}