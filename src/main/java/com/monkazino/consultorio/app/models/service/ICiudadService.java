package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.CiudadEntity;

public interface ICiudadService {

	public List<CiudadEntity> findAll();
	
	public Page<CiudadEntity> findAll(Pageable pageable);

	public void save(CiudadEntity ciudadEntity);
	
	public CiudadEntity findOne(Long id);
	
	public void delete(Long id);
	
	public List<CiudadEntity> consultarCiudadesDepartamento(Long departamento);
	
	public CiudadEntity fetchByIdWithLocalidades(Long id);
	
}