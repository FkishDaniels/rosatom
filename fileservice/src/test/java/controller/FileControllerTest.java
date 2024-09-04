package controller;

import org.example.fileservice.controller.FileController;
import org.example.fileservice.dto.FileRequest;
import org.example.fileservice.dto.FileResponse;
import org.example.fileservice.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    @Mock
    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        fileController = new FileController(fileService);
    }

    @Test
    void uploadFileTest(){
        FileRequest fileRequest = new FileRequest();
        String expectedFileId = "12345";
        when(fileService.uploadFile(any(FileRequest.class))).thenReturn(expectedFileId);
        ResponseEntity<String> response = fileController.uploadFile(fileRequest);
        assertEquals(ResponseEntity.ok(expectedFileId), response);
    }

    @Test
    void getFileTest(){
        FileRequest fileRequest = new FileRequest();
        fileRequest.setFileData("TWUgRmlsZQ==");
        fileRequest.setTitle("Test Title");
        fileRequest.setDescription("Test Description");
        fileRequest.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));

        FileResponse file = new FileResponse();
        file.setTitle("Test Title");
        file.setDescription("Test Description");
        file.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));
        file.setFileData("TWUgRmlsZQ==");

        when(fileService.getFile(String.valueOf(1L))).thenReturn(file);
        FileResponse response = fileService.getFile(String.valueOf(1L));

        assertEquals(fileRequest.getFileData(), response.getFileData());
        assertEquals(fileRequest.getTitle(), response.getTitle());
        assertEquals(fileRequest.getDescription(), response.getDescription());
        assertEquals(fileRequest.getCreationDate(), response.getCreationDate());
    }

    @Test
    void getAllFilesTest(){
        // Arrange
        FileResponse file1 = FileResponse
                .builder()
                .fileData("TWUgRmlsZQ==")
                .title("Test Title1")
                .description("Test Description1")
                .creationDate(LocalDateTime.parse("2023-01-01T10:15:30"))
                .build();

        FileResponse file2 = FileResponse
                .builder()
                .fileData("TWUgRmlsZQ==")
                .title("Test Title2")
                .description("Test Description2")
                .creationDate(LocalDateTime.parse("2023-01-01T10:15:30"))
                .build();

        List<FileResponse> fileResponses = Arrays.asList(file1, file2);

        when(fileService.getAllFiles()).thenReturn(fileResponses);

        List<FileResponse> result = fileController.getAllFile();
        assertEquals(fileResponses, result);
    }
}
