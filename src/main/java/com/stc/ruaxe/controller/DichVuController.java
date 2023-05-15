package com.stc.ruaxe.controller;

import com.stc.ruaxe.dtos.dichvu.DichVuDto;
import com.stc.ruaxe.entities.DichVu;
import com.stc.ruaxe.entities.embedded.GiaDichVu;
import com.stc.ruaxe.service.dichvu.DichVuService;
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
@RequestMapping("rest/dichvu")
public class DichVuController {
    public final DichVuService dichVuService;
    private final int size = 5;
    private final String sort = "asc";
    private final String search = "true";
    private final String column = "tenDichVu";


    @ApiOperation(value ="Create Dich vu")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<DichVu> create(@Valid @RequestBody DichVuDto dto, Principal principal){
        return new ResponseEntity<>(dichVuService.create(dto,principal), HttpStatus.OK);
    }


    @ApiOperation(value ="Update Gia Dich vu theo maDichVu")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<DichVu> update(@PathVariable String id,
                                           @RequestBody DichVuDto dto,Principal principal){
        return new ResponseEntity<>(dichVuService.update(id,dto,principal), HttpStatus.OK);
    }

    @ApiOperation(value ="Update DichVu")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/addGiaDV/{maDichVu}")
    public ResponseEntity<DichVu> addGiaDV(@PathVariable String maDichVu,
                                           @RequestBody GiaDichVu giaDichVu){
        return new ResponseEntity<>(dichVuService.addGiaDV(maDichVu,giaDichVu), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All DichVu")
    @GetMapping("/all")
    public List<DichVu> getDichVu() {

        return dichVuService.finfAll();
    }
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String maDichVu){
        dichVuService.changeStatus(maDichVu);
        //Optional<DichVu> dichVu=dichVuService.getDichVuByMaDichVu(maDichVu);
        DichVu dichVu=dichVuService.getDichVuByMaDichVu(maDichVu);
       // TaiKhoan taiKhoan = userService.getUser(id);
        return new ResponseEntity<>(String.format("Dịch Vụ %s da duoc thay doi trang thai thanh %s"
                , dichVu.getTenDichVu(), dichVu.isTrangThai()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All USER Paging")
    @GetMapping("allPaging")
    public ResponseEntity<Page<DichVu>> getDichVuPaging(@RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(dichVuService.filter(search,page,size,sort,column), HttpStatus.OK);
    }



}
