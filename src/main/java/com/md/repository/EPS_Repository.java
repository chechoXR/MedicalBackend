package com.md.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.md.entity.EPS;
@Repository
public interface EPS_Repository extends JpaRepository<EPS, Long>{

}
