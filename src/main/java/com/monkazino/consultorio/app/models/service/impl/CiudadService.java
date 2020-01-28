package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.ICiudadDao;
import com.monkazino.consultorio.app.models.entity.CiudadEntity;
import com.monkazino.consultorio.app.models.service.ICiudadService;

@Service
public class CiudadService implements ICiudadService {

	@Autowired
	private ICiudadDao ciudadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<CiudadEntity> findAll() {
		return (List<CiudadEntity>) ciudadDao.findAll();
	}

	@Override
	@Transactional
	public void save(CiudadEntity ciudadEntity) {
		ciudadDao.save(ciudadEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public CiudadEntity findOne(Long id) {
		return ciudadDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		ciudadDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<CiudadEntity> findAll(Pageable pageable) {
		return ciudadDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CiudadEntity> consultarCiudadesDepartamento(Long departamento) {
		return (List<CiudadEntity>) ciudadDao.consultarCiudadesDepartamento(departamento);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CiudadEntity fetchByIdWithLocalidades(Long ciudad) {
		return ciudadDao.fetchByIdWithLocalidades(ciudad);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountCiudadByCodigoDepartamento(String codigo, Long departamento) {
		return ciudadDao.consultarCountCiudadByCodigoDepartamento(codigo, departamento);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountCiudadByCodigoCiudadDepartamento(String codigo, Long ciudad, Long departamento) {
		return ciudadDao.consultarCountCiudadByCodigoCiudadDepartamento(codigo, ciudad, departamento);
	}
}
