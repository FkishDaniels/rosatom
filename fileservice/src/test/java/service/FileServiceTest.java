package service;


import org.example.fileservice.dto.FileRequest;
import org.example.fileservice.dto.FileResponse;
import org.example.fileservice.model.File;
import org.example.fileservice.repository.FileRepository;
import org.example.fileservice.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;
    private FileService fileService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        fileService = new FileService(fileRepository);
    }

    @Test
    void uploadFileTest(){
        FileRequest fileRequest = new FileRequest();
        fileRequest.setFileData(Base64.getEncoder().encodeToString("TWUgRmlsZQ==".getBytes(StandardCharsets.UTF_8)));
        fileRequest.setTitle("Test Title");
        fileRequest.setDescription("Test Description");
        fileRequest.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));

        File file = new File();
        file.setId(1L);
        file.setTitle("Test Title");
        file.setDescription("Test Description");
        file.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));
        file.setFileData("Test data");

        when(fileRepository.save(any(File.class))).thenReturn(file);

        String fileId = fileService.uploadFile(fileRequest);
        assertEquals("1", fileId);
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    void getFileByIdTest(){
        FileRequest fileRequest = new FileRequest();
        fileRequest.setFileData(Base64.getEncoder().encodeToString("TWUgRmlsZQ==".getBytes(StandardCharsets.UTF_8)));
        fileRequest.setTitle("Test Title");
        fileRequest.setDescription("Test Description");
        fileRequest.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));

        File file = new File();
        file.setId(1L);
        file.setTitle("Test Title");
        file.setDescription("Test Description");
        file.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));
        file.setFileData("Test data");

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));
        when(fileRepository.findById(2L)).thenReturn(Optional.empty());
        org.example.fileservice.dto.FileResponse response = fileService.getFile("1");

        assertNotNull(response);
        assertEquals(file.getTitle(), response.getTitle());
        assertEquals(file.getDescription(), response.getDescription());
        assertEquals(file.getCreationDate(), response.getCreationDate());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileService.getFile("2");
        });

        assertEquals("File not found", exception.getMessage());
    }

    @Test
    void getAllFilesTest(){
        File file1 = new File();
        file1.setId(1L);
        file1.setTitle("File 1");
        file1.setDescription("Description 1");
        file1.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));
        file1.setFileData("data1");

        File file2 = new File();
        file2.setId(2L);
        file2.setTitle("File 2");
        file2.setDescription("Description 2");
        file2.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));
        file2.setFileData("data2");

        List<File> files = Arrays.asList(file1, file2);

        when(fileRepository.findAll()).thenReturn(files);

        List<FileResponse> fileResponses = fileService.getAllFiles();

        assertEquals(2, fileResponses.size());
        assertEquals("File 1", fileResponses.get(0).getTitle());
        assertEquals("File 2", fileResponses.get(1).getTitle());
    }
}
