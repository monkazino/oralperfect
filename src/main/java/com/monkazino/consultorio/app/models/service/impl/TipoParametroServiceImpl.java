package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.ITipoParametroDao;
import com.monkazino.consultorio.app.models.entity.TipoParametroEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroService;

@Service
public class TipoParametroServiceImpl implements ITipoParametroService {

	@Autowired
	private ITipoParametroDao tipoParametroDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoParametroEntity> findAll() {
		// TODO Auto-generated method stub
		return (List<TipoParametroEntity>) tipoParametroDao.findAll();
	}

	@Override
	@Transactional
	public void save(TipoParametroEntity tipoParametroEntity) {
		tipoParametroDao.save(tipoParametroEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public TipoParametroEntity findOne(Long id) {
		return tipoParametroDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		tipoParametroDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoParametroEntity> findAll(Pageable pageable) {
		return tipoParametroDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoParametroEntity fetchByIdWithParametros(Long id) {
		return tipoParametroDao.fetchByIdWithParametros(id);
	}

}
