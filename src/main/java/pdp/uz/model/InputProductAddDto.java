package pdp.uz.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class InputProductAddDto {
    private Double amount;

    private Double price;

    private LocalDate expireDate;

    private Long productId;

    private Long inputId;
}
