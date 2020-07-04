package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.BodegaEntity;

public interface IBodegaService {

	public void save(BodegaEntity bodegaEntity);
	
	public void delete(Long bodega);
	
	public BodegaEntity findOne(Long bodega);
	
	public List<BodegaEntity> findAll();
	
	public Page<BodegaEntity> findAll(Pageable pageable);

	public Long consultarCountBodegaByCodigo(String codigo);
	
	public Long consultarCountBodegaByCodigoBodega(String codigo, Long bodega);
	
}
