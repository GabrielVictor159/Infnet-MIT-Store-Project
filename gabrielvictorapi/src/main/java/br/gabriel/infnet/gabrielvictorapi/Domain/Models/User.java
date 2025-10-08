package br.gabriel.infnet.gabrielvictorapi.Domain.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Shared.Converters.PasswordConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Convert(converter = PasswordConverter.class)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = false)
    private LocalDateTime insertAt = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Column(nullable = false)
    private Boolean isActive = true;
    @Column(nullable = true)
    private UUID createId;
    @Column(nullable = true)
    private UUID alterPasswordId;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "user_files", 
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id") 
    )
    private List<Files> files = new ArrayList<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public UUID getCreateId() {
        return createId;
    }

    public void setCreateId(UUID createId) {
        this.createId = createId;
    }

    public UUID getAlterPasswordId() {
        return alterPasswordId;
    }

    public void setAlterPasswordId(UUID alterPasswordId) {
        this.alterPasswordId = alterPasswordId;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    
}
