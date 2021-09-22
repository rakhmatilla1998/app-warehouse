package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pdp.uz.entity.Attachment;
import pdp.uz.entity.AttachmentContent;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.model.AttachmentAddDto;
import pdp.uz.model.AttachmentDto;
import pdp.uz.repository.AttachmentContentRepo;
import pdp.uz.repository.AttachmentRepo;
import pdp.uz.service.AttachmentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepo attachmentRepo;
    private final AttachmentContentRepo attachmentContentRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepo attachmentRepo, AttachmentContentRepo attachmentContentRepo, MapstructMapper mapstructMapper) {
        this.attachmentRepo = attachmentRepo;
        this.attachmentContentRepo = attachmentContentRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public AttachmentDto upload(MultipartFile file) throws IOException {
        // Attachment view object
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());

        // Attachment view save to database
        Attachment createdAttachment = attachmentRepo.save(attachment);

        // Attachment content object
        AttachmentContent content = new AttachmentContent();
        content.setData(file.getBytes());
        content.setAttachment(createdAttachment);

        // Attachment content save to database
        attachmentContentRepo.save(content);

        return mapstructMapper.toAttachmentDto(createdAttachment);
    }

    @Override
    public Attachment validate(Long id) {
        Optional<Attachment> attachmentOpt = attachmentRepo.findById(id);
        if (!attachmentOpt.isPresent()) {
            throw new RuntimeException("Attachment id = " + id + ", not found!");
        }
        return attachmentOpt.get();
    }

    @Override
    public List<AttachmentDto> getAttachments() {
        return mapstructMapper.toAttachmentDto(attachmentRepo.findAll());
    }

    @Override
    public AttachmentDto getAttachment(Long id) {
        Attachment attachment = validate(id);
        return mapstructMapper.toAttachmentDto(attachment);
    }

    @Override
    public AttachmentDto delete(Long id) {
        Attachment attachment = validate(id);
        AttachmentDto attachmentDto = mapstructMapper.toAttachmentDto(attachment);

        attachmentRepo.delete(attachment);

        return attachmentDto;
    }

    @Override
    public AttachmentDto edit(Long id, AttachmentAddDto dto) {
        Attachment attachment = validate(id);

        attachment.setContentType(dto.getContentType());
        attachment.setName(dto.getName());
        attachment.setSize(dto.getSize());

        Attachment savedAttachment = attachmentRepo.save(attachment);

        return mapstructMapper.toAttachmentDto(savedAttachment);
    }

}
