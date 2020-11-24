package com.md.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.md.entity.CitaWeb;

@Repository
public interface CitaWebRepository extends JpaRepository<CitaWeb, Long>{
	
	@Query(value = "SELECT * FROM cita_web WHERE medico_id = ?1 AND inicio = ?2", nativeQuery = true)
	public List<CitaWeb> citasDelDiaMedico(Long medicoid, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date inicio);

	@Query(value = "SELECT * FROM cita_web WHERE paciente_id = ?1 AND inicio = ?2", nativeQuery = true)
	public List<CitaWeb> citasDelDiaPaciente(Long medicoid, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date inicio);
	
}