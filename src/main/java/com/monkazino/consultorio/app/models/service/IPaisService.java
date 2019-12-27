package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.PaisEntity;

public interface IPaisService {

	public List<PaisEntity> findAll();
	
	public Page<PaisEntity> findAll(Pageable pageable);

	public void save(PaisEntity paisEntity);
	
	public PaisEntity findOne(Long id);
	
	public void delete(Long id);
	
	public PaisEntity fetchByIdWithDepartamentos(Long id);

}
