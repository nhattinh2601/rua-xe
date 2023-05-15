package com.stc.ruaxe.controller;

import com.stc.ruaxe.dtos.datcho.DatChoDto;
import com.stc.ruaxe.entities.DatCho;
import com.stc.ruaxe.entities.embedded.ThongTinDatCho;
import com.stc.ruaxe.service.datcho.DatChoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("rest/datcho")
public class DatChoController {
    public final DatChoService datChoService;
    private final int size = 5;
    private final String sort = "asc";
    private final String search = "true";
    private final String column = "email";


    @ApiOperation(value ="Create DatCho")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/create")
    public ResponseEntity<DatCho> create(@Valid @RequestBody DatChoDto dto){
        return new ResponseEntity<>(datChoService.create(dto), HttpStatus.OK);
    }
    @ApiOperation(value ="Add Thong tin Dat Cho")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/addThongTinDC/{id}")
    public ResponseEntity<DatCho> addThongTinDC(@PathVariable String id,
                                           @RequestBody ThongTinDatCho thongTinDatCho){
        return new ResponseEntity<>(datChoService.addThongTinDatCho(id,thongTinDatCho), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All DatCho Paging")
    @GetMapping("allPaging")
    public ResponseEntity<Page<DatCho>> allPaging(@RequestParam(defaultValue = "") String search,
                                                        @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(datChoService.filter(search,page,size,sort,column), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Get All DatCho")
    @GetMapping("/all")
    public List<DatCho> getall() {

        return datChoService.finfAll();
    }
    @ApiOperation(value ="Update DatCho")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/update/{id}")
    public ResponseEntity<DatCho> update(@PathVariable String id,
                                         @RequestBody DatChoDto dto){
        return new ResponseEntity<>(datChoService.update(id,dto), HttpStatus.OK);
    }
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String id){
        datChoService.changeStatus(id);
        //Optional<DichVu> dichVu=dichVuService.getDichVuByMaDichVu(maDichVu);
        Optional<DatCho> datCho=datChoService.findById(id);
        //DichVu dichVu=dichVuService.getDichVuByMaDichVu(maDichVu);
        // TaiKhoan taiKhoan = userService.getUser(id);
        return new ResponseEntity<>(String.format("DatCho có Id %s da duoc thay doi trang thai thanh %s"
                , datCho.get().getId(), datCho.get().isTrangThai()), HttpStatus.OK);
    }

}
