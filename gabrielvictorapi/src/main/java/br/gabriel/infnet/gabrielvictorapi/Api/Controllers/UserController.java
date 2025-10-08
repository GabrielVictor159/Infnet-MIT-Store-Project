package br.gabriel.infnet.gabrielvictorapi.Api.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.User.CreateUserRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Mappers.User.UserMapperExtension;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.ConfirmUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.CreateUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.User.CreateUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/User")
@CrossOrigin
public class UserController {
    @Autowired
    private Mediator mediator;

    @PostMapping("/CreateUser")
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserCommand command = UserMapperExtension.toCreateUserCommand(request);
        CreateUserDTO result = mediator.Handler(command);
        return new ResponseEntity<Object>(result.Message, HttpStatus.OK);
    }

    @GetMapping("/ConfirmUser/{id}")
    public ResponseEntity<Object> confirmUser(@PathVariable UUID id) {
        ConfirmUserCommand command = new ConfirmUserCommand();
        command.setId(id);
        String result = mediator.Handler(command);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
