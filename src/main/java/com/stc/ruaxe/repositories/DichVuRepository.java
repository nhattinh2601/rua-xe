package com.stc.ruaxe.repositories;

import com.stc.ruaxe.entities.DichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DichVuRepository extends MongoRepository<DichVu,String> {
    boolean existsByMaDichVu(String maDichVu);
    
    Page<DichVu> findByMaDichVuContainingOrTenDichVuContainingAllIgnoreCase(String maDichVu, String tenDichVu, Pageable pageable);

    Optional<DichVu> findDichVuByMaDichVu(String maDichVu);

   // Page<DichVu> findByNameContainingOrEmailContainingAllIgnoreCase(String search, String search1, Pageable pageable);
}
