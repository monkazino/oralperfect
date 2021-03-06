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
@Table(name = "TB_MOV_ITEM_INVENTARIO")
public class ItemInventarioEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "ITEM_INVENTARIO")
	private Long itemInventario;
	
	@NotNull
	@Column (name = "CANTIDAD")
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTO")
	private ProductoEntity productoEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BODEGA")
	private BodegaEntity bodegaEntity;

	public ItemInventarioEntity() {
		
	}
	
	public Long getItemInventario() {
		return itemInventario;
	}

	public void setItemInventario(Long itemInventario) {
		this.itemInventario = itemInventario;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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

	public Double calcularImporte() {
		return cantidad.doubleValue() * productoEntity.getPrecio();
	}
	
}
