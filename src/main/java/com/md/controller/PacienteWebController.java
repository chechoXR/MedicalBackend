package com.md.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.md.entity.PacienteWeb;
import com.md.service.PacienteWebService;

@CrossOrigin()
@RestController
@RequestMapping("/web/pacientes")
public class PacienteWebController {

	@Autowired
	PacienteWebService service;
	
	@PostMapping
	public ResponseEntity<PacienteWeb> create(@RequestBody PacienteWeb paciente){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(paciente));
	}
	
	@GetMapping()
	public List<PacienteWeb> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Optional<PacienteWeb> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.status(HttpStatus.FOUND).body(optional.get());
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PacienteWeb paciente){
		Optional<PacienteWeb> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		optional.get().setNombre(paciente.getNombre());
		optional.get().setFechaNacimiento(paciente.getFechaNacimiento());
		optional.get().setIdentificacion(paciente.getIdentificacion());
		optional.get().setTipoIdentificacion(paciente.getTipoIdentificacion());
		optional.get().setEPS(paciente.getEPS());
		optional.get().setHistoriaClinica(paciente.getHistoriaClinica());
		
		return ResponseEntity.status(HttpStatus.OK).body(service.save(optional.get()));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id){
		Optional<PacienteWeb> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		service.deleteById(id);
		
		return ResponseEntity.ok().build();
	}

	
}
