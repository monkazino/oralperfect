package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.CiudadEntity;

public interface ICiudadService {

	public void save(CiudadEntity ciudadEntity);
	
	public void delete(Long ciudad);
	
	public CiudadEntity findOne(Long ciudad);
	
	public List<CiudadEntity> findAll();
	
	public Page<CiudadEntity> findAll(Pageable pageable);

	public int consultarCountCiudadByCodigoDepartamento(String codigo, Long departamento);
	
	public int consultarCountCiudadByCodigoCiudadDepartamento(String codigo, Long ciudad, Long departamento);
	
	public List<CiudadEntity> consultarCiudadesDepartamento(Long departamento);
	
	public CiudadEntity fetchByIdWithLocalidades(Long ciudad);

}