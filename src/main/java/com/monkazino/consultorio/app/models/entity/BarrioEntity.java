package com.monkazino.consultorio.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_GEO_BARRIO")
public class BarrioEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "BARRIO")
	private Long barrio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LOCALIDAD")
	private LocalidadEntity localidadEntity;
	
	@NotNull
	@NotEmpty
	@Column (name = "CODIGO")
	private String codigo;
	
	@NotNull
	@NotEmpty
	@Column (name = "DESCRIPCION")
	private String descripcion;
	
	public BarrioEntity() {
		
	}

	public Long getBarrio() {
		return barrio;
	}

	public void setBarrio(Long barrio) {
		this.barrio = barrio;
	}

	public LocalidadEntity getLocalidadEntity() {
		return localidadEntity;
	}

	public void setLocalidadEntity(LocalidadEntity localidadEntity) {
		this.localidadEntity = localidadEntity;
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
}
