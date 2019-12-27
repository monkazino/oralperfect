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
	public PaisEntity findOne(Long id) {
		return paisDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		paisDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<PaisEntity> findAll(Pageable pageable) {
		return paisDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PaisEntity fetchByIdWithDepartamentos(Long id) {
		return paisDao.fetchByIdWithDepartamentos(id);
	}

}
