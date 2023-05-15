package com.stc.ruaxe.repositories;

import com.stc.ruaxe.entities.LoaiThuCung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoaiThuCungRepository extends MongoRepository<LoaiThuCung,String> {
    @Override
    boolean existsById(String s);

    boolean existsByMaLoaiThuCung(String maLoai);

    Page<LoaiThuCung> findByMaLoaiThuCungContainingOrTenLoaiThuCungContainingAllIgnoreCase(String search, String search1, Pageable pageable);

    LoaiThuCung findLoaiThuCungByMaLoaiThuCung(String maLoaiThuCung);
}
