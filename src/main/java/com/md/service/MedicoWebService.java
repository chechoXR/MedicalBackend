package com.md.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.md.entity.MedicoWeb;
import com.md.repository.MedicoWebRepository;

@Service
public class MedicoWebService {

	@Autowired
	MedicoWebRepository repository;
	
	
	@Transactional(readOnly = true)
	public Iterable<MedicoWeb> findAll() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<MedicoWeb> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public MedicoWeb save(MedicoWeb medico) {
		return repository.save(medico);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	
}
