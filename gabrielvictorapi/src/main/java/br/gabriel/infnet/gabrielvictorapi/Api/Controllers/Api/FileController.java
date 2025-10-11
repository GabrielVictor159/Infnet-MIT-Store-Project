package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.DeleteOneFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.GetAllFilesUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.GetOneFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.UploadFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Services.JwtTokenService;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/File")
@CrossOrigin
public class FileController {
    @Autowired
    private Mediator mediator;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/GetAllUser")
    public ResponseEntity<Object> getAllUser(@RequestParam(required = true) Integer userId,
            @RequestParam Optional<FileTypeEnum> fileType, @RequestParam Optional<Boolean> content,
            HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        var command = new GetAllFilesUserCommand();
        command.setUserRequestId(idRequestUser);
        command.setUserId(userId);
        command.setFileType(fileType);
        if (content.isPresent())
            command.setContent(content.get());
        var result = mediator.Handler(command);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @GetMapping("/GetOne/{id}")
    public ResponseEntity<Object> getAllUser(@PathVariable UUID id,
            HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        var command = new GetOneFileCommand();
        command.setUserRequestId(idRequestUser);
        command.setId(id);
        var result = mediator.Handler(command);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/UploadMultipart", consumes = "multipart/form-data")
    public ResponseEntity<Object> uploadMultipart(
        @RequestParam Optional<List<Integer>> usersId,
        @RequestParam Optional<List<Integer>> productsId,
        @RequestParam FileTypeEnum fileType,
        @Parameter(
            description = "Arquivo para upload",
            schema = @Schema(type = "string", format = "binary")
        )
        @RequestPart MultipartFile file,
        HttpServletRequest httpRequest) throws Exception {

        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
  
        var command = new UploadFileCommand();
        command.setUserRequestId(idRequestUser);
        command.setUsersId(usersId.isPresent() ? usersId.get(): new ArrayList<Integer>());
        command.setProductsId(productsId.isPresent() ? productsId.get(): new ArrayList<Integer>());
        command.setFileType(fileType);
        command.setFileName(file.getOriginalFilename());
        command.setContent(file.getBytes());

        var result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/UploadBase64")
    public ResponseEntity<Object> uploadBase64(
            @RequestParam Optional<List<Integer>> usersId,
            @RequestParam Optional<List<Integer>> productsId,
            @RequestParam FileTypeEnum fileType,
            @RequestParam String fileName,
            @RequestBody String base64Content,
            HttpServletRequest httpRequest) {

        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        byte[] decoded = java.util.Base64.getDecoder().decode(base64Content);

        var command = new UploadFileCommand();
        command.setUserRequestId(idRequestUser);
        command.setUsersId(usersId.isPresent() ? usersId.get(): new ArrayList<Integer>());
        command.setProductsId(productsId.isPresent() ? productsId.get(): new ArrayList<Integer>());
        command.setFileType(fileType);
        command.setFileName(fileName);
        command.setContent(decoded);

        var result = mediator.Handler(command);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/DeleteOne/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable UUID id,
            HttpServletRequest httpRequest) {
        var idRequestUser = jwtTokenService.getIdFromHttpRequest(httpRequest);
        var command = new DeleteOneFileCommand();
        command.setRequestUser(idRequestUser);
        command.setId(id);
        var result = mediator.Handler(command);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
