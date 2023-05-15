package com.stc.ruaxe.controller;

import com.stc.ruaxe.dtos.loaithucung.LoaiThuCungDto;
import com.stc.ruaxe.entities.LoaiThuCung;
import com.stc.ruaxe.service.loaithucung.LoaiThuCungService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("rest/loaithucung")
public class LoaiThuCungController
{
    private final int size = 5;
    private final String sort = "asc";
    private final String search = "true";
    private final String column = "maLoaiThuCung";
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All Loai Thu Cung Paging")
    @GetMapping("allPaging")
    public ResponseEntity<Page<LoaiThuCung>> allPaging(@RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(loaiThuCungService.filter(search,page,size,sort,column), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Get All Loai Thu cung")
    @GetMapping("/all")
    public List<LoaiThuCung> all() {

        return loaiThuCungService.finfAll();
    }
    public final LoaiThuCungService loaiThuCungService;
    @ApiOperation(value ="Create Loai Thu cung")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<LoaiThuCung> create(@Valid @RequestBody LoaiThuCungDto dto){
        return new ResponseEntity<>(loaiThuCungService.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value ="Update LoaiThuCung")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<LoaiThuCung> update(@PathVariable String id,
                                         @RequestBody LoaiThuCungDto dto){
        return new ResponseEntity<>(loaiThuCungService.update(id,dto), HttpStatus.OK);
    }
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String maLoaiThuCung){
        loaiThuCungService.changeStatus(maLoaiThuCung);
        //Optional<DichVu> dichVu=dichVuService.getDichVuByMaDichVu(maDichVu);
        LoaiThuCung loaiThuCung=loaiThuCungService.getLoaiThuCungByMaLoaiThuCung(maLoaiThuCung);
        // TaiKhoan taiKhoan = userService.getUser(id);
        return new ResponseEntity<>(String.format("Mã loại thú cưng %s da duoc thay doi trang thai thanh %s"
                , loaiThuCung.getMaLoaiThuCung(), loaiThuCung.isTrangThai()), HttpStatus.OK);
    }
}
