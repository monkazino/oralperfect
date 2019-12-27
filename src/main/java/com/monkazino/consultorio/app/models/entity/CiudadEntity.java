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
@Table(name = "TB_GEO_CIUDAD")
public class CiudadEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "CIUDAD")
	private Long ciudad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DEPARTAMENTO")
	private DepartamentoEntity departamentoEntity;
	
	@NotNull
	@NotEmpty
	@Column (name = "CODIGO")
	private String codigo;
	
	@NotNull
	@NotEmpty
	@Column (name = "DESCRIPCION")
	private String descripcion;
	
	@OneToMany(mappedBy = "ciudadEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalidadEntity> localidades;
	
	public CiudadEntity() {
		localidades = new ArrayList<LocalidadEntity>();
	}

	public Long getCiudad() {
		return ciudad;
	}

	public void setCiudad(Long ciudad) {
		this.ciudad = ciudad;
	}

	public DepartamentoEntity getDepartamentoEntity() {
		return departamentoEntity;
	}

	public void setDepartamentoEntity(DepartamentoEntity departamentoEntity) {
		this.departamentoEntity = departamentoEntity;
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

	public List<LocalidadEntity> getLocalidades() {
		return localidades;
	}

	public void setLocalidad(List<LocalidadEntity> localidades) {
		this.localidades = localidades;
	}
	
	public void addLocalidad(LocalidadEntity localidadEntity) {
		localidades.add(localidadEntity);
	}
}
