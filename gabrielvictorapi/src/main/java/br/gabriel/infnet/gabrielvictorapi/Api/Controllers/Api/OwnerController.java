// package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Api;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Owner.CreateOwnerRequest;
// import br.gabriel.infnet.gabrielvictorapi.Api.Mappers.Owner.OwnerMapperRequestsExtension;
// import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.CreateOwnerCommand;
// import br.gabriel.infnet.gabrielvictorapi.Application.Services.JwtTokenService;
// import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.validation.Valid;

// @RestController
// @RequestMapping(value = "/Owner")
// @CrossOrigin
// public class OwnerController {
//     @Autowired
//     private Mediator mediator;
//     @Autowired
//     private JwtTokenService jwtTokenService;

//     @PostMapping("/CreateOwner")
//     public ResponseEntity<Object> createOwner(@RequestBody @Valid CreateOwnerRequest request,  HttpServletRequest httpRequest) {
//         var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
//         CreateOwnerCommand command = OwnerMapperRequestsExtension.toCreateOwnerCommand(request);
//         command.setRequestUserId(idRequestUser);
//         Integer result = mediator.Handler(command);
//         return new ResponseEntity<Object>(result, HttpStatus.OK);
//     }
// }
