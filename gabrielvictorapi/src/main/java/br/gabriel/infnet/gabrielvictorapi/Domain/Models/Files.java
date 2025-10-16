package br.gabriel.infnet.gabrielvictorapi.Domain.Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;
    @Column(name = "content", columnDefinition = "bytea")
    private byte[] content;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileTypeEnum fileType;
    @Column(nullable = false)
    private LocalDateTime insertDate = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime updateDate = LocalDateTime.now();

    @ManyToMany(mappedBy = "files")
    @JsonBackReference
    private Set<Owner> owners = new HashSet<>();

    @ManyToMany(mappedBy = "files")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "files")
    @JsonBackReference
    private Set<Product> products = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
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

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }



}
