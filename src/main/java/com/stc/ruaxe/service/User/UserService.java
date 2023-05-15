package com.stc.ruaxe.service.User;

import com.stc.ruaxe.dtos.user.SigupDto;
import com.stc.ruaxe.dtos.user.UpdateUserDto;
import com.stc.ruaxe.entities.TaiKhoan;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface UserService {

    Page<TaiKhoan> filter(String search,
                      int page, int size, String sort, String column);

    List<TaiKhoan> finAll();

    TaiKhoan getUser(String id);

    void deleteById(String id);
    TaiKhoan getUserByEmail(String email);

    TaiKhoan create(SigupDto dto, Principal principal);

    //TaiKhoan update(String id, UserDto dto, Principal principal);

    TaiKhoan update(String id, UpdateUserDto dto, Principal principal);

    TaiKhoan changeStatus(String id);
}
