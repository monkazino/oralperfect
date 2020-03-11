package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.ProveedorEntity;

public interface IProveedorDao  extends PagingAndSortingRepository<ProveedorEntity, Long> {
	
	@Query("select p from ProveedorEntity p left join fetch p.documentosInventario di where p.proveedor=?1")
	public ProveedorEntity fetchByIdWithDocumentosInventario(Long proveedor);
	
}
