package com.md.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.md.entity.PacienteWeb;

@Repository
public interface PacienteWebRepository extends JpaRepository<PacienteWeb, Long> {

//	@Query(value = "select paciente_web.id, paciente_web.nombre, paciente_web.fecha_nacimiento, paciente_web.identificacion, paciente_web.tipo_identificacion, paciente_web.historia_clinica, eps.eps  from paciente_web left join eps ON paciente_web.eps = eps.id;",nativeQuery = true)
	@Query(value = "select paciente_web.id, paciente_web.nombre, paciente_web.fecha_nacimiento, paciente_web.identificacion, tipo_identificacion.tipo_identificacion, paciente_web.historia_clinica, eps.eps  from paciente_web left join eps ON CAST(paciente_web.eps AS bigint) = eps.id left join tipo_identificacion on CAST(paciente_web.tipo_identificacion AS bigint) = tipo_identificacion.id;",nativeQuery = true)
	public List<PacienteWeb> getAll();
}
