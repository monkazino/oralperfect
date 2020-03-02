package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.BarrioEntity;

public interface IBarrioService {

	public void save(BarrioEntity barrioEntity);
	
	public void delete(Long barrio);
	
	public BarrioEntity findOne(Long barrio);
	
	public List<BarrioEntity> findAll();
	
	public Page<BarrioEntity> findAll(Pageable pageable);

	public int consultarCountBarrioByCodigoLocalidad(String codigo, Long localidad);
	
	public int consultarCountBarrioByCodigoBarrioLocalidad(String codigo, Long barrio, Long localidad);

	public List<BarrioEntity> consultarBarriosLocalidad(Long localidad);

}
