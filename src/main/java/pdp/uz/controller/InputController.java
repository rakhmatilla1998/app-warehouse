package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pdp.uz.model.InputAddDto;
import pdp.uz.model.InputDto;
import pdp.uz.service.InputService;

@RestController
@RequestMapping("/api/input")
public class InputController {
    private final InputService inputService;

    @Autowired
    public InputController(InputService inputService) {
        this.inputService = inputService;
    }

    @PostMapping("/add")
    public InputDto add(@RequestBody InputAddDto dto){
        return inputService.add(dto);
    }
}
