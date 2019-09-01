package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ParametroPersonaEntity;

public interface IParametroPersonaService {

	public List<ParametroPersonaEntity> findAll();
	
	public Page<ParametroPersonaEntity> findAll(Pageable pageable);

	public void save(ParametroPersonaEntity parametroPersonaEntity);
	
	public ParametroPersonaEntity findOne(Long id);
	
	public void delete(Long id);
	
	public List<ParametroPersonaEntity> consultarParametrosPersonaTipoParametro(String codigoTipoParametro);
	
}
