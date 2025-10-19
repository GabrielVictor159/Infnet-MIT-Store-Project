package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Owner.AlterOwnerRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Owner.CreateOwnerRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Mappers.Owner.OwnerMapperRequestsExtension;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.AlterOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.CreateOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.DisableOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.GetOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.SeachOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Owner.GetOwnerDTO;
import br.gabriel.infnet.gabrielvictorapi.Application.Services.JwtTokenService;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.SearchPageSizeEnum;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/Owner")
@CrossOrigin
public class OwnerController {
    @Autowired
    private Mediator mediator;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/CreateOwner")
    public ResponseEntity<Object> createOwner(@RequestBody @Valid CreateOwnerRequest request,  HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        CreateOwnerCommand command = OwnerMapperRequestsExtension.toCreateOwnerCommand(request);
        command.setRequestUserId(idRequestUser);
        Integer result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/GetOwner/{id}")
    public ResponseEntity<Object> GetOwner(@PathVariable Integer id,  HttpServletRequest httpRequest) {
        GetOwnerCommand command = new GetOwnerCommand();
        command.setId(id);
        GetOwnerDTO result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/SearchOwner")
    public ResponseEntity<Object> SearchOwner(@RequestParam Optional<Integer> id, @RequestParam Optional<String> name,@RequestParam Optional<String> cnpj,@RequestParam Optional<String> address,
    @RequestParam Optional<String> cep, @RequestParam Optional<String> email, @RequestParam Integer page, @RequestParam SearchPageSizeEnum size,  HttpServletRequest httpRequest) {
        SeachOwnerCommand command = new SeachOwnerCommand();
        command.setId(id);
        command.setName(name);
        command.setCnpj(cnpj);
        command.setAddress(address);
        command.setCep(cep);
        command.setEmail(email);
        command.setPage(page);
        command.setSize(size);
        List<GetOwnerDTO> result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/AlterOwner")
    public ResponseEntity<Object> GetOwner(@RequestBody AlterOwnerRequest request,  HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        AlterOwnerCommand command = new AlterOwnerCommand();
        BeanUtils.copyProperties(request, command);
        command.setRequestUserId(idRequestUser);
        Boolean result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/DisableOwner/{id}")
    public ResponseEntity<Object> DisableOwner(@PathVariable Integer id,  HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        DisableOwnerCommand command = new DisableOwnerCommand();
        command.setRequestUser(idRequestUser);
        command.setId(id);
        Boolean result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
