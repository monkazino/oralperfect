package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;

public interface ITipoParametroPersonaService {

	public void save(TipoParametroPersonaEntity tipoParametroPersonaEntity);
	
	public void delete(Long tipoParametro);
	
	public TipoParametroPersonaEntity findOne(Long tipoParametroPersona);
	
	public List<TipoParametroPersonaEntity> findAll();
	
	public Page<TipoParametroPersonaEntity> findAll(Pageable pageable);

	public TipoParametroPersonaEntity fetchByIdWithParametrosPersona(Long tipoParametroPersona);

	public int consultarCountTipoParametroPersonaByCodigo(String codigo);
	
	public int consultarCountTipoParametroPersonaByCodigoTipoParametroPersona(String codigo, Long tipoParametroPersona);
	
}
