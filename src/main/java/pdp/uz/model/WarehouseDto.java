package pdp.uz.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WarehouseDto implements Serializable {

    private Long id;

    private String name;

    private Boolean active;
}
