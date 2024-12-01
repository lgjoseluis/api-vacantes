package com.local.vacantes.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericRepository<T, ID>{
    void deleteById(ID id);
    
    Page<T> findAll(Pageable pageable);
}
