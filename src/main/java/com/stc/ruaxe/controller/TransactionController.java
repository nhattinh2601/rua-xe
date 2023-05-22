package com.stc.ruaxe.controller;

import com.stc.ruaxe.dtos.transactions.TransactionDto;
import com.stc.ruaxe.dtos.user.SigupDto;
import com.stc.ruaxe.entities.Transactions;
import com.stc.ruaxe.service.transactions.TransactionService;
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
@RequestMapping("rest/transactions")
public class TransactionController {
    private final int size = 5;
    private final String sort = "asc";

    private final String column = "maLoaiThuCung";

        @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All Loai Thu Cung Paging")
    @GetMapping("allPaging")
    public ResponseEntity<Page<Transactions>> allPaging(@RequestParam(defaultValue = "") String search,
                                                        @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(transactionService.filter(search,page,size,sort,column), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Get All Loai Thu cung")
    @GetMapping("/all")
    public List<Transactions> all() {

        return transactionService.findAll();
    }


    public final TransactionService transactionService;

    @ApiOperation(value = "Create Loai Thu cung")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create/{vehicleId}/{userId}/{serviceId}")
    public ResponseEntity<TransactionDto> addReservationToUser(@Valid @RequestBody TransactionDto dto,
                                                               Principal principal, @PathVariable String vehicleId, @PathVariable String userId, @PathVariable String serviceId) {
        TransactionDto transactionDto = transactionService.create(dto, principal, vehicleId, userId, serviceId);
        return ResponseEntity.ok(transactionDto);
    }

        @ApiOperation(value ="Update LoaiThuCung")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<Transactions> update(@PathVariable String id,
                                               @RequestBody TransactionDto dto){
        return new ResponseEntity<>(transactionService.update(id,dto), HttpStatus.OK);
    }
    @PostMapping("/change-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@PathVariable String id){
        transactionService.changeStatus(id);

        Transactions loaiThuCung=transactionService.findById(id);

        return new ResponseEntity<>(String.format("Mã loại thú cưng %s da duoc thay doi trang thai thanh %s"
                , loaiThuCung.isTrangThai()), HttpStatus.OK);
    }



    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/find/{id}")
    public Transactions findById(@PathVariable String id) {
        return transactionService.findById(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

