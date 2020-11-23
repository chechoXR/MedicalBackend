package com.md.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.md.entity.PacienteWeb;
import com.md.repository.PacienteWebRepository;

@Service
public class PacienteWebService {

	@Autowired
	PacienteWebRepository repository;
	
	
	@Transactional(readOnly = true)
	public Iterable<PacienteWeb> findAll() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<PacienteWeb> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public PacienteWeb save(PacienteWeb paciente) {
		return repository.save(paciente);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	

}
