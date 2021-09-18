package pdp.uz.service;

import pdp.uz.entity.Measurement;
import pdp.uz.model.MeasurementAddDto;
import pdp.uz.model.MeasurementDto;

import java.util.List;

public interface MeasurementService {

    List<MeasurementDto> getAll();

    MeasurementDto add(MeasurementAddDto dto);

    Measurement validate(Long id);
}
