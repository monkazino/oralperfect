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

@Entity
@Table(name = "TB_PROD_PRODUCTO_BODEGA")
public class ProductoBodegaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "PRODUCTO_BODEGA")
	private Long productoBodega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTO")
	private ProductoEntity productoEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BODEGA")
	private BodegaEntity bodegaEntity;

	@NotNull
	@Column (name = "CANTIDAD")
	private Integer cantidad;
	
	public ProductoBodegaEntity() {
		
	}
	
	public Long getProductoBodega() {
		return productoBodega;
	}

	public void setProductoBodega(Long productoBodega) {
		this.productoBodega = productoBodega;
	}
	
	public ProductoEntity getProductoEntity() {
		return productoEntity;
	}

	public void setProductoEntity(ProductoEntity productoEntity) {
		this.productoEntity = productoEntity;
	}

	public BodegaEntity getBodegaEntity() {
		return bodegaEntity;
	}

	public void setBodegaEntity(BodegaEntity bodegaEntity) {
		this.bodegaEntity = bodegaEntity;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
}
