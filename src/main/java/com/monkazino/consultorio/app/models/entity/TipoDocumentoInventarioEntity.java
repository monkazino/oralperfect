package com.monkazino.consultorio.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_PARAM_TIPO_DOCUMENTO_INVENTARIO")
public class TipoDocumentoInventarioEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "TIPO_DOCUMENTO_INVENTARIO")
	private Long tipoDocumentoInventario;

	@NotNull
	@NotEmpty
	@Column (name = "CODIGO")
	private String codigo;
	
	@NotNull
	@NotEmpty
	@Column (name = "DESCRIPCION")
	private String descripcion;
	
	@NotEmpty
	@Column (name = "OBSERVACION")
	private String observacion;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	@NotNull
	@NotEmpty
	@Column (name = "ESTADO")
	private String estado;
	
	@NotNull
	@NotEmpty
	@Column (name = "TIPO_MOVIMIENTO_INVENTARIO")
	private String tipoMovimientoInventario;
	
	public TipoDocumentoInventarioEntity() {
		
	}

	public Long getTipoDocumentoInventario() {
		return tipoDocumentoInventario;
	}

	public void setTipoDocumentoInventario(Long tipoDocumentoInventario) {
		this.tipoDocumentoInventario = tipoDocumentoInventario;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoMovimientoInventario() {
		return tipoMovimientoInventario;
	}

	public void setTipoMovimientoInventario(String tipoMovimientoInventario) {
		this.tipoMovimientoInventario = tipoMovimientoInventario;
	}
	
}
