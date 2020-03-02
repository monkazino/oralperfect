package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ParametroPersonaEntity;

public interface IParametroPersonaService {

	public void save(ParametroPersonaEntity parametroPersonaEntity);
	
	public void delete(Long parametroPersona);
	
	public ParametroPersonaEntity findOne(Long parametroPersona);
	
	public List<ParametroPersonaEntity> findAll();
	
	public Page<ParametroPersonaEntity> findAll(Pageable pageable);

	public List<ParametroPersonaEntity> consultarParametrosPersonaTipoParametroPersona(String codigoTipoParametroPersona);

	public int consultarCountParametroPersonaByCodigoTipoParametroPersona(String codigo, Long tipoParametroPersona);
	
	public int consultarCountParametroPersonaByCodigoParametroPersonaTipoParametroPersona(String codigo, Long parametroPersona, Long tipoParametroPersona);
	
}
