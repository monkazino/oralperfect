package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.ITipoParametroPersonaDao;
import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroPersonaService;

@Service
public class TipoParametroPersonaServiceImpl implements ITipoParametroPersonaService {

	@Autowired
	private ITipoParametroPersonaDao tipoParametroPersonaDao;
	
	@Override
	@Transactional
	public void save(TipoParametroPersonaEntity tipoParametroPersonaEntity) {
		tipoParametroPersonaDao.save(tipoParametroPersonaEntity);

	}

	@Override
	@Transactional
	public void delete(Long tipoParametroPersona) {
		tipoParametroPersonaDao.deleteById(tipoParametroPersona);

	}

	@Override
	@Transactional(readOnly = true)
	public TipoParametroPersonaEntity findOne(Long tipoParametroPersona) {
		return tipoParametroPersonaDao.findById(tipoParametroPersona).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoParametroPersonaEntity> findAll() {
		return (List<TipoParametroPersonaEntity>) tipoParametroPersonaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoParametroPersonaEntity> findAll(Pageable pageable) {
		return tipoParametroPersonaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoParametroPersonaEntity fetchByIdWithParametrosPersona(Long tipoParametroPersona) {
		return tipoParametroPersonaDao.fetchByIdWithParametrosPersona(tipoParametroPersona);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountTipoParametroPersonaByCodigo(String codigo) {
		return tipoParametroPersonaDao.consultarCountTipoParametroPersonaByCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountTipoParametroPersonaByCodigoTipoParametroPersona(String codigo, Long tipoParametroPersona) {
		return tipoParametroPersonaDao.consultarCountTipoParametroPersonaByCodigoTipoParametroPersona(codigo, tipoParametroPersona);
	}

}
