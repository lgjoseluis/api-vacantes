package com.local.vacantes.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepository<T, ID>{ //extends JpaRepository<T, ID> {    
    void deleteById(ID id);
    
    Page<T> findAll(Pageable pageable);
}
