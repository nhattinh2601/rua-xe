package com.stc.ruaxe.controller;

import com.stc.ruaxe.dtos.services.ServiceDto;
import com.stc.ruaxe.entities.Services;
import com.stc.ruaxe.service.services.ServicesService;
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
@RequestMapping("rest/services")
public class ServicesController {
    public final ServicesService datChoService;
    private final int size = 5;
    private final String sort = "asc";

    private final String column = "email";


    @ApiOperation(value ="Create DatCho")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/create")
    public ResponseEntity<Services> create(@Valid @RequestBody ServiceDto dto){
        return new ResponseEntity<>(datChoService.create(dto), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All DatCho Paging")
    @GetMapping("allPaging")
    public ResponseEntity<Page<Services>> allPaging(@RequestParam(defaultValue = "") String search,
                                                    @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(datChoService.filter(search,page,size,sort,column), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Get All DatCho")
    @GetMapping("/all")
    public List<Services> getall() {

        return datChoService.findAll();
    }
    @ApiOperation(value ="Update DatCho")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/update/{id}")
    public ResponseEntity<Services> update(@PathVariable String id,
                                           @RequestBody ServiceDto dto){
        return new ResponseEntity<>(datChoService.update(id,dto), HttpStatus.OK);
    }


    // 
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/find/{id}")
    public Services findById(@PathVariable String id) {
        return datChoService.findById(id);
    }


    // 
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        datChoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
