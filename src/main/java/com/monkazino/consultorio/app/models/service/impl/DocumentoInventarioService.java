package com.monkazino.consultorio.app.models.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IDocumentoInventarioDao;
import com.monkazino.consultorio.app.models.entity.DocumentoInventarioEntity;
import com.monkazino.consultorio.app.models.service.IDocumentoInventarioService;

@Service
public class DocumentoInventarioService implements IDocumentoInventarioService {

	@Autowired
	private IDocumentoInventarioDao documentoInventarioDao;
	
	@Override
	@Transactional
	public void save(DocumentoInventarioEntity documentoInventarioEntity) {
		documentoInventarioDao.save(documentoInventarioEntity);
	}

	@Override
	@Transactional
	public void delete(Long documentoInventario) {
		documentoInventarioDao.deleteById(documentoInventario);

	}

	@Override
	@Transactional(readOnly = true)
	public DocumentoInventarioEntity findOne(Long documentoInventario) {
		return documentoInventarioDao.findById(documentoInventario).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DocumentoInventarioEntity> findAll() {
		return (List<DocumentoInventarioEntity>) documentoInventarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DocumentoInventarioEntity> findAll(Pageable pageable) {
		return documentoInventarioDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly=true)
	public DocumentoInventarioEntity fetchByIdWithProveedorEntityWhithItemDocumentoInventarioEntityWithProductoEntity(Long documentoInventario) {
		return documentoInventarioDao.fetchByIdWithProveedorEntityWhithItemDocumentoInventarioEntityWithProductoEntity(documentoInventario);
	}
	
}
