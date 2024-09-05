package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.fileservice.dto.FileRequest;
import org.example.fileservice.repository.FileRepository;
import org.example.fileservice.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FileControllerIntegrationTest.class)
@AutoConfigureMockMvc
public class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Test
    public void testUploadFile() throws Exception {
    FileRequest fileRequest = new FileRequest();
        fileRequest.setTitle("test.txt");
        fileRequest.setFileData("TWUgRmlsZQ==");
        fileRequest.setDescription("Test Description");
        fileRequest.setCreationDate(LocalDateTime.parse("2023-01-01T10:15:30"));

    when(fileService.uploadFile(any(FileRequest.class))).thenReturn("12345");

        mockMvc.perform(post("http://localhost:8082/api/v1/file-service/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fileRequest)))
            .andExpect(status().isOk());
}
}
