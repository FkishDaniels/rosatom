package org.example.fileservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileRequest {
    private String title;
    private LocalDateTime creationDate;
    private String description;
    private String fileData;
}
