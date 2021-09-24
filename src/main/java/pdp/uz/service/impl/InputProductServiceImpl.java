package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Input;
import pdp.uz.entity.InputProduct;
import pdp.uz.entity.Product;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.InputProductAddDto;
import pdp.uz.model.InputProductDto;
import pdp.uz.model.resp.ProductReport;
import pdp.uz.repository.InputProductRepo;
import pdp.uz.repository.InputRepo;
import pdp.uz.service.InputProductService;
import pdp.uz.service.InputService;
import pdp.uz.service.ProductService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InputProductServiceImpl implements InputProductService {
    private final InputProductRepo inputProductRepo;
    private final MapstructMapper mapper;
    private final ProductService productService;
    private final InputService inputService;
    private final InputRepo inputRepo;


    @Autowired
    public InputProductServiceImpl(InputProductRepo inputProductRepo, MapstructMapper mapper, ProductService productService, InputService inputService, InputRepo inputRepo) {
        this.inputProductRepo = inputProductRepo;
        this.mapper = mapper;
        this.productService = productService;
        this.inputService = inputService;
        this.inputRepo = inputRepo;
    }

    @Override
    public InputProductDto add(InputProductAddDto dto) {
        return mapper.toInputProductDto(addOne(dto));
    }

    @Override
    public List<InputProductDto> addAll(List<InputProductAddDto> dto) {
        List<InputProduct> inputProducts = new ArrayList<>();
        dto.forEach(inputProductAddDto -> inputProducts.add(addOne(inputProductAddDto)));

        List<InputProduct> inputProductList = inputProductRepo.saveAll(inputProducts);
        return mapper.toInputProductDto(inputProductList);
    }

    public InputProduct addOne(InputProductAddDto dto) {
        Long inputId = dto.getInputId();
        if (Utils.isEmpty(inputId)) {
            throw new RuntimeException("Input id should not be null!");
        }
        Long productId = dto.getProductId();
        if (Utils.isEmpty(productId)) {
            throw new RuntimeException("Product id should not be null!");
        }
        Double amount = dto.getAmount();
        if (Utils.isEmpty(amount)) {
            throw new RuntimeException("Amount should not be null!");
        }
        Double price = dto.getPrice();
        if (Utils.isEmpty(price)) {
            throw new RuntimeException("Price should not be null!");
        }
        Optional<Input> optionalInput = inputRepo.findById(inputId);
        if (!optionalInput.isPresent()) {
            throw new RuntimeException("Input id = " + inputId + " not found!");
        }

        Product product = productService.validate(productId);
        Input input = optionalInput.get();

        InputProduct inputProduct = new InputProduct();
        inputProduct.setProduct(product);
        inputProduct.setInput(input);
        inputProduct.setAmount(amount);
        inputProduct.setPrice(price);
        inputProduct.setExpireDate(dto.getExpireDate());
        return inputProductRepo.save(inputProduct);
    }

    @Override
    public List<ProductReport> get() {
        LocalDate today = LocalDate.now();
        return inputProductRepo.findAllByDate(today);
    }

    @Override
    public List<ProductReport> get(String date) {
        try {
            LocalDate parse = LocalDate.parse(date);
            return inputProductRepo.findAllByDate(parse);
        } catch (Exception e) {
            throw new RuntimeException("Wrong date format is included (User yyyy-mm-dd");
        }
    }

    @Override
    public InputProductDto delete(Long id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepo.findById(id);
        if (optionalInputProduct.isPresent()) {
            InputProduct inputProduct = optionalInputProduct.get();
            inputProductRepo.delete(inputProduct);

            return mapper.toInputProductDto(inputProduct);
        }
        return new InputProductDto();
    }

    @Override
    public InputProductDto edit(Long id, InputProductAddDto dto) {
        InputProduct validatedIP = validate(id);
        validatedIP.setProduct(productService.validate(dto.getProductId()));
        validatedIP.setAmount(dto.getAmount());
        validatedIP.setPrice(dto.getPrice());
        validatedIP.setExpireDate(dto.getExpireDate());

        inputProductRepo.save(validatedIP);

        return mapper.toInputProductDto(validatedIP);
    }

    @Override
    public InputProduct validate(Long id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepo.findById(id);
        if (!optionalInputProduct.isPresent()) {
            throw new RuntimeException("InputProduct id = " + id + ", not found!");
        }
        return optionalInputProduct.get();
    }

}
