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
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_PARAM_PARAMETRO_PRODUCTO")
public class ParametroProductoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "PARAMETRO_PRODUCTO")
	private Long parametroProducto;

	@NotNull
	@NotEmpty
	@Column (name = "DESCRIPCION")
	private String descripcion;
	
	@NotNull
	@NotEmpty
	@Column (name = "CODIGO")
	private String codigo;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TIPO_PARAMETRO_PRODUCTO")
	private TipoParametroProductoEntity tipoParametroProductoEntity;
	
	public ParametroProductoEntity() {
		
	}

	public Long getParametroProducto() {
		return parametroProducto;
	}

	public void setParametroProducto(Long parametroProducto) {
		this.parametroProducto = parametroProducto;
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
	
	public TipoParametroProductoEntity getTipoParametroProductoEntity() {
		return tipoParametroProductoEntity;
	}

	public void setTipoParametroProductoEntity(TipoParametroProductoEntity tipoParametroProductoEntity) {
		this.tipoParametroProductoEntity = tipoParametroProductoEntity;
	}
	
}
