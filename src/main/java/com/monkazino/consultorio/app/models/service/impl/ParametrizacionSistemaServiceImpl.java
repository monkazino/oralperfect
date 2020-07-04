package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IParametrizacionSistemaDao;
import com.monkazino.consultorio.app.models.entity.ParametrizacionSistemaEntity;
import com.monkazino.consultorio.app.models.service.IParametrizacionSistemaService;

@Service
public class ParametrizacionSistemaServiceImpl implements IParametrizacionSistemaService {

	@Autowired
	private IParametrizacionSistemaDao parametrizacionSistemaDao;
	
	@Override
	@Transactional
	public void save(ParametrizacionSistemaEntity parametrizacionSistemaEntity) {
		parametrizacionSistemaDao.save(parametrizacionSistemaEntity);

	}

	@Override
	@Transactional
	public void delete(Long parametrizacionSistema) {
		parametrizacionSistemaDao.deleteById(parametrizacionSistema);

	}

	@Override
	@Transactional(readOnly = true)
	public ParametrizacionSistemaEntity findOne(Long parametrizacionSistema) {
		return parametrizacionSistemaDao.findById(parametrizacionSistema).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ParametrizacionSistemaEntity> findAll() {
		return (List<ParametrizacionSistemaEntity>) parametrizacionSistemaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParametrizacionSistemaEntity> findAll(Pageable pageable) {
		return parametrizacionSistemaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int consultarCountParametrizacionSistemaByCodigo(String codigo) {
		return parametrizacionSistemaDao.consultarCountParametrizacionSistemaByCodigo(codigo);
	}

}
