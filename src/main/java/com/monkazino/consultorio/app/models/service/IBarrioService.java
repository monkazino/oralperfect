package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.BarrioEntity;

public interface IBarrioService {

	public List<BarrioEntity> findAll();
	
	public Page<BarrioEntity> findAll(Pageable pageable);

	public void save(BarrioEntity barrioEntity);
	
	public BarrioEntity findOne(Long barrio);
	
	public void delete(Long barrio);
	
	public List<BarrioEntity> consultarBarriosLocalidad(Long localidad);

	public int consultarCountBarrioByCodigoLocalidad(String codigo, Long localidad);
	
	public int consultarCountBarrioByCodigoBarrioLocalidad(String codigo, Long barrio, Long localidad);

}
