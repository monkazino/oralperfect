package com.monkazino.consultorio.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_MOV_DOCUMENTO_INVENTARIO")
public class DocumentoInventarioEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "DOCUMENTO_INVENTARIO")
	private Long documentoInventario;
	
	@NotNull
	@NotEmpty
	@Column (name = "OBSERVACION")
	private String observacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ProveedorEntity proveedorEntity;

	public DocumentoInventarioEntity() {
		
	}
	
	public Long getDocumentoInventario() {
		return documentoInventario;
	}

	public void setDocumentoInventario(Long documentoInventario) {
		this.documentoInventario = documentoInventario;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public ProveedorEntity getProveedorEntity() {
		return proveedorEntity;
	}

	public void setProveedorEntity(ProveedorEntity proveedorEntity) {
		this.proveedorEntity = proveedorEntity;
	}

	
}
