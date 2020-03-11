package com.monkazino.consultorio.app.models.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IProveedorDao;
import com.monkazino.consultorio.app.models.entity.ProveedorEntity;
import com.monkazino.consultorio.app.models.service.IProveedorService;

@Service
public class ProveedorService implements IProveedorService {

	@Autowired
	private IProveedorDao proveedorDao;
	
	@Override
	@Transactional
	public void save(ProveedorEntity proveedorEntity) {
		proveedorDao.save(proveedorEntity);
	}

	@Override
	@Transactional
	public void delete(Long proveedor) {
		proveedorDao.deleteById(proveedor);

	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProveedorEntity> findAll() {
		return (List<ProveedorEntity>) proveedorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ProveedorEntity findOne(Long proveedor) {
		return proveedorDao.findById(proveedor).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProveedorEntity> findAll(Pageable pageable) {
		return proveedorDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProveedorEntity fetchByIdWithDocumentosInventario(Long proveedor) {
		return proveedorDao.fetchByIdWithDocumentosInventario(proveedor);
	}

	
}
