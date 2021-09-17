package pdp.uz.helpers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import pdp.uz.entity.User;
import pdp.uz.entity.Warehouse;
import pdp.uz.model.UserAddDto;
import pdp.uz.model.UserDto;
import pdp.uz.model.WarehouseAddDto;
import pdp.uz.model.WarehouseDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapstructMapper {

    Warehouse toWarehouse(WarehouseAddDto dto);

    WarehouseDto toWarehouseDto(Warehouse warehouse);

    List<WarehouseDto> toWarehouseDto(List<Warehouse> warehouses);

    User toUser(UserAddDto dto);

    UserDto toUserDto(User user);
}
