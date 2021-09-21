package pdp.uz.service;

import pdp.uz.model.OutputProductAddDto;
import pdp.uz.model.OutputProductDto;

import java.util.List;

public interface OutputProductService {
    OutputProductDto add(OutputProductAddDto dto);

    List<OutputProductDto> addAll(List<OutputProductAddDto> dto);
}
