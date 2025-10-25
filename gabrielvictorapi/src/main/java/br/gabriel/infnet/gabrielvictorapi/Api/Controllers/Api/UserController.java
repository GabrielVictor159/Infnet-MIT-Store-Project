package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.User.AlterUserRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.User.CreateUserRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.User.ResetPasswordRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Mappers.User.UserMapperRequestsExtension;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.AlterUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.CreateUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.DisableUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.ForbiddenPasswordCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.GetUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.ResetPasswordCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.User.CreateUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Application.Services.JwtTokenService;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/User")
@CrossOrigin
public class UserController {
    @Autowired
    private Mediator mediator;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/GetUser/{id}")
    public ResponseEntity<Object> GetUser(@PathVariable Integer id,  HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        GetUserCommand command = new GetUserCommand();
        command.setRequestUser(idRequestUser);
        command.setUserId(id);
        var result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/CreateUser")
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserCommand command = UserMapperRequestsExtension.toCreateUserCommand(request);
        CreateUserDTO result = mediator.Handler(command);
        return new ResponseEntity<>(result.Message, HttpStatus.OK);
    }

    @PostMapping("/ForbiddenPassword")
    public ResponseEntity<Object> ForbiddenPassword(@RequestParam String email) {
        ForbiddenPasswordCommand command = new ForbiddenPasswordCommand();
        command.setEmail(email);

        mediator.Handler(command);
        return new ResponseEntity<>("Por favor verifique o email", HttpStatus.OK);
    }

    @PatchMapping("/AlterUser")
    public ResponseEntity<Object> AlterUser(@RequestBody @Valid AlterUserRequest request,  HttpServletRequest httpRequest) {
        AlterUserCommand command = UserMapperRequestsExtension.toAlterUserCommand(request);
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        command.setRequestUserId(idRequestUser);
        mediator.Handler(command);
        return new ResponseEntity<>("Usuário alterado com sucesso", HttpStatus.OK);
    }

    @PostMapping("/ResetPassword/{id}")
    public ResponseEntity<Object> ProcessReset(
            @PathVariable UUID id,
            @RequestBody ResetPasswordRequest request) {
        ResetPasswordCommand command = new ResetPasswordCommand();
        command.setId(id);
        command.setPassword(request.getPassword());
        command.setConfirmPassword(request.getConfirmPassword());

        String result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/DisableUser/{id}")
    public ResponseEntity<Object> DisableUser(@PathVariable Integer id, HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        var command = new DisableUserCommand();
        command.setId(id);
        command.setRequestUserId(idRequestUser);

        Boolean result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}
