package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IBarrioDao;
import com.monkazino.consultorio.app.models.entity.BarrioEntity;
import com.monkazino.consultorio.app.models.service.IBarrioService;

@Service
public class BarrioService implements IBarrioService {

	@Autowired
	private IBarrioDao barrioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<BarrioEntity> findAll() {
		return (List<BarrioEntity>) barrioDao.findAll();
	}

	@Override
	@Transactional
	public void save(BarrioEntity barrioEntity) {
		barrioDao.save(barrioEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public BarrioEntity findOne(Long id) {
		return barrioDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		barrioDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<BarrioEntity> findAll(Pageable pageable) {
		return barrioDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<BarrioEntity> consultarBarriosLocalidad(Long localidad) {
		return (List<BarrioEntity>) barrioDao.consultarBarriosLocalidad(localidad);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountBarrioByCodigoLocalidad(String codigo, Long localidad) {
		return barrioDao.consultarCountBarrioByCodigoLocalidad(codigo, localidad);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountBarrioByCodigoBarrioLocalidad(String codigo, Long barrio, Long localidad) {
		return barrioDao.consultarCountBarrioByCodigoBarrioLocalidad(codigo, barrio, localidad);
	}
}
