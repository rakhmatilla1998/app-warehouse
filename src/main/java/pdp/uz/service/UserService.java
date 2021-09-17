package pdp.uz.service;

import pdp.uz.model.UserAddDto;
import pdp.uz.model.UserDto;

public interface UserService {

    UserDto add(UserAddDto dto);
}
