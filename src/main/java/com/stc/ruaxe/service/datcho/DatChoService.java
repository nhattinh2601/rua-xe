package com.stc.ruaxe.service.datcho;


import com.stc.ruaxe.dtos.datcho.DatChoDto;
import com.stc.ruaxe.entities.DatCho;

import com.stc.ruaxe.entities.embedded.ThongTinDatCho;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface DatChoService {
    Page<DatCho> filter(String search, int page, int size, String sort, String column);
    DatCho getDatChoByMaDatCho(String maDatCho);
    Optional<DatCho> findById(String Id);
    List<DatCho> getAllDatCho(String search);
    List<DatCho> finfAll();
    DatCho addThongTinDatCho(String id, ThongTinDatCho thongTinDatCho);
    DatCho create(DatChoDto dto);

    DatCho update(String id, DatChoDto dto);

    DatCho changeStatus(String id);
}
