package org.example.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.example.fileservice.dto.FileRequest;
import org.example.fileservice.dto.FileResponse;
import org.example.fileservice.model.File;
import org.example.fileservice.repository.FileRepository;
import org.springframework.stereotype.Service;



import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public String uploadFile(FileRequest fileRequest) {
        File file = mapFromDto(fileRequest);
        File savedFile = fileRepository.save(file);
        return String.valueOf(savedFile.getId());
    }

    public FileResponse getFile(String id) {
        File file = fileRepository.findById(Long.parseLong(id)).orElse(null);
        if(file == null) {
            throw new RuntimeException("File not found");
        }
        return mapToDto(file);
    }

    public List<FileResponse> getAllFiles() {
        return fileRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    private File mapFromDto(FileRequest dto) {
        File file = new File();

        byte[] decodedString = Base64.getDecoder().decode(dto.getFileData());
        file.setFileData(new String(decodedString));

        file.setCreationDate(dto.getCreationDate());
        file.setTitle(dto.getTitle());
        file.setDescription(dto.getDescription());
        return file;
    }

    private FileResponse mapToDto(File file) {
        FileResponse dto = new FileResponse();
        dto.setTitle(file.getTitle());
        dto.setDescription(file.getDescription());
        dto.setCreationDate(file.getCreationDate());

        String fileDate = Base64.getEncoder().encodeToString(file.getFileData().getBytes());
        dto.setFileData(fileDate);
        return dto;
    }


}
