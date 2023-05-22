package com.stc.ruaxe.service.users;

import com.stc.ruaxe.dtos.vehicle.VehicleDto;
import com.stc.ruaxe.dtos.user.SigupDto;
import com.stc.ruaxe.dtos.user.UpdateUserDto;
import com.stc.ruaxe.dtos.user.UserDto;
import com.stc.ruaxe.entities.Services;
import com.stc.ruaxe.entities.User;
import com.stc.ruaxe.entities.Vehicle;
import com.stc.ruaxe.exceptions.InvalidException;
import com.stc.ruaxe.exceptions.NotFoundException;
import com.stc.ruaxe.repositories.UserRepository;
import com.stc.ruaxe.repositories.VehicleRepository;
import com.stc.ruaxe.utils.EnumRole;
import com.stc.ruaxe.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.modelmapper.ModelMapper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;



    @Autowired
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return userRepository.findByNameContainingOrEmailContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",id)));                
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",id)));
    }

    // Xu ly jwt
    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
    


    @Override
    public User create(SigupDto dto, Principal principal) {
        User taiKhoan = new User();

        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Tên không được để trống");
        }
        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException("Email không được để trống");
        }
        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException("Password không được để trống");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new InvalidException(String.format("Email đã tồn tại, vui lòng nhập email khác",
                    dto.getEmail()));
        }
            taiKhoan.setName(dto.getName());
            taiKhoan.setEmail(dto.getEmail());
            taiKhoan.setPassword(dto.getPassword());
            taiKhoan.setRoles(Arrays.asList(EnumRole.ROLE_USER.name()));

        return userRepository.save(taiKhoan);

    }



    @Override
    public User update(String id, UpdateUserDto dto, Principal principal) {

        User taiKhoan = userRepository.findById(id).orElse(null);
        if (taiKhoan==null)
            throw new NotFoundException("Khong tim thay tai khoan");

        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Tên Không được bỏ trống");
        }
        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException("Mật khẩu không được để trống");
        }


        taiKhoan.setName(dto.getName());
        
        taiKhoan.setPassword(dto.getPassword());

        return userRepository.save(taiKhoan);
    }


    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public UserDto addVehicle(String userId, String vehicleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        // kt khóa ngoại tham chiếu có đúng hay không
        if (user.getVehicle() != null && user.getVehicle().stream().anyMatch(r -> r != null && r.getId().equals(vehicleId))) {
            throw new IllegalArgumentException("Reservation with ID " + vehicleId + " already exists for user with ID " + userId);
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + vehicleId));
//        tạo dữ liệu trong bảng vehicle
//        vehicle = vehicleRepository.save(vehicle);
        if (user.getVehicle() == null) {
            user.setVehicle(new ArrayList<>());
        }
        user.getVehicle().add(vehicle);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }
}
