package com.stc.ruaxe.service.loaithucung;


import com.stc.ruaxe.dtos.loaithucung.LoaiThuCungDto;
import com.stc.ruaxe.entities.LoaiThuCung;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoaiThuCungService {
    Page<LoaiThuCung> filter(String search, int page, int size, String sort, String column);
    LoaiThuCung getLoaiThuCungByMaLoaiThuCung(String maLoaiThuCung);
    List<LoaiThuCung> getAllLoaiThuCung(String search);
    List<LoaiThuCung> finfAll();

    LoaiThuCung create(LoaiThuCungDto dto);

    LoaiThuCung update(String id, LoaiThuCungDto dto);


    LoaiThuCung changeStatus(String maLoaiThuCung);
}
