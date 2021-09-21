package pdp.uz.service;

import pdp.uz.model.InputProductAddDto;
import pdp.uz.model.InputProductDto;

import java.util.List;

public interface InputProductService {
    InputProductDto add(InputProductAddDto dto);

    List<InputProductDto> addAll(List<InputProductAddDto> dto);
}
