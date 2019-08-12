package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ParametroEntity;

public interface IParametroService {

	public List<ParametroEntity> findAll();
	
	public Page<ParametroEntity> findAll(Pageable pageable);

	public void save(ParametroEntity parametroEntity);
	
	public ParametroEntity findOne(Long id);
	
	public void delete(Long id);
	
}
