package com.md.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.md.entity.MedicoWeb;
@Repository
public interface MedicoWebRepository extends JpaRepository<MedicoWeb, Long>{
	//CAST(medico_web.tipo_identificacion AS bigint)
	@Query(value = "select medico_web.id, medico_web.nombre, medico_web.identificacion, tipo_identificacion.tipo_identificacion, medico_web.tarjeta_profesional, medico_web.anios_experiencia, medico_web.especialidad, medico_web.inicio, medico_web.fin from medico_web left join tipo_identificacion on CAST(medico_web.tipo_identificacion AS bigint) = tipo_identificacion.id;",nativeQuery = true)
	public List<MedicoWeb> getAll();
}
