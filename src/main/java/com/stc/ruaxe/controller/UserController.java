package com.stc.ruaxe.controller;


import com.stc.ruaxe.dtos.vehicle.VehicleDto;
import com.stc.ruaxe.dtos.user.SigupDto;
import com.stc.ruaxe.dtos.user.UpdateUserDto;
import com.stc.ruaxe.dtos.user.UserDto;
import com.stc.ruaxe.entities.User;
import com.stc.ruaxe.service.users.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/user")
public class UserController {
    public final UserService userService;
    

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Get All USER")
    @GetMapping("/all")
    public List<User> getall() {

        return userService.findAll();
    }

    // Xử lý phân trang 
    private final int size = 5;
    private final String sort = "asc";    
    private final String column = "name";

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All USER Paging")
    @GetMapping("allPaging")
    public ResponseEntity<Page<User>> allPaging(@RequestParam(defaultValue = "") String search,
                                                @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(userService.filter(search,page,size,sort,column), HttpStatus.OK);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/find/{id}")
    public User findById(@PathVariable String id) {
        return userService.findById(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Create user")
    @PostMapping("/create")
    public ResponseEntity<User> create(@Valid @RequestBody SigupDto dto,
                                       Principal principal) {
        return new ResponseEntity<>(userService.create(dto, principal), HttpStatus.OK);
    }



    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Update user")
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable String id,
                                       @Valid @RequestBody UpdateUserDto dto,
                                       Principal principal) {
        return new ResponseEntity<>(userService.update(id,dto, principal), HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/{userId}/addxe/{vehicleId}")
    public ResponseEntity<UserDto> addReservationToUser(@PathVariable String userId, @PathVariable String vehicleId) {
        UserDto userDto = userService.addVehicle(userId, vehicleId);
        return ResponseEntity.ok(userDto);
    }

}
