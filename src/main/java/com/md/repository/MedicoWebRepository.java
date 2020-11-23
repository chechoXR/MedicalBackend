package com.md.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.md.entity.MedicoWeb;
@Repository
public interface MedicoWebRepository extends JpaRepository<MedicoWeb, Long>{

}
