package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.model.InputAddDto;
import pdp.uz.model.InputDto;
import pdp.uz.service.InputService;

import java.util.List;

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

    @GetMapping(value = "/getInputs")
    private List<InputDto> getInputs() {
        return inputService.getInputs();
    }

    @GetMapping(value = "/getInput/{id}")
    private InputDto getInput(@PathVariable Long id) {
        return inputService.getInput(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    private InputDto deleteInput(@PathVariable Long id) {
        return inputService.deleteInput(id);
    }

    @PutMapping(value = "/edit/{id}")
    private InputDto editInput(@PathVariable Long id, @RequestBody InputAddDto dto) {
        return inputService.edit(id, dto);
    }
}
