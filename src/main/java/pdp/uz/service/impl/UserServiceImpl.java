package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.User;
import pdp.uz.entity.Warehouse;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.UserAddDto;
import pdp.uz.model.UserDto;
import pdp.uz.repository.UserRepo;
import pdp.uz.repository.WarehouseRepo;
import pdp.uz.service.UserService;
import pdp.uz.service.WarehouseService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final WarehouseRepo warehouseRepo;
    private final MapstructMapper mapstructMapper;
    private final WarehouseService warehouseService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, WarehouseRepo warehouseRepo, MapstructMapper mapstructMapper, WarehouseService warehouseService) {
        this.userRepo = userRepo;
        this.warehouseRepo = warehouseRepo;
        this.mapstructMapper = mapstructMapper;
        this.warehouseService = warehouseService;
    }

    @Override
    public UserDto add(UserAddDto dto) {
        String phoneNumber = dto.getPhoneNumber();
        if (Utils.isEmpty(phoneNumber)) {
            throw new RuntimeException("User phone number is should not be null!");
        } else {
            Optional<User> userOpt = userRepo.findByPhoneNumber(phoneNumber);
            if (userOpt.isPresent()) {
                throw new RuntimeException("This phone number is already exist!");
            }
        }

        Set<Warehouse> warehouses = new HashSet<>();

        for (Long warehouseId : dto.getWarehouseIds()) {
            if (Utils.isEmpty(warehouseId)) {
                continue;
            }
            Optional<Warehouse> warehouseOpt = warehouseRepo.findById(warehouseId);
            if (warehouseOpt.isPresent()) {
                if (warehouseService.active(warehouseOpt.get())) {
                    warehouses.add(warehouseOpt.get());
                }
            }
        }

        User user = mapstructMapper.toUser(dto);
        user.setCode(Utils.generateCode());
        user.setWarehouses(warehouses);

        User savedUser = userRepo.save(user);

        return mapstructMapper.toUserDto(savedUser);
    }
}
