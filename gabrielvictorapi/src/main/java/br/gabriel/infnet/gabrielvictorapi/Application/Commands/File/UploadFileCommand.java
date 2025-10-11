package br.gabriel.infnet.gabrielvictorapi.Application.Commands.File;

import java.util.List;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.UploadFileDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UploadFileCommand implements Command<UploadFileDTO>  {
    @NotNull
    private Integer userRequestId;
    private List<Integer> usersId;
    private List<Integer> productsId;
    @NotEmpty
    private byte[] content;
    @NotNull
    private FileTypeEnum fileType;
    @NotNull
    private String fileName;
    public Integer getUserRequestId() {
        return userRequestId;
    }
    public void setUserRequestId(Integer userRequestId) {
        this.userRequestId = userRequestId;
    }
    public List<Integer> getUsersId() {
        return usersId;
    }
    public void setUsersId(List<Integer> usersId) {
        this.usersId = usersId;
    }
    public List<Integer> getProductsId() {
        return productsId;
    }
    public void setProductsId(List<Integer> productsId) {
        this.productsId = productsId;
    }
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }
    public FileTypeEnum getFileType() {
        return fileType;
    }
    public void setFileType(FileTypeEnum fileType) {
        this.fileType = fileType;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
