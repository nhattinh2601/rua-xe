package com.stc.ruaxe.service.dichvu;

import com.stc.ruaxe.dtos.dichvu.DichVuDto;
import com.stc.ruaxe.entities.DichVu;
import com.stc.ruaxe.entities.embedded.GiaDichVu;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface DichVuService {
    Page<DichVu> filter(String search, int page, int size, String sort, String column);
    DichVu getDichVuByMaDichVu(String maDichVu);
    List<DichVu> getAllDichVu(String search);
    List<DichVu> finfAll();
    DichVu addGiaDV(String maDichVu, GiaDichVu giaDichVu);
    DichVu create(DichVuDto dto, Principal principal);

    DichVu update(String id, DichVuDto dto, Principal principal);

    DichVu changeStatus(String maDichVu);
}
