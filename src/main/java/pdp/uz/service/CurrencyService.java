package pdp.uz.service;

import pdp.uz.entity.Currency;
import pdp.uz.model.CurrencyAddDto;
import pdp.uz.model.CurrencyDto;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDto> get();

    List<CurrencyDto> getActive();

    CurrencyDto add(CurrencyAddDto dto);

    Currency validate(Long id);
}
