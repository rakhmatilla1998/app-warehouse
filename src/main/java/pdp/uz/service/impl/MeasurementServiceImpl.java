package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Measurement;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.MeasurementAddDto;
import pdp.uz.model.MeasurementDto;
import pdp.uz.repository.MeasurementRepo;
import pdp.uz.service.MeasurementService;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepo measurementRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepo measurementRepo, MapstructMapper mapstructMapper) {
        this.measurementRepo = measurementRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public List<MeasurementDto> getAll() {
        List<Measurement> list = measurementRepo.findAll();
        return mapstructMapper.toMeasurementDto(list);
    }

    @Override
    public MeasurementDto add(MeasurementAddDto dto) {
        String name = dto.getName();
        if (Utils.isEmpty(name)) {
            throw new RuntimeException("Measurement name should not be null!");
        } else {
            Optional<Measurement> measurementOpt = measurementRepo.findByName(name);
            if (measurementOpt.isPresent()) {
                throw new RuntimeException("This name is already exist!");
            }
        }
        Measurement measurement = mapstructMapper.toMeasurement(dto);
        Measurement savedMeasurement = measurementRepo.save(measurement);
        return mapstructMapper.toMeasurementDto(savedMeasurement);
    }

    @Override
    public Measurement validate(Long id) {
        Optional<Measurement> measurementOpt = measurementRepo.findById(id);
        if (!measurementOpt.isPresent()) {
            throw new RuntimeException("Measurement id = " + id + ", not found!");
        }
        Measurement measurement = measurementOpt.get();
        if (!measurement.getActive()) {
            throw new RuntimeException("Measurement id = " + id + ", is inactive!");
        }
        return measurement;
    }
}
