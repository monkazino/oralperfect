package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IParametroPersonaDao;
import com.monkazino.consultorio.app.models.entity.ParametroPersonaEntity;
import com.monkazino.consultorio.app.models.service.IParametroPersonaService;
import com.monkazino.consultorio.app.util.general.EstadoActivoInactivoEnum;

@Service
public class ParametroPersonaService implements IParametroPersonaService {

	@Autowired
	private IParametroPersonaDao parametroPersonaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ParametroPersonaEntity> findAll() {
		return (List<ParametroPersonaEntity>) parametroPersonaDao.findAll();
	}

	@Override
	@Transactional
	public void save(ParametroPersonaEntity parametroPersonaEntity) {
		parametroPersonaDao.save(parametroPersonaEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public ParametroPersonaEntity findOne(Long parametroPersona) {
		return parametroPersonaDao.findById(parametroPersona).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long parametroPersona) {
		parametroPersonaDao.deleteById(parametroPersona);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParametroPersonaEntity> findAll(Pageable pageable) {
		return parametroPersonaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ParametroPersonaEntity> consultarParametrosPersonaTipoParametroPersona(String codigoTipoParametroPersona) {
		return (List<ParametroPersonaEntity>) parametroPersonaDao.consultarParametrosPersonaTipoParametroPersona(codigoTipoParametroPersona, EstadoActivoInactivoEnum.ACTIVO.getCodigo());
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountParametroPersonaByCodigoTipoParametroPersona(String codigo, Long tipoParametroPersona) {
		return parametroPersonaDao.consultarCountParametroPersonaByCodigoTipoParametroPersona(codigo, tipoParametroPersona);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountParametroPersonaByCodigoParametroPersonaTipoParametroPersona(String codigo, Long parametroPersona, Long tipoParametroPersona) {
		return parametroPersonaDao.consultarCountParametroPersonaByCodigoParametroPersonaTipoParametroPersona(codigo, parametroPersona, tipoParametroPersona);
	}

}
