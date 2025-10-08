package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.View;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.User.ResetPasswordRequest;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.ConfirmUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;

@Controller
public class UserViewController {
    @Autowired
    private Mediator mediator;

    @GetMapping("/View/reset-password/{id}")
    public String viewResetPasswordView(@PathVariable UUID id, Model model) {
        model.addAttribute("userId", id);
        model.addAttribute("resetForm", new ResetPasswordRequest());
        return "reset_password";
    }

    @GetMapping("/View/ConfirmUser/{id}")
    public String confirmUser(@PathVariable UUID id,Model model) {
        ConfirmUserCommand command = new ConfirmUserCommand();
        command.setId(id);
        String result = mediator.Handler(command);
        return result;
    }

}
