package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ProveedorEntity;

public interface IProveedorService {

	public void save(ProveedorEntity proveedorEntity);
	
	public void delete(Long proveedor);
	
	public ProveedorEntity findOne(Long proveedor);

	public List<ProveedorEntity> findAll();
	
	public Page<ProveedorEntity> findAll(Pageable pageable);
	
	public ProveedorEntity fetchByIdWithDocumentosInventario(Long proveedor);
	
}
