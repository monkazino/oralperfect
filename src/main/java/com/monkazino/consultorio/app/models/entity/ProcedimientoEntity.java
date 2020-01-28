package com.monkazino.consultorio.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_PROC_PROCEDIMIENTO")
public class ProcedimientoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "PROCEDIMIENTO")
	private Long procedimiento;

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
	
	public ProcedimientoEntity() {
	}

	public Long getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(Long procedimiento) {
		this.procedimiento = procedimiento;
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

}
