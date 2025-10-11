package br.gabriel.infnet.gabrielvictorapi.Application.Mappers.Files;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.GetAllFileUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.GetOneFileDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Files;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.DTO.File.FileNotContentDTO;

public final class FilesMapperResponseExtensions {

    public static List<GetAllFileUserDTO> toGetAllFileUserDTOFromFileNotContent(List<FileNotContentDTO> files) {
        List<GetAllFileUserDTO> result = new ArrayList<GetAllFileUserDTO>();
        for (FileNotContentDTO file : files) {
            result.add(toGetAllFileUserDTO(file));
        }
        return result;
    }

    public static GetAllFileUserDTO toGetAllFileUserDTO(FileNotContentDTO file) {
        GetAllFileUserDTO dto = new GetAllFileUserDTO();
        BeanUtils.copyProperties(file, dto);
        dto.setContent(null);
        return dto;
    }

    public static List<GetAllFileUserDTO> toGetAllFileUserDTO(List<Files> files) {
        List<GetAllFileUserDTO> result = new ArrayList<GetAllFileUserDTO>();
        for (Files file : files) {
            result.add(toGetAllFileUserDTO(file));
        }
        return result;
    }

    public static GetAllFileUserDTO toGetAllFileUserDTO(Files file) {
        GetAllFileUserDTO dto = new GetAllFileUserDTO();
        BeanUtils.copyProperties(file, dto);
        dto.setContent(Base64.getEncoder().encodeToString(file.getContent()));
        return dto;
    }

    public static GetOneFileDTO toGetOneFileDTO(Files file) {
        GetOneFileDTO dto = new GetOneFileDTO();
        BeanUtils.copyProperties(file, dto);
        dto.setContent(Base64.getEncoder().encodeToString(file.getContent()));
        return dto;
    }
}
