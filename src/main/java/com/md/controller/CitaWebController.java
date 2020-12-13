package com.md.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.TimeZone;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.md.entity.CitaWeb;
import com.md.entity.MedicoWeb;
import com.md.entity.PacienteWeb;
import com.md.service.CitaService;
import com.md.service.MedicoWebService;
import com.md.service.PacienteWebService;

@CrossOrigin()
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
		System.out.println("Cita: " + cita.toString());
		if (medicoExistente(Long.parseLong(cita.getMedicoId())))
			if (pacienteExistente(Long.parseLong(cita.getPacienteId())))
				if (citaPosible(cita))
					return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cita));
				else
					return ResponseEntity.status(HttpStatus.CONFLICT).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping()
	public List<CitaWeb> findAll() {
		
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Optional<CitaWeb> optional = service.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}

//	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CitaWeb cita) {
		Optional<CitaWeb> optional = service.findById(id);

		if (!optional.isPresent())
			return ResponseEntity.notFound().build();

		if (medicoExistente(Long.parseLong(cita.getMedicoId())))
			if ( pacienteExistente(Long.parseLong(cita.getPacienteId())) )
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

		

		Optional<MedicoWeb> medico = mService.findById(Long.parseLong(cita.getMedicoId()));

		Calendar inicioMedico = Calendar.getInstance();
		Calendar finMedico = Calendar.getInstance();
		TimeZone zone = TimeZone.getTimeZone("UTC");
		
		Calendar citaMedicaInicio = Calendar.getInstance(zone);
		citaMedicaInicio.setTime(cita.getInicio());
		citaMedicaInicio.setTimeZone(zone);

		Calendar citaMedicaFin = Calendar.getInstance(zone);
		citaMedicaFin.setTime(cita.getFin());
		citaMedicaFin.setTimeZone(zone);
		
		inicioMedico.setTime(citaMedicaInicio.getTime());
		inicioMedico.set(Calendar.SECOND, 0);

		finMedico.setTime(citaMedicaInicio.getTime());
		finMedico.set(Calendar.SECOND, 0);
		StringTokenizer st = new StringTokenizer(medico.get().getInicio(), ":");

		inicioMedico.set(Calendar.HOUR_OF_DAY, Integer.parseInt(st.nextToken()));
		if (st.hasMoreTokens()) {
			inicioMedico.set(Calendar.MINUTE, Integer.parseInt(st.nextToken().substring(0, 2)));
		} else {
			inicioMedico.set(Calendar.MINUTE, 00);
			inicioMedico.set(Calendar.SECOND, 0);
		}

		st = new StringTokenizer(medico.get().getFin(), ":");

		finMedico.set(Calendar.HOUR_OF_DAY, Integer.parseInt(st.nextToken()));
		if (st.hasMoreTokens()) {
			finMedico.set(Calendar.MINUTE, Integer.parseInt(st.nextToken().substring(0, 2)));
		} else {
			finMedico.set(Calendar.MINUTE, 00);
			finMedico.set(Calendar.SECOND, 0);
		}

		System.out.println("Cita:			 " + citaMedicaInicio.getTime().toString());
		System.out.println("Hora inicio de atención  " + inicioMedico.getTime().toString());
		System.out.println("Hora fin de atención 	 " + finMedico.getTime().toString());
