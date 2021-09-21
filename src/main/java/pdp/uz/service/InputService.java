package pdp.uz.service;

import pdp.uz.entity.Input;
import pdp.uz.model.InputAddDto;
import pdp.uz.model.InputDto;

public interface InputService {
    InputDto add(InputAddDto dto);
}
