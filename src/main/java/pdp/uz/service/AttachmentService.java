package pdp.uz.service;

import org.springframework.web.multipart.MultipartFile;
import pdp.uz.entity.Attachment;
import pdp.uz.model.AttachmentAddDto;
import pdp.uz.model.AttachmentDto;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {

    AttachmentDto upload(MultipartFile file) throws IOException;

    Attachment validate(Long id);

    List<AttachmentDto> getAttachments();

    AttachmentDto getAttachment(Long id);

    AttachmentDto delete(Long id);

    AttachmentDto edit(Long id, AttachmentAddDto dto);
}
