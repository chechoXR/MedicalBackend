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
	
	@Query(value = "SELECT * FROM cita_web WHERE CAST(medico_id AS bigint) = ?1 AND inicio = ?2", nativeQuery = true)
	public List<CitaWeb> citasDelDiaMedico(Long medicoid, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date inicio);

	@Query(value = "SELECT * FROM cita_web WHERE CAST(paciente_id AS bigint) = ?1 AND inicio = ?2", nativeQuery = true)
	public List<CitaWeb> citasDelDiaPaciente(Long medicoid, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date inicio);
	
	
	//@Query(value = "select cita_web.id, cita_web.inicio, cita_web.fin, paciente_web.nombre, medico_web.nombre from cita_web left join paciente_web on cita_web.paciente_id = paciente_web.id left join medico_web on cita_web.medico_id=medico_web.id;", nativeQuery = true)
	@Query(value = "select cita_web.id, cita_web.inicio, cita_web.fin, paciente_web.nombre as paciente_id, medico_web.nombre as medico_id from cita_web left join paciente_web on CAST(cita_web.paciente_id AS bigint) = paciente_web.id left join medico_web on CAST(cita_web.medico_id AS bigint) = medico_web.id;", nativeQuery = true)
	public List<CitaWeb> getAll();
}