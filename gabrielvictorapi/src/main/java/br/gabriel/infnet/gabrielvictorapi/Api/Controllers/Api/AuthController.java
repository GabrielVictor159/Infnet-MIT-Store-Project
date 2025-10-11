package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Auth.AuthLoginRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Mappers.Auth.AuthMapperRequestsExtension;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Auth.LoginDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/Auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private Mediator mediator;

     @PostMapping("/Login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthLoginRequest request) {
        var command = AuthMapperRequestsExtension.toLoginCommand(request);
        LoginDTO result = mediator.Handler(command);
        return new ResponseEntity<Object>(result.Token, HttpStatus.OK);
    }
}
