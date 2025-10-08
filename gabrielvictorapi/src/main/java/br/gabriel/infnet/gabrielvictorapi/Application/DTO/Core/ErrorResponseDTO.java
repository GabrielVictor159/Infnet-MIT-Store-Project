package br.gabriel.infnet.gabrielvictorapi.Application.DTO.Core;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDTO {

    private final LocalDateTime timestamp;
    private final int status;
    private final List<String> errors;
    private final String message;
    private final String path;

    public ErrorResponseDTO(int status,  List<String> errors, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.errors = errors;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public List<String> getErrors() { return errors; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
}
