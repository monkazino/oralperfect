package com.monkazino.consultorio.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_GEO_LOCALIDAD")
public class LocalidadEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "LOCALIDAD")
	private Long localidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CIUDAD")
	private CiudadEntity ciudadEntity;
	
	@NotNull
	@NotEmpty
	@Column (name = "CODIGO")
	private String codigo;
	
	@NotNull
	@NotEmpty
	@Column (name = "DESCRIPCION")
	private String descripcion;
	
	@NotNull
	@NotEmpty
	@Column (name = "ESTADO")
	private String estado;
	
	@OneToMany(mappedBy = "localidadEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BarrioEntity> barrios;
	
	public LocalidadEntity() {
		barrios = new ArrayList<BarrioEntity>();
	}

	public Long getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Long localidad) {
		this.localidad = localidad;
	}

	public CiudadEntity getCiudadEntity() {
		return ciudadEntity;
	}

	public void setCiudadEntity(CiudadEntity ciudadEntity) {
		this.ciudadEntity = ciudadEntity;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<BarrioEntity> getBarrios() {
		return barrios;
	}

	public void setBarrio(List<BarrioEntity> barrios) {
		this.barrios = barrios;
	}
	
	public void addBarrio(BarrioEntity barrioEntity) {
		barrios.add(barrioEntity);
	}
}
