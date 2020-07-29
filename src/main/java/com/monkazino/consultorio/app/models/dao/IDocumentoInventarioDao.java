package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.DocumentoInventarioEntity;

public interface IDocumentoInventarioDao extends PagingAndSortingRepository<DocumentoInventarioEntity, Long> {
	
	@Query("select di from DocumentoInventarioEntity di join fetch di.proveedorEntity p join fetch di.itemsInventario id join fetch id.productoEntity where di.documentoInventario=?1")
	public DocumentoInventarioEntity fetchByIdWithProveedorEntityWhithItemDocumentoInventarioEntityWithProductoEntity(Long documentoInventario);
}
