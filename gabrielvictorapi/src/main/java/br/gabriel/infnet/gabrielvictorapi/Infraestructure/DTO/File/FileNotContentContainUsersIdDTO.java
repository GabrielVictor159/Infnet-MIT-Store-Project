package br.gabriel.infnet.gabrielvictorapi.Infraestructure.DTO.File;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;

public class FileNotContentContainUsersIdDTO extends FileNotContentDTO{
    private List<Integer> usersIds;

    public FileNotContentContainUsersIdDTO(UUID id, String fileName, FileTypeEnum fileType, LocalDateTime insertDate,
            LocalDateTime updateDate,List<Integer> usersIds) {
        super(id, fileName, fileType, insertDate, updateDate);
        this.usersIds = usersIds;
    }
    
    public List<Integer> getUsersIds() {
        return usersIds;
    }

    public void setUsersIds(List<Integer> usersIds) {
        this.usersIds = usersIds;
    }
}



