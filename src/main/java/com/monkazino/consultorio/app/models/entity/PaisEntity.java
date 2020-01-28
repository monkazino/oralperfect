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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_GEO_PAIS")
public class PaisEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "PAIS")
	private Long pais;

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
	
	@OneToMany(mappedBy = "paisEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DepartamentoEntity> departamentos;
		
	public PaisEntity() {
		departamentos = new ArrayList<DepartamentoEntity>();
	}

	public Long getPais() {
		return pais;
	}

	public void setPais(Long pais) {
		this.pais = pais;
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

	public List<DepartamentoEntity> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamento(List<DepartamentoEntity> departamentos) {
		this.departamentos = departamentos;
	}
	
	public void addDepartamento(DepartamentoEntity departamentoEntity) {
		departamentos.add(departamentoEntity);
	}
	
}
