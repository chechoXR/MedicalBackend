package com.md.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import com.md.entity.TipoIdentificacion;
import com.md.service.TipoIdentificacionService;

@CrossOrigin()
@RestController
@RequestMapping("/tipoIdentificacion")
public class TipoIdentificacionController {

	@Autowired
	TipoIdentificacionService service;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody TipoIdentificacion TipoIdentificacion){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(TipoIdentificacion));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TipoIdentificacion TipoIdentificacion){
		Optional<TipoIdentificacion> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		optional.get().setTipoIdentificacion(TipoIdentificacion.getTipoIdentificacion());
		return ResponseEntity.status(HttpStatus.OK).body(service.save(TipoIdentificacion));
		
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		List<TipoIdentificacion> TipoIdentificacion = StreamSupport.stream(service.findAll().spliterator(), false).collect(Collectors.toList());
		
		return ResponseEntity.accepted().body(TipoIdentificacion);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Optional<TipoIdentificacion> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<TipoIdentificacion> optional = service.findById(id);

		if (!optional.isPresent())
			return ResponseEntity.notFound().build();

		service.deleteById(id);

		return ResponseEntity.ok().build();
	}

	
	
}
