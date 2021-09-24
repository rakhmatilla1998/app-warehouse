package pdp.uz.service;

import pdp.uz.entity.Input;
import pdp.uz.model.InputAddDto;
import pdp.uz.model.InputDto;

import java.util.List;

public interface InputService {
    InputDto add(InputAddDto dto);

    List<InputDto> getInputs();

    InputDto getInput(Long id);

    InputDto deleteInput(Long id);

    InputDto edit(Long id, InputAddDto dto);
}
