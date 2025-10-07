package com.store.domain.Domain.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.store.domain.Domain.Enums.FileTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;
    @Lob
    @Column(nullable = true)
    private Byte[] content;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileTypeEnum fileType;
    @Column(nullable = false)
    private LocalDateTime InsertDate = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime UpdateDate = LocalDateTime.now();

    @ManyToMany(mappedBy = "files")
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    @ManyToMany(mappedBy = "files")
    @JsonBackReference
    private List<Product> products = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Byte[] getContent() {
        return content;
    }

    public void setContent(Byte[] content) {
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
        return InsertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        InsertDate = insertDate;
    }

    public LocalDateTime getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        UpdateDate = updateDate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
