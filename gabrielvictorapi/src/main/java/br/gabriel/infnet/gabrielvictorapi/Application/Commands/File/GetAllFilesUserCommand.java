package br.gabriel.infnet.gabrielvictorapi.Application.Commands.File;

import java.util.List;
import java.util.Optional;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.GetAllFileUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotNull;

public class GetAllFilesUserCommand implements Command<List<GetAllFileUserDTO>> {
    @NotNull
    private Integer userRequestId;
    @NotNull
    private Integer userId;
    private Optional<FileTypeEnum> fileType;
    private Boolean content = true;
    public Integer getUserRequestId() {
        return userRequestId;
    }
    public void setUserRequestId(Integer userRequestId) {
        this.userRequestId = userRequestId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Optional<FileTypeEnum> getFileType() {
        return fileType;
    }
    public void setFileType(Optional<FileTypeEnum> fileType) {
        this.fileType = fileType;
    }
    public Boolean getContent() {
        return content;
    }
    public void setContent(Boolean content) {
        this.content = content;
    }
    
}
