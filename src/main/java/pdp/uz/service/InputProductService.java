package pdp.uz.service;

import pdp.uz.entity.InputProduct;
import pdp.uz.model.InputProductAddDto;
import pdp.uz.model.InputProductDto;
import pdp.uz.model.resp.ProductReport;

import java.time.LocalDate;
import java.util.List;

public interface InputProductService {
    InputProductDto add(InputProductAddDto dto);

    List<InputProductDto> addAll(List<InputProductAddDto> dto);

    List<ProductReport> get();

    List<ProductReport> get(String date);

    InputProductDto delete(Long id);

    InputProductDto edit(Long id, InputProductAddDto dto);

    InputProduct validate(Long id);
}
