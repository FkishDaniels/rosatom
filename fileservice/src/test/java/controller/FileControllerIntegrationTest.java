package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.fileservice.FileserviceApplication;
import org.example.fileservice.dto.FileRequest;
import org.example.fileservice.dto.FileResponse;
import org.example.fileservice.repository.FileRepository;
import org.example.fileservice.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest( classes = FileserviceApplication.class)
@AutoConfigureMockMvc
@Testcontainers
public class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("fileservice/src/test/init.sql");
    static {
        postgresContainer.start();
    }
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

    @Test
    void getFile() throws Exception {
        String fileId = "12345";
        FileResponse fileResponse = new FileResponse("Test File", LocalDateTime.parse("2023-01-01T10:15:30"), "Test Description", "2023-01-01T00:00:00");


        when(fileService.getFile(anyString())).thenReturn(fileResponse);

        mockMvc.perform(get("/api/v1/file-service/get?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test File"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetAllFiles() throws Exception {
        FileResponse fileResponse1 = FileResponse.builder()
                .title("File 1")
                .creationDate(LocalDateTime.now())
                .description("Description 1")
                .fileData("fileData1")
                .build();

        FileResponse fileResponse2 = FileResponse.builder()
                .title("File 2")
                .creationDate(LocalDateTime.now())
                .description("Description 2")
                .fileData("fileData2")
                .build();

        Page<FileResponse> page = new PageImpl<>(Arrays.asList(fileResponse1, fileResponse2),
                PageRequest.of(0, 10), 2);

        when(fileService.getAllFiles(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/file-service/get_all?page=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("File 1"))
                .andExpect(jsonPath("$.content[1].title").value("File 2"));
    }
}
