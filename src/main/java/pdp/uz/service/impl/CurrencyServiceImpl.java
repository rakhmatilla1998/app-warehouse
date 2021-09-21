package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Currency;
import pdp.uz.entity.Supplier;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.CurrencyAddDto;
import pdp.uz.model.CurrencyDto;
import pdp.uz.repository.CurrencyRepo;
import pdp.uz.service.CurrencyService;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepo currencyRepo;
    private final MapstructMapper mapper;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepo currencyRepo, MapstructMapper mapper) {
        this.currencyRepo = currencyRepo;
        this.mapper = mapper;
    }

    @Override
    public List<CurrencyDto> get() {
        List<Currency> currencies = currencyRepo.findAll();
        return mapper.toCurrencyDto(currencies);
    }

    @Override
    public List<CurrencyDto> getActive() {
        List<Currency> currencies = currencyRepo.findAllByActiveTrue();
        return mapper.toCurrencyDto(currencies);
    }

    @Override
    public CurrencyDto add(CurrencyAddDto dto) {
        String name = dto.getName();
        if (Utils.isEmpty(name)) {
            throw new RuntimeException("Currency name should not be null!");
        }
        Optional<Currency> optionalCurrency = currencyRepo.findByNameAndActiveTrue(name);
        if (optionalCurrency.isPresent()){
            throw new RuntimeException("This currency has already existed");
        }
        Currency currency = mapper.toCurrency(dto);
        Currency savedCurrency = currencyRepo.save(currency);
        return mapper.toCurrencyDto(savedCurrency);
    }

    public Currency validate(Long id) {
        Optional<Currency> optionalCurrency = currencyRepo.findById(id);
        if (!optionalCurrency.isPresent()) {
            throw new RuntimeException("Currency id = " + id + ", not found!");
        }
        Currency currency = optionalCurrency.get();
        if (!currency.getActive()) {
            throw new RuntimeException("Currency id = " + id + ", is inactive!");
        }
        return currency;
    }
}
