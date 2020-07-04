package com.monkazino.consultorio.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_DOCUMENTO_INVENTARIO")
	private TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROVEEDOR")
	private ProveedorEntity proveedorEntity;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	@NotNull
	@NotEmpty
	@Column (name = "OBSERVACION")
	private String observacion;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DOCUMENTO_INVENTARIO")
	private List<ItemInventarioEntity> itemsInventario;

	public DocumentoInventarioEntity() {
		this.tipoDocumentoInventarioEntity = new TipoDocumentoInventarioEntity();
		this.itemsInventario = new ArrayList<ItemInventarioEntity>();
	}
	
	public Long getDocumentoInventario() {
		return documentoInventario;
	}

	public void setDocumentoInventario(Long documentoInventario) {
		this.documentoInventario = documentoInventario;
	}

	public TipoDocumentoInventarioEntity getTipoDocumentoInventarioEntity() {
		return this.tipoDocumentoInventarioEntity;
	}
	
	public void setTipoDocumentoInventarioEntity(TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity) {
		this.tipoDocumentoInventarioEntity = tipoDocumentoInventarioEntity;
	}

	public ProveedorEntity getProveedorEntity() {
		return proveedorEntity;
	}

	public void setProveedorEntity(ProveedorEntity proveedorEntity) {
		this.proveedorEntity = proveedorEntity;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public List<ItemInventarioEntity> getItemsInventario() {
		return itemsInventario;
	}

	public void setItemsInventario(List<ItemInventarioEntity> itemsInventario) {
		this.itemsInventario = itemsInventario;
	}
	
	public void addItemFactura(ItemInventarioEntity itemInventario) {
		this.itemsInventario.add(itemInventario);
	}

	public Double getTotal() {
		Double total = 0.0;

		int size = itemsInventario.size();

		for (int i = 0; i < size; i++) {
			total += itemsInventario.get(i).calcularImporte();
		}
		return total;
	}
	
}
