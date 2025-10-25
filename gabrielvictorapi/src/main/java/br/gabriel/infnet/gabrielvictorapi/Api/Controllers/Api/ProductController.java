package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Api;

import java.util.List;
import java.util.Optional;

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

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Product.AlterProductRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Product.CreateProductRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Mappers.Product.ProductMapperRequestsExtension;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.AlterProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.CreateProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.DisableProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.GetProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.SearchProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Product.ProductDTO;
import br.gabriel.infnet.gabrielvictorapi.Application.Services.JwtTokenService;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.SearchPageSizeEnum;
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
        var command = ProductMapperRequestsExtension.toCreateProductCommand(request);
        command.setRequestUserId(idRequestUser);
        Integer result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/AlterProduct")
    public ResponseEntity<Object> alterProduct(@RequestBody @Valid AlterProductRequest request,  HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        var command = ProductMapperRequestsExtension.toAlterProductCommand(request);
        command.setRequestUserId(idRequestUser);
        Boolean result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/GetProduct/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable Integer id,  HttpServletRequest httpRequest) {
        var command = new GetProductCommand();
        command.setId(id);
        ProductDTO result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/SearchProduct")
    public ResponseEntity<Object> searchProduct(@RequestParam Optional<Integer> id, @RequestParam Optional<String> name, @RequestParam Integer page, @RequestParam SearchPageSizeEnum size,  HttpServletRequest httpRequest) {
        var command = new SearchProductCommand();
        command.setId(id);
        command.setName(name);
        List<ProductDTO> result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/DisableProduct/{id}")
    public ResponseEntity<Object> disableProduct(@PathVariable Integer id,  HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        var command = new DisableProductCommand();
        command.setId(id);
        command.setRequestUser(idRequestUser);
        Boolean result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
