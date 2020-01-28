package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;

public interface ITipoParametroPersonaService {

	public List<TipoParametroPersonaEntity> findAll();
	
	public Page<TipoParametroPersonaEntity> findAll(Pageable pageable);

	public void save(TipoParametroPersonaEntity tipoParametroPersonaEntity);
	
	public TipoParametroPersonaEntity findOne(Long id);
	
	public void delete(Long id);
	
	public TipoParametroPersonaEntity fetchByIdWithParametrosPersona(Long tipoParametroPersona);

	public int consultarCountTipoParametroPersonaByCodigo(String codigo);
	
	public int consultarCountTipoParametroPersonaByCodigoTipoParametroPersona(String codigo, Long tipoParametroPersona);
	
}
