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
@Table(name = "TB_GEO_DEPARTAMENTO")
public class DepartamentoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "DEPARTAMENTO")
	private Long departamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PAIS")
	private PaisEntity paisEntity;
	
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
	
	@OneToMany(mappedBy = "departamentoEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CiudadEntity> ciudades;
	
	public DepartamentoEntity() {
		ciudades = new ArrayList<CiudadEntity>();
	}

	public Long getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}
	
	public PaisEntity getPaisEntity() {
		return paisEntity;
	}

	public void setPaisEntity(PaisEntity paisEntity) {
		this.paisEntity = paisEntity;
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
	
	public List<CiudadEntity> getCiudades() {
		return ciudades;
	}

	public void setCiudad(List<CiudadEntity> ciudades) {
		this.ciudades = ciudades;
	}
	
	public void addCiudad(CiudadEntity ciudadEntity) {
		ciudades.add(ciudadEntity);
	}
}
