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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_PROD_PRODUCTO")
public class ProductoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "PRODUCTO")
	private Long producto;

	@NotNull
	@NotEmpty
	@Column (name = "CODIGO")
	private String codigo;
	
	@NotNull
	@NotEmpty
	@Column (name = "DESCRIPCION")
	private String descripcion;
	
	@NotNull
	@Column (name = "PRECIO")
	private Double precio;
	
	@NotNull
	@Column (name = "COSTO")
	private Double costo;
	
	@NotNull
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_TIPO_PRODUCTO")
	private ParametroProductoEntity paramTipoProducto;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_MARCA")
	private ParametroProductoEntity paramMarca;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_CATEGORIA")
	private ParametroProductoEntity paramCategoria;
	
	@NotNull
	@NotEmpty
	@Column (name = "ESTADO")
	private String estado;
	
	public ProductoEntity() {
		this.paramTipoProducto = new ParametroProductoEntity();
		this.paramMarca = new ParametroProductoEntity();
		this.paramCategoria = new ParametroProductoEntity();
	}
	
	public Long getProducto() {
		return producto;
	}

	public void setProducto(Long producto) {
		this.producto = producto;
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
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	public ParametroProductoEntity getParamTipoProducto() {
		return this.paramTipoProducto;
	}
	
	public void setParamTipoProducto(ParametroProductoEntity paramTipoProducto) {
		this.paramTipoProducto = paramTipoProducto;
	}

	public ParametroProductoEntity getParamMarca() {
		return this.paramMarca;
	}
	
	public void setParamMarca(ParametroProductoEntity paramMarca) {
		this.paramMarca = paramMarca;
	}

	public ParametroProductoEntity getParamCategoria() {
		return this.paramCategoria;
	}
	
	public void setParamCategoria(ParametroProductoEntity paramCategoria) {
		this.paramCategoria = paramCategoria;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
