package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IDepartamentoDao;
import com.monkazino.consultorio.app.models.entity.DepartamentoEntity;
import com.monkazino.consultorio.app.models.service.IDepartamentoService;

@Service
public class DepartamentoService implements IDepartamentoService {

	@Autowired
	private IDepartamentoDao departamentoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<DepartamentoEntity> findAll() {
		return (List<DepartamentoEntity>) departamentoDao.findAll();
	}

	@Override
	@Transactional
	public void save(DepartamentoEntity departamentoEntity) {
		departamentoDao.save(departamentoEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public DepartamentoEntity findOne(Long departamento) {
		return departamentoDao.findById(departamento).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long departamento) {
		departamentoDao.deleteById(departamento);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<DepartamentoEntity> findAll(Pageable pageable) {
		return departamentoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DepartamentoEntity> consultarDepartamentosPais(Long pais) {
		return (List<DepartamentoEntity>) departamentoDao.consultarDepartamentosPais(pais);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DepartamentoEntity fetchByIdWithCiudades(Long departamento) {
		return departamentoDao.fetchByIdWithCiudades(departamento);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountDepartamentoByCodigoPais(String codigo, Long pais) {
		return departamentoDao.consultarCountDepartamentoByCodigoPais(codigo, pais);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountDepartamentoByCodigoDepartamentoPais(String codigo, Long departamento, Long pais) {
		return departamentoDao.consultarCountDepartamentoByCodigoDepartamentoPais(codigo, departamento, pais);
	}

}
