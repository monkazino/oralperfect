package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.TipoParametroEntity;

public interface ITipoParametroService {

	public List<TipoParametroEntity> findAll();
	
	public Page<TipoParametroEntity> findAll(Pageable pageable);

	public void save(TipoParametroEntity tipoParametroEntity);
	
	public TipoParametroEntity findOne(Long id);
	
	public void delete(Long id);
	
	public TipoParametroEntity fetchByIdWithParametros(Long id);
	
}
