package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.ITipoDocumentoInventarioDao;
import com.monkazino.consultorio.app.models.entity.TipoDocumentoInventarioEntity;
import com.monkazino.consultorio.app.models.service.ITipoDocumentoInventarioService;

@Service
public class TipoDocumentoInventarioImpl implements ITipoDocumentoInventarioService {

	@Autowired
	private ITipoDocumentoInventarioDao tipoDocumentoInventarioDao;
	
	@Override
	@Transactional
	public void save(TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity) {
		tipoDocumentoInventarioDao.save(tipoDocumentoInventarioEntity);

	}

	@Override
	@Transactional
	public void delete(Long tipoDocumentoInventario) {
		tipoDocumentoInventarioDao.deleteById(tipoDocumentoInventario);

	}

	@Override
	@Transactional(readOnly = true)
	public TipoDocumentoInventarioEntity findOne(Long tipoDocumentoInventario) {
		return tipoDocumentoInventarioDao.findById(tipoDocumentoInventario).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoDocumentoInventarioEntity> findAll() {
		return (List<TipoDocumentoInventarioEntity>) tipoDocumentoInventarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoDocumentoInventarioEntity> findAll(Pageable pageable) {
		return tipoDocumentoInventarioDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int consultarCountTipoDocumentoInventarioByCodigo(String codigo) {
		return tipoDocumentoInventarioDao.consultarCountTipoDocumentoInventarioByCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountTipoDocumentoInventarioByCodigoTipoDocumentoInventario(String codigo, Long tipoDocumentoInventario) {
		return tipoDocumentoInventarioDao.consultarCountTipoDocumentoInventarioByCodigoTipoDocumentoInventario(codigo, tipoDocumentoInventario);
	}

}
