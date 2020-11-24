package com.md.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medico_web")
public class MedicoWeb{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(unique = true, nullable = false)
	private String identificacion;
	
	@Column(name = "tarjetaProfesional",unique = true, nullable = false)
	private String numeroTarjetaProfesional;
	
	@Column(nullable = false)
	private String tipoIdentificacion;
	
	@Column(nullable = false)
	private float aniosExperiencia;
	
	@Column(nullable = false)
	private String especialidad;
	
	@Column(nullable = false)
	private String inicio;
	
	@Column(nullable = false)
	private String fin;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getNumeroTarjetaProfesional() {
		return numeroTarjetaProfesional;
	}
	public void setNumeroTarjetaProfesional(String numeroTarjetaProfesional) {
		this.numeroTarjetaProfesional = numeroTarjetaProfesional;
	}
	public float getAniosExperiencia() {
		return aniosExperiencia;
	}
	public void setAniosExperiencia(float aniosExperiencia) {
		this.aniosExperiencia = aniosExperiencia;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getFin() {
		return fin;
	}
	public void setFin(String fin) {
		this.fin = fin;
	}
	
	
	
}
