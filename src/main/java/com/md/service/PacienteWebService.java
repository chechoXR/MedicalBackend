package com.md.service;

import java.util.List;
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
	public List<PacienteWeb> findAll() {
		return repository.getAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<PacienteWeb> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public PacienteWeb save(PacienteWeb paciente) {
		System.out.println(paciente.getFechaNacimiento().toString());
		return repository.save(paciente);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	

}
