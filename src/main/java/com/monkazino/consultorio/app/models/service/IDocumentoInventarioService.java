package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.DocumentoInventarioEntity;

public interface IDocumentoInventarioService {

	public void save(DocumentoInventarioEntity documentoInventarioEntity);
	
	public void delete(Long documentoInventario);
	
	public DocumentoInventarioEntity findOne(Long documentoInventario);

	public List<DocumentoInventarioEntity> findAll();
	
	public Page<DocumentoInventarioEntity> findAll(Pageable pageable);
	
	DocumentoInventarioEntity fetchByIdWithProveedorEntityWhithItemDocumentoInventarioEntityWithProductoEntity(Long documentoInventario);
	
}
