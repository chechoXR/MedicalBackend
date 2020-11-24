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

import com.md.entity.CitaWeb;
import com.md.entity.MedicoWeb;
import com.md.entity.PacienteWeb;
import com.md.service.CitaService;
import com.md.service.MedicoWebService;
import com.md.service.PacienteWebService;

@RestController
@RequestMapping("/web/citas")
public class CitaWebController {

	@Autowired
	CitaService service;

	@Autowired
	MedicoWebService mService;

	@Autowired
	PacienteWebService pService;

	@PostMapping
	public ResponseEntity<CitaWeb> create(@RequestBody CitaWeb cita) {
		if (medicoExistente(cita.getMedicoId()))
			if (pacienteExistente(cita.getPacienteId()))
				if (citaPosible(cita))
					return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cita));
				else
					return ResponseEntity.status(HttpStatus.CONFLICT).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping()
	public List<CitaWeb> findAll() {
		List<CitaWeb> citas = StreamSupport.stream(service.findAll().spliterator(), false).collect(Collectors.toList());
		return citas;
	}

	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CitaWeb cita) {
		Optional<CitaWeb> optional = service.findById(id);

		if (!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		if (medicoExistente(cita.getMedicoId()))
			if (pacienteExistente(cita.getPacienteId()))
				if (citaActualizable(cita)) {

					optional.get().setPacienteId(cita.getPacienteId());
					optional.get().setMedicoId(cita.getMedicoId());
					optional.get().setInicio(cita.getInicio());
					optional.get().setFin(cita.getFin());

					return ResponseEntity.status(HttpStatus.OK).body(service.save(optional.get()));
				} else
					return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Optional<CitaWeb> optional = service.findById(id);

		if (!optional.isPresent())
			return ResponseEntity.notFound().build();

		service.deleteById(id);

		return ResponseEntity.ok().build();
	}

	private boolean citaPosible(CitaWeb cita) {

		List<?> citasMedico = service.citasDelDiaMedico(cita.getMedicoId(), cita.getInicio());
		List<?> citasPaciente = service.citasDelDiaPaciente(cita.getPacienteId(), cita.getInicio());
		
		System.out.println(citasMedico.size());
		if (citasMedico.size() == 0 && citasPaciente.size()==0)
			return true;

		return false;
	}
	
	private boolean citaActualizable(CitaWeb cita) {

		List<?> citasMedico = service.citasDelDiaMedico(cita.getMedicoId(), cita.getInicio());
		List<?> citasPaciente = service.citasDelDiaPaciente(cita.getPacienteId(), cita.getInicio());
		
		System.out.println(citasMedico.size());
		if (citasMedico.size() <=1 && citasPaciente.size()==0)
			return true;

		return false;
	}

	private boolean medicoExistente(Long id) {
		Optional<MedicoWeb> mOptional = mService.findById(id);
		return mOptional.isPresent();
	}

	private boolean pacienteExistente(Long id) {
		Optional<PacienteWeb> pOptional = pService.findById(id);
		return pOptional.isPresent();
	}

}
