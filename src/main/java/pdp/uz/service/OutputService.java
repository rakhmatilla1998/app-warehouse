package pdp.uz.service;

import pdp.uz.model.OutputAddDto;
import pdp.uz.model.OutputDto;

public interface OutputService {
    OutputDto add(OutputAddDto dto);
}
