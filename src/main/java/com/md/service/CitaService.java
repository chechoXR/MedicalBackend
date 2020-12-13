package com.md.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.md.entity.CitaWeb;
import com.md.repository.CitaWebRepository;

@Service
public class CitaService {

	@Autowired
	CitaWebRepository repository;
	
	@Transactional(readOnly = true)
	public List<CitaWeb> findAll() {
		return repository.getAll();
	}
	
	@Transactional(readOnly = true)
	public List<CitaWeb> citasDelDiaMedico(Long medicoId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date inicio){
		return repository.citasDelDiaMedico(medicoId, inicio);
	}
	
	@Transactional(readOnly = true)
	public List<CitaWeb> citasDelDiaPaciente(Long medicoId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date inicio){
		return repository.citasDelDiaPaciente(medicoId, inicio);
	}
	
	@Transactional(readOnly = true)
	public Optional<CitaWeb> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public CitaWeb save(CitaWeb cita) {
		return repository.save(cita);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
}
