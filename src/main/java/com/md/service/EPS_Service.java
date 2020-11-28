package com.md.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.md.entity.EPS;
import com.md.repository.EPS_Repository;

@Service
public class EPS_Service {

	@Autowired
	EPS_Repository repository;
	
	
	@Transactional(readOnly = true)
	public Iterable<EPS> findAll() {
		return repository.findAll();
	}	
	
	@Transactional(readOnly = true)
	public Optional<EPS> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public EPS save(EPS eps) {
		return repository.save(eps);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	
}
