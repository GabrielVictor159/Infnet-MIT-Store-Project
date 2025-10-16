// package br.gabriel.infnet.gabrielvictorapi.Api.Mappers.Owner;

// import org.springframework.beans.BeanUtils;

// import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Owner.CreateOwnerRequest;
// import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.CreateOwnerCommand;

// public final class OwnerMapperRequestsExtension {
//     public static CreateOwnerCommand toCreateOwnerCommand(CreateOwnerRequest req){
//         var command = new CreateOwnerCommand();
//         BeanUtils.copyProperties(req, command);
//         return command;
//     }
// }
