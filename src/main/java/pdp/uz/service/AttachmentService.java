package pdp.uz.service;

import org.springframework.web.multipart.MultipartFile;
import pdp.uz.entity.Attachment;
import pdp.uz.model.AttachmentDto;

import java.io.IOException;

public interface AttachmentService {

    AttachmentDto upload(MultipartFile file) throws IOException;

    Attachment validate(Long id);

}
