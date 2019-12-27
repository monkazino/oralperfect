package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.LocalidadEntity;

public interface ILocalidadService {

	public List<LocalidadEntity> findAll();
	
	public Page<LocalidadEntity> findAll(Pageable pageable);

	public void save(LocalidadEntity localidadEntity);
	
	public LocalidadEntity findOne(Long id);
	
	public void delete(Long id);
	
	public List<LocalidadEntity> consultarLocalidadesCiudad(Long ciudad);
	
	public LocalidadEntity fetchByIdWithBarrios(Long id);

}
