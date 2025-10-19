package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Product.CreateProductRequest;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.CreateProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Services.JwtTokenService;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/Product")
@CrossOrigin
public class ProductController {
    @Autowired
    private Mediator mediator;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/CreateProduct")
    public ResponseEntity<Object> createProduct(@RequestBody @Valid CreateProductRequest request,  HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        var command = new CreateProductCommand();
        command.setRequestUserId(idRequestUser);
        Integer result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
