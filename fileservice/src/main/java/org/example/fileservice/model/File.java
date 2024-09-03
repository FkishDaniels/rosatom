package org.example.fileservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "files")
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name ="creation_date")
    private LocalDateTime creationDate;
    @Column(name = "description")
    private String description;
    @Column(name="file_data")
    private String fileData;
}
