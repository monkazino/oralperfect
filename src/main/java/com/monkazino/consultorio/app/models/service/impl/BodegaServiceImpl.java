package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IBodegaDao;
import com.monkazino.consultorio.app.models.entity.BodegaEntity;
import com.monkazino.consultorio.app.models.service.IBodegaService;

@Service
public class BodegaServiceImpl implements IBodegaService {

	@Autowired
	private IBodegaDao bodegaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<BodegaEntity> findAll() {
		return (List<BodegaEntity>) bodegaDao.findAll();
	}

	@Override
	@Transactional
	public void save(BodegaEntity bodegaEntity) {
		bodegaDao.save(bodegaEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public BodegaEntity findOne(Long bodega) {
		return bodegaDao.findById(bodega).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long bodega) {
		bodegaDao.deleteById(bodega);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<BodegaEntity> findAll(Pageable pageable) {
		return bodegaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long consultarCountBodegaByCodigo(String codigo) {
		return bodegaDao.consultarCountBodegaByCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public Long consultarCountBodegaByCodigoBodega(String codigo, Long bodega) {
		return bodegaDao.consultarCountBodegaByCodigoBodega(codigo, bodega);
	}

}
