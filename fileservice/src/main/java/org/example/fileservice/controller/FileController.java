package org.example.fileservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.fileservice.dto.FileRequest;
import org.example.fileservice.dto.FileResponse;
import org.example.fileservice.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/api/v1/file-service")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestBody FileRequest fileRequest
    ){
        String fileId = fileService.uploadFile(fileRequest);
        return ResponseEntity.ok(fileId);
    }

    @GetMapping("/get")
    public FileResponse getFile(@RequestParam("id") String id){
        return fileService.getFile(id);
    }
}
