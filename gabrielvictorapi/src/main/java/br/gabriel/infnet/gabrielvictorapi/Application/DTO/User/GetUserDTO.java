package br.gabriel.infnet.gabrielvictorapi.Application.DTO.User;

import java.time.LocalDateTime;
import java.util.Date;

import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;

public class GetUserDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private Date birthDate;
    private UserRulesEnum rule;
    private LocalDateTime insertAt;
    private LocalDateTime updatedAt;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public UserRulesEnum getRule() {
        return rule;
    }
    public void setRule(UserRulesEnum rule) {
        this.rule = rule;
    }
    public LocalDateTime getInsertAt() {
        return insertAt;
    }
    public void setInsertAt(LocalDateTime insertAt) {
        this.insertAt = insertAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
