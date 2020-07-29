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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_PERS_PROVEEDOR")
public class ProveedorEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "PROVEEDOR")
	private Long proveedor;
	
	@NotNull
	@NotEmpty
	@Column (name = "NUMERO_IDENTIFICACION")
	private String numeroIdentificacion;

	@NotNull
	@NotEmpty
	@Column (name = "RAZON_SOCIAL")
	private String razonSocial;
	
	@OneToMany(mappedBy = "proveedorEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DocumentoInventarioEntity> documentosInventario;

	
	public ProveedorEntity() {
		documentosInventario = new ArrayList<DocumentoInventarioEntity>();		
	}
	
	public Long getProveedor() {
		return proveedor;
	}

	public void setProveedor(Long proveedor) {
		this.proveedor = proveedor;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public List<DocumentoInventarioEntity> getDocumentosInventario() {
		return documentosInventario;
	}

	public void setDocumentosInventario(List<DocumentoInventarioEntity> documentosInventario) {
		this.documentosInventario = documentosInventario;
	}

	public void addDocumentoInventario(DocumentoInventarioEntity documentoInventario) {
		documentosInventario.add(documentoInventario);
	}
	
}
