package com.md.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.md.entity.MedicoWeb;
import com.md.service.MedicoWebService;

@RestController
@RequestMapping("/web/medicos")
public class MedicoWebController {

	@Autowired
	MedicoWebService service;
	
	@PostMapping
	public ResponseEntity<MedicoWeb> create(@RequestBody MedicoWeb medico){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(medico));
	}
	
	@GetMapping()
	public List<MedicoWeb> findAll(){
		List<MedicoWeb> medicos = StreamSupport.stream(service.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return medicos;
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MedicoWeb medico){
		Optional<MedicoWeb> optionalUser = service.findById(id);
		
		if(!optionalUser.isPresent())
			return ResponseEntity.notFound().build();
		
		optionalUser.get().setNombre(medico.getNombre());
		optionalUser.get().setIdentificacion(medico.getIdentificacion());
		optionalUser.get().setTipoIdentificacion(medico.getTipoIdentificacion());
		optionalUser.get().setNumeroTarjetaProfesional(medico.getNumeroTarjetaProfesional());
		optionalUser.get().setAniosExperiencia(medico.getAniosExperiencia());
		optionalUser.get().setEspecialidad(medico.getEspecialidad());
		optionalUser.get().setInicio(medico.getInicio());
		optionalUser.get().setFin(medico.getFin());
		
		return ResponseEntity.status(HttpStatus.OK).body(service.save(optionalUser.get()));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id){
		Optional<MedicoWeb> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		service.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
}
