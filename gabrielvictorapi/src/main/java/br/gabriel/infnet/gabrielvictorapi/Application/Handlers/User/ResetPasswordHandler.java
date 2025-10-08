package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.ResetPasswordCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class ResetPasswordHandler implements CommandHandler<ResetPasswordCommand, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public String handle(ResetPasswordCommand command) {

        if (!command.getPassword().equals(command.getConfirmPassword())) {
            throw new OperationException("As senhas não coincidem");
        }

        Optional<User> user = userRepository.findByAlterPasswordId(command.getId());
        if(!user.isPresent()){
            return "Não foi encontrado nenhuma solicitação com o id informado";
        }

        user.get().setAlterPasswordId(null);
        user.get().setPassword(command.getPassword());

        userRepository.save(user.get());
        return "Senha alterada com sucesso!";
    }
}
