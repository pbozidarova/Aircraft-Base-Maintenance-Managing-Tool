package managing.tool.e_notification.service.impl;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class CloudinaryServiceImplTest {
    private final String ATTACHMENT_URL = "http://url";
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private MultipartFile attachment = new MockMultipartFile( "file",
            "hello.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes());

    CloudinaryServiceImpl testService;

    @Mock
    Cloudinary mockedCloudinary;

    @BeforeEach
    void setUp(){

        testService = new CloudinaryServiceImpl(mockedCloudinary);
    }

    @Test
    void uploadTest() throws IOException {
        File file = File.createTempFile(TEMP_FILE, TEMP_FILE);
        attachment.transferTo(file);
        Mockito.when(mockedCloudinary
                .uploader()
                .upload(file, Collections.singletonMap("resource_type", "auto"))
                .get(URL)
                .toString())
                .thenReturn(ATTACHMENT_URL);

        Assertions.assertTrue(testService.uploadImage(attachment).equals(ATTACHMENT_URL));
    }

}
