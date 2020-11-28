package com.md.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.md.entity.TipoIdentificacion;
import com.md.repository.TipoIdentificacionRepository;

@Service
public class TipoIdentificacionService {
	
	@Autowired
	TipoIdentificacionRepository repository;
	
	@Transactional(readOnly = true)
	public Iterable<TipoIdentificacion> findAll() {
		return repository.findAll();
	}	
	
	@Transactional(readOnly = true)
	public Optional<TipoIdentificacion> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public TipoIdentificacion save(TipoIdentificacion TipoIdentificacion) {
		return repository.save(TipoIdentificacion);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	
}
