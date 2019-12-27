package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.BarrioEntity;

public interface IBarrioService {

	public List<BarrioEntity> findAll();
	
	public Page<BarrioEntity> findAll(Pageable pageable);

	public void save(BarrioEntity barrioEntity);
	
	public BarrioEntity findOne(Long id);
	
	public void delete(Long id);
	
	public List<BarrioEntity> consultarBarriosLocalidad(Long localidad);

}
