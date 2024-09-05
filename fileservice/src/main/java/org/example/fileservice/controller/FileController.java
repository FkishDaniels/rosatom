package org.example.fileservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.fileservice.dto.FileRequest;
import org.example.fileservice.dto.FileResponse;
import org.example.fileservice.service.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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

    @GetMapping("/get_all")
    public ResponseEntity<Page<FileResponse>> getAllFile(
            @RequestParam(value = "page",defaultValue = "0") int page
    ){
        Page<FileResponse> fileResponses = fileService.getAllFiles(PageRequest.of(page,10, Sort.by("creationDate")));
        return ResponseEntity.ok(fileResponses);
    }
}
