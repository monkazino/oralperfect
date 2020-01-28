package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.ILocalidadDao;
import com.monkazino.consultorio.app.models.entity.LocalidadEntity;
import com.monkazino.consultorio.app.models.service.ILocalidadService;

@Service
public class LocalidadService implements ILocalidadService {

	@Autowired
	private ILocalidadDao localidadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<LocalidadEntity> findAll() {
		return (List<LocalidadEntity>) localidadDao.findAll();
	}

	@Override
	@Transactional
	public void save(LocalidadEntity localidadEntity) {
		localidadDao.save(localidadEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public LocalidadEntity findOne(Long id) {
		return localidadDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		localidadDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<LocalidadEntity> findAll(Pageable pageable) {
		return localidadDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<LocalidadEntity> consultarLocalidadesCiudad(Long ciudad) {
		return (List<LocalidadEntity>) localidadDao.consultarLocalidadesCiudad(ciudad);
	}
	
	@Override
	@Transactional(readOnly = true)
	public LocalidadEntity fetchByIdWithBarrios(Long localidad) {
		return localidadDao.fetchByIdWithBarrios(localidad);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountLocalidadByCodigoCiudad(String codigo, Long ciudad) {
		return localidadDao.consultarCountLocalidadByCodigoCiudad(codigo, ciudad);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountLocalidadByCodigoLocalidadCiudad(String codigo, Long localidad, Long ciudad) {
		return localidadDao.consultarCountLocalidadByCodigoLocalidadCiudad(codigo, localidad, ciudad);
	}
}
