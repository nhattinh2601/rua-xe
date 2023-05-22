package com.stc.ruaxe.service.users;

import com.stc.ruaxe.dtos.vehicle.VehicleDto;
import com.stc.ruaxe.dtos.user.SigupDto;
import com.stc.ruaxe.dtos.user.UpdateUserDto;
import com.stc.ruaxe.dtos.user.UserDto;
import com.stc.ruaxe.entities.User;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface UserService {

    Page<User> filter(String search,
                      int page, int size, String sort, String column);

    List<User> findAll();

    User getUser(String id);

    User findById(String id);

    void deleteById(String id);
    public User getUserByEmail(String email);

    User create(SigupDto dto, Principal principal);



    User update(String id, UpdateUserDto dto, Principal principal);


    public UserDto addVehicle(String userId, String vehicleId);
}
