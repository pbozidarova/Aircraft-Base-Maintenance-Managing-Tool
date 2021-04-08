package managing.tool.e_notification.service.impl;

import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import managing.tool.e_notification.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

  private static final String TEMP_FILE = "temp-file";
  private static final String URL = "url";

  private final Cloudinary cloudinary;

  @Override
  public String uploadImage(MultipartFile multipartFile) throws IOException {
    File file = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
    multipartFile.transferTo(file);

    return this.cloudinary
        .uploader()
        .upload(file, Collections.emptyMap())
        .get(URL)
        .toString();
  }
}
