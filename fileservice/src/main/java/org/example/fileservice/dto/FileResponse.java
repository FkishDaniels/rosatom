package org.example.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {
    private String title;
    private LocalDateTime creationDate;
    private String description;
    private String fileData;
}
