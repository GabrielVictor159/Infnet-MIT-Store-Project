package com.store.domain.Shared.Converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        if (attribute.startsWith("$2a$") || attribute.startsWith("$2b$") || attribute.startsWith("$2y$")) {
            return attribute;
        }
        return passwordEncoder.encode(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
