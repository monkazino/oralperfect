package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IPaisDao;
import com.monkazino.consultorio.app.models.entity.PaisEntity;
import com.monkazino.consultorio.app.models.service.IPaisService;

@Service
public class PaisService implements IPaisService {

	@Autowired
	private IPaisDao paisDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<PaisEntity> findAll() {
		return (List<PaisEntity>) paisDao.findAll();
	}

	@Override
	@Transactional
	public void save(PaisEntity paisEntity) {
		paisDao.save(paisEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public PaisEntity findOne(Long departamento) {
		return paisDao.findById(departamento).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long departamento) {
		paisDao.deleteById(departamento);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<PaisEntity> findAll(Pageable pageable) {
		return paisDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PaisEntity fetchByIdWithDepartamentos(Long pais) {
		return paisDao.fetchByIdWithDepartamentos(pais);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountPaisByCodigo(String codigo) {
		return paisDao.consultarCountPaisByCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountPaisByCodigoPais(String codigo, Long pais) {
		return paisDao.consultarCountPaisByCodigoPais(codigo, pais);
	}
}
