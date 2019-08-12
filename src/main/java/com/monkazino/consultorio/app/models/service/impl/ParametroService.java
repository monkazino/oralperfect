package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IParametroDao;
import com.monkazino.consultorio.app.models.entity.ParametroEntity;
import com.monkazino.consultorio.app.models.service.IParametroService;

@Service
public class ParametroService implements IParametroService {

	@Autowired
	private IParametroDao parametroDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ParametroEntity> findAll() {
		// TODO Auto-generated method stub
		return (List<ParametroEntity>) parametroDao.findAll();
	}

	@Override
	@Transactional
	public void save(ParametroEntity parametroEntity) {
		parametroDao.save(parametroEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public ParametroEntity findOne(Long id) {
		return parametroDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		parametroDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParametroEntity> findAll(Pageable pageable) {
		return parametroDao.findAll(pageable);
	}

}
