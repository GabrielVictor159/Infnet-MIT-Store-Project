package br.gabriel.infnet.gabrielvictorapi.Application.DTO.File;

import java.time.LocalDateTime;
import java.util.UUID;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;

public class GetAllFileUserDTO {
    private UUID id;
    private String content;
    private String fileName;
    private FileTypeEnum fileType;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public FileTypeEnum getFileType() {
        return fileType;
    }
    public void setFileType(FileTypeEnum fileType) {
        this.fileType = fileType;
    }
    public LocalDateTime getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
