package com.monkazino.consultorio.app.models.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IPacienteDao;
import com.monkazino.consultorio.app.models.entity.PacienteEntity;
import com.monkazino.consultorio.app.models.service.IPacienteService;

@Service
public class PacienteService implements IPacienteService {

	@Autowired
	private IPacienteDao pacienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<PacienteEntity> findAll() {
		return (List<PacienteEntity>) pacienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(PacienteEntity pacienteEntity) {
		pacienteDao.save(pacienteEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public PacienteEntity findOne(Long id) {
		return pacienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		pacienteDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<PacienteEntity> findAll(Pageable pageable) {
		return pacienteDao.findAll(pageable);
	}
	
}