//		System.out.println(citaMedica.getTime().compareTo(inicioMedico.getTime()) + " && "
//				+ citaMedica.getTime().compareTo(finMedico.getTime()));
		//Dividir cita medica en inicio y fin para garantizar la franja horaria de 1 hora.
		boolean aTiempo = citaMedicaInicio.getTime().compareTo(inicioMedico.getTime()) >= 0
				&& citaMedicaFin.getTime().compareTo(finMedico.getTime()) < 0;
		boolean antes = citaMedicaInicio.getTime().compareTo(inicioMedico.getTime()) < 0;
		boolean despues = citaMedicaInicio.getTime().compareTo(finMedico.getTime()) >= 0;

		if (aTiempo)
			System.out.println("A tiempo");
		else if (antes)
			System.out.println("Antes de horario");
		else if (despues)
			System.out.println("Después de horario");
		
		
		List<?> citasMedico = service.citasDelDiaMedico(Long.parseLong(cita.getMedicoId()), citaMedicaInicio.getTime());
		List<?> citasPaciente = service.citasDelDiaPaciente(Long.parseLong(cita.getPacienteId()), citaMedicaInicio.getTime());
		
		System.out.println(citasMedico.size()>0?"Franja no disponible":"Franja disponible");
		if (citasMedico.size() == 0 && citasPaciente.size() == 0 && aTiempo)
			return true;

		return false;
	}

	private boolean citaActualizable(CitaWeb cita) {
		
		Optional<MedicoWeb> medico = mService.findById(Long.parseLong(cita.getMedicoId()));

		Calendar inicioMedico = Calendar.getInstance();
		Calendar finMedico = Calendar.getInstance();
		TimeZone zone = TimeZone.getTimeZone("UTC");
		
		Calendar citaMedicaInicio = Calendar.getInstance(zone);
		citaMedicaInicio.setTime(cita.getInicio());
		citaMedicaInicio.setTimeZone(zone);

		Calendar citaMedicaFin = Calendar.getInstance(zone);
		citaMedicaFin.setTime(cita.getFin());
		citaMedicaFin.setTimeZone(zone);
		
		inicioMedico.setTime(citaMedicaInicio.getTime());
		inicioMedico.set(Calendar.SECOND, 0);

		finMedico.setTime(citaMedicaInicio.getTime());
		finMedico.set(Calendar.SECOND, 0);
		StringTokenizer st = new StringTokenizer(medico.get().getInicio(), ":");

		inicioMedico.set(Calendar.HOUR_OF_DAY, Integer.parseInt(st.nextToken()));
		if (st.hasMoreTokens()) {
			inicioMedico.set(Calendar.MINUTE, Integer.parseInt(st.nextToken().substring(0, 2)));
		} else {
			inicioMedico.set(Calendar.MINUTE, 00);
			inicioMedico.set(Calendar.SECOND, 0);
		}

		st = new StringTokenizer(medico.get().getFin(), ":");

		finMedico.set(Calendar.HOUR_OF_DAY, Integer.parseInt(st.nextToken()));
		if (st.hasMoreTokens()) {
			finMedico.set(Calendar.MINUTE, Integer.parseInt(st.nextToken().substring(0, 2)));
		} else {
			finMedico.set(Calendar.MINUTE, 00);
			finMedico.set(Calendar.SECOND, 0);
		}

		System.out.println("Cita:			 " + citaMedicaInicio.getTime().toString());
		System.out.println("Hora inicio de atención  " + inicioMedico.getTime().toString());
		System.out.println("Hora fin de atención 	 " + finMedico.getTime().toString());
//		System.out.println(citaMedica.getTime().compareTo(inicioMedico.getTime()) + " && "
//				+ citaMedica.getTime().compareTo(finMedico.getTime()));
		//Dividir cita medica en inicio y fin para garantizar la franja horaria de 1 hora.
		boolean aTiempo = citaMedicaInicio.getTime().compareTo(inicioMedico.getTime()) >= 0
				&& citaMedicaFin.getTime().compareTo(finMedico.getTime()) < 0;
		boolean antes = citaMedicaInicio.getTime().compareTo(inicioMedico.getTime()) < 0;
		boolean despues = citaMedicaInicio.getTime().compareTo(finMedico.getTime()) >= 0;

		if (aTiempo)
			System.out.println("A tiempo");
		else if (antes)
			System.out.println("Antes de horario");
		else if (despues)
			System.out.println("Después de horario");
		
		
		
		List<?> citasMedico = service.citasDelDiaMedico(Long.parseLong(cita.getMedicoId()), cita.getInicio());
		List<?> citasPaciente = service.citasDelDiaPaciente(Long.parseLong(cita.getPacienteId()), cita.getInicio());

		System.out.println(citasMedico.size());
		if (citasMedico.size() <= 1 && citasPaciente.size() == 0 && aTiempo)
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
