package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pdp.uz.model.InputAddDto;
import pdp.uz.model.InputDto;
import pdp.uz.model.OutputAddDto;
import pdp.uz.model.OutputDto;
import pdp.uz.service.InputService;
import pdp.uz.service.OutputService;

@RestController
@RequestMapping("/api/output")
public class OutputController {
    private final OutputService outputService;

    @Autowired
    public OutputController(OutputService outputService) {
        this.outputService = outputService;
    }

    @PostMapping("/add")
    public OutputDto add(@RequestBody OutputAddDto dto){
        return outputService.add(dto);
    }
}
