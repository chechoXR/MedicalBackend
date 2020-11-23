package com.md.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.md.entity.PacienteWeb;
@Repository
public interface PacienteWebRepository extends JpaRepository<PacienteWeb, Long> {

}
