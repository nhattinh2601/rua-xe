package com.stc.ruaxe.repositories;

import com.stc.ruaxe.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

    
    // xử lý phân trang 
    Page<User> findByNameContainingOrEmailContainingAllIgnoreCase(String name, String email, Pageable pageable);
    

    // kiem tra tai khoan trung khi tao moi tai khoan
    boolean existsByEmail(String email);
    

    // Xu ly jwt
    @Query(value = "{'email': ?0}")
    Optional<User> getUser(String email);
}
