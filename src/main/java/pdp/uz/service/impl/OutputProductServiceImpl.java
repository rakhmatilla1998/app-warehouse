package pdp.uz.service.impl;

import org.springframework.stereotype.Service;
import pdp.uz.entity.*;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.OutputProductAddDto;
import pdp.uz.model.OutputProductDto;
import pdp.uz.repository.OutputProductRepo;
import pdp.uz.repository.OutputRepo;
import pdp.uz.service.OutputProductService;
import pdp.uz.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OutputProductServiceImpl implements OutputProductService {
    private final OutputProductRepo outputProductRepo;
    private final MapstructMapper mapper;
    private final OutputRepo outputRepo;
    private final ProductService productService;

    public OutputProductServiceImpl(OutputProductRepo outputProductRepo, MapstructMapper mapper, OutputRepo outputRepo, ProductService productService) {
        this.outputProductRepo = outputProductRepo;
        this.mapper = mapper;
        this.outputRepo = outputRepo;
        this.productService = productService;
    }

    @Override
    public OutputProductDto add(OutputProductAddDto dto) {
        return mapper.toOutputProductDto(addOne(dto));
    }

    @Override
    public List<OutputProductDto> addAll(List<OutputProductAddDto> dto) {
        List<OutputProduct> outputProducts = new ArrayList<>();
        dto.forEach(outputProductAddDto -> outputProducts.add(addOne(outputProductAddDto)));
        List<OutputProduct> outputProductList = outputProductRepo.saveAll(outputProducts);
        return mapper.toOutputProductDto(outputProductList);
    }

    public OutputProduct addOne(OutputProductAddDto dto){
        Long outputId = dto.getOutputId();
        if (Utils.isEmpty(outputId)){
            throw new RuntimeException("Output id should not be null!");
        }
        Long productId = dto.getProductId();
        if (Utils.isEmpty(productId)){
            throw new RuntimeException("Product id should not be null!");
        }
        Double amount = dto.getAmount();
        if (Utils.isEmpty(amount)){
            throw new RuntimeException("Amount should not be null!");
        }
        Double price = dto.getPrice();
        if (Utils.isEmpty(price)){
            throw new RuntimeException("Price should not be null!");
        }
        Optional<Output> optionalOutput = outputRepo.findById(outputId);
        if (!optionalOutput.isPresent()){
            throw new RuntimeException("Output id = " + outputId + " not found!");
        }

        Product product = productService.validate(productId);
        Output output = optionalOutput.get();

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setProduct(product);
        outputProduct.setOutput(output);
        outputProduct.setAmount(amount);
        outputProduct.setPrice(price);
        return outputProductRepo.save(outputProduct);
    }
}
