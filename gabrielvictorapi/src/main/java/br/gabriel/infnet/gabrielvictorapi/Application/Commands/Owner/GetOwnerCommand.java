package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Owner.GetOwnerDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;

public class GetOwnerCommand implements Command<GetOwnerDTO> {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
