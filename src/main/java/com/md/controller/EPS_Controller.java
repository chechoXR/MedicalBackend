package com.md.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.md.entity.EPS;
import com.md.service.EPS_Service;

@CrossOrigin()
@Controller
@RequestMapping("/eps")
public class EPS_Controller {

	@Autowired
	EPS_Service service;
	
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody EPS eps){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(eps));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EPS eps){
		Optional<EPS> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		optional.get().setEps(eps.getEps());
		return ResponseEntity.status(HttpStatus.OK).body(service.save(eps));
		
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		List<EPS> eps = StreamSupport.stream(service.findAll().spliterator(), false).collect(Collectors.toList());
		
		return ResponseEntity.accepted().body(eps);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Optional<EPS> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.status(HttpStatus.FOUND).body(optional.get());
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<EPS> optional = service.findById(id);

		if (!optional.isPresent())
			return ResponseEntity.notFound().build();

		service.deleteById(id);

		return ResponseEntity.ok().build();
	}
	
}
