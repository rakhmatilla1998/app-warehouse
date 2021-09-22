package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pdp.uz.model.AttachmentAddDto;
import pdp.uz.model.AttachmentDto;
import pdp.uz.service.AttachmentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping(value = "/upload")
    private AttachmentDto upload(@RequestParam MultipartFile file) throws IOException {
        return attachmentService.upload(file);
    }

    @GetMapping(value = "/getAttachments")
    private List<AttachmentDto> getAttachments() {
        return attachmentService.getAttachments();
    }

    @GetMapping(value = "/getAttachment/{id}")
    private AttachmentDto getAttachment(@PathVariable Long id) {
        return attachmentService.getAttachment(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    private AttachmentDto delete(@PathVariable Long id) {
        return attachmentService.delete(id);
    }

    @PutMapping(value = "/edit/{id}")
    private AttachmentDto edit(@PathVariable Long id, @RequestBody AttachmentAddDto dto) {
        return attachmentService.edit(id, dto);
    }
}
