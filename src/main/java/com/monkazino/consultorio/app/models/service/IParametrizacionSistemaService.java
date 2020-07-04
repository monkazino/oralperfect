package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ParametrizacionSistemaEntity;

public interface IParametrizacionSistemaService {

	public void save(ParametrizacionSistemaEntity parametrizacionSistemaEntity);
	
	public void delete(Long parametrizacionSistema);
	
	public ParametrizacionSistemaEntity findOne(Long parametrizacionSistema);
	
	public List<ParametrizacionSistemaEntity> findAll();
	
	public Page<ParametrizacionSistemaEntity> findAll(Pageable pageable);

	public int consultarCountParametrizacionSistemaByCodigo(String codigo);
	
}
