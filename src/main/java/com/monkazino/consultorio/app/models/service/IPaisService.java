package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.PaisEntity;

public interface IPaisService {

	public void save(PaisEntity paisEntity);
	
	public void delete(Long pais);
	
	public PaisEntity findOne(Long pais);
	
	public List<PaisEntity> findAll();
	
	public Page<PaisEntity> findAll(Pageable pageable);

	public int consultarCountPaisByCodigo(String codigo);
	
	public int consultarCountPaisByCodigoPais(String codigo, Long pais);

	public PaisEntity fetchByIdWithDepartamentos(Long pais);
	
}
