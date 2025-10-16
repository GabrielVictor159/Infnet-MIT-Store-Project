// package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Owner;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.CreateOwnerCommand;
// import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
// import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
// import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
// import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
// import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
// import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
// import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
// import jakarta.transaction.Transactional;

// @Component
// public class CreateOwnerHandler implements CommandHandler<CreateOwnerCommand, Integer> {

//     @Autowired
//     private UserRepository userRepository;
//     @Autowired
//     private OwnerRepository ownerRepository;

//     @Override
//     @Transactional
//     public Integer handle(CreateOwnerCommand command) {
//         var requestUser = userRepository.findById(command.getRequestUserId());
//         if(requestUser.get().getRule()!=UserRulesEnum.Admin){
//             throw new ForbiddenException("Você não tem permissão de criar um vendedor");
//         }

//         var user = userRepository.findById(command.getUserId());
//         if(!user.isPresent()){
//             throw new OperationException("Usuário não encontrado");
//         }
//         var newOwner = new Owner();
//         newOwner.setUser(user.get());

//         newOwner = ownerRepository.save(newOwner);
//         return newOwner.getId();
//     }
// }